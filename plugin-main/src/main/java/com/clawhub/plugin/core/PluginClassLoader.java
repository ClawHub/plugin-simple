package com.clawhub.plugin.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * <Description> 插件类加载器<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/4 <br>
 */
public class PluginClassLoader {
    /**
     * The Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(PluginClassLoader.class);

    /**
     * 类加载器
     */
    private URLClassLoader classLoader;

    /**
     * 构造函数
     *
     * @param jarfileDir jar包文件所在目录
     */
    public PluginClassLoader(String jarfileDir) {
        this(new File(jarfileDir), null, null);
    }

    /**
     * 构造函数
     *
     * @param jarfileDir jar包文件所在目录
     * @param parent     父类加载器
     * @param filter     文件过滤
     */
    public PluginClassLoader(File jarfileDir, ClassLoader parent, FileFilter filter) {
        this.classLoader = createClassLoader(jarfileDir, parent, filter);
    }

    /**
     * 创建类加载器，并加载libDir下所有jar包
     *
     * @param libDir jar包文件所在目录
     * @param parent 父类加载器
     * @param filter 文件过滤
     * @return URL类加载器
     */
    private URLClassLoader createClassLoader(final File libDir, ClassLoader parent, FileFilter filter) {
        if (null == parent) {
            parent = Thread.currentThread().getContextClassLoader();
        }
        return replaceClassLoader(URLClassLoader.newInstance(new URL[0], parent), libDir, filter);
    }

    /**
     * 加载额外的jar包
     *
     * @param baseDir jar包文件所在目录
     * @param filter  文件过滤器
     */
    public void addToClassLoader(final String baseDir, final FileFilter filter) {
        if (StringUtils.isEmpty(baseDir)) {
            throw new IllegalArgumentException("baseDir can not be empty!");
        }
        File base = new File(baseDir);
        this.classLoader = replaceClassLoader(classLoader, base, filter);
    }


    /**
     * 替换旧的类加载器，用新的类加载器加载所有base文件夹下的jar
     * 不同的类加载器，加载同一个类是不同的对象，所以存在调用类是抛出异常，所以都用同一个类加载器加载jar
     *
     * @param oldLoader 老的类加载器
     * @param base      jar所在路径
     * @param filter    文件过滤
     * @return 新的类加载器
     */
    private URLClassLoader replaceClassLoader(final URLClassLoader oldLoader, final File base, final FileFilter filter) {

        //判断文件存在，有读取权限，且为文件夹
        if (null != base && base.canRead() && base.isDirectory()) {
            //过滤获取文件夹下所有文件
            File[] files = base.listFiles(filter);
            //如果没有文件，返回原始类加载器
            if (null == files || 0 == files.length) {
                logger.error("replaceClassLoader base dir:{} is empty", base.getAbsolutePath());
                return oldLoader;
            }
            logger.info("replaceClassLoader base dir: {} ,size: {}", base.getAbsolutePath(), files.length);
            //原始类加载器中的资源数组
            URL[] oldElements = oldLoader.getURLs();
            //新建资源数组，大小为：原始资源数组大小 + base文件夹下文件数量
            URL[] elements = new URL[oldElements.length + files.length];
            //数组拷贝
            System.arraycopy(oldElements, 0, elements, 0, oldElements.length);
            //遍历新的jar,放到新的资源数组中的原始资源后面
            for (int j = 0; j < files.length; j++) {
                try {
                    URL element = files[j].toURI().normalize().toURL();
                    elements[oldElements.length + j] = element;
                    logger.info("Adding '{}' to classloader", element.toString());
                } catch (MalformedURLException e) {
                    logger.error("load jar file error", e);
                }
            }
            ClassLoader oldParent = oldLoader.getParent();
            //关闭原始类加载器
            IoUtils.closeQuietly(oldLoader);
            //在原始类加载器的父加载器下面新建类加载器
            return URLClassLoader.newInstance(elements, oldParent);
        }
        return oldLoader;
    }


    /**
     * 获取类文件
     *
     * @param className 类的全量名
     * @return 类文件
     * @throws ClassNotFoundException the class not found exception
     */
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return classLoader.loadClass(className);
    }

}
