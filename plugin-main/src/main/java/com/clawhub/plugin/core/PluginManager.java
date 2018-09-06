package com.clawhub.plugin.core;

import java.io.FileFilter;

/**
 * <Description> 插件管理器<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/4 <br>
 */
public class PluginManager {
    /**
     * 插件类加载器
     */
    private static PluginClassLoader pluginClassLoader;

    /**
     * Sets plugin class loader.
     *
     * @param pluginClassLoader the plugin class loader
     */
    public static void setPluginClassLoader(PluginClassLoader pluginClassLoader) {
        PluginManager.pluginClassLoader = pluginClassLoader;
    }

    /**
     * Gets plugin.
     *
     * @param <T>       the type parameter
     * @param className the class name
     * @param required  the required
     * @return the plugin
     */
    public static <T> T getPlugin(String className, Class<T> required) {
        Class<?> cls;
        try {
            cls = pluginClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("can not find class:" + className, e);
        }
        if (required.isAssignableFrom(cls)) {
            try {
                return (T) cls.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("can not newInstance class:" + className, e);
            }
        }
        throw new IllegalArgumentException("class:" + className + " not sub class of " + required);
    }

    /**
     * Add external jar.
     *
     * @param basePath the base path
     * @param filter   the filter
     */
    public static void addExternalJar(String basePath, FileFilter filter) {
        pluginClassLoader.addToClassLoader(basePath, filter);
    }
}
