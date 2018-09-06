package com.clawhub.plugin.service;

import java.io.FileFilter;

/**
 * <Description> 插件服务接口<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/5 <br>
 */
public interface PluginService {
    /**
     * 增加额外的jar
     *
     * @param basePath 额外jar路径
     * @param filter   文件过滤
     */
    void addExternalJar(String basePath, FileFilter filter);

    /**
     * 获取插件类
     *
     * @param className 插件类的全量名
     * @param required  插件的接口地址
     * @param <T>       插件类型
     * @return 插件类
     */
    <T> T getPlugin(String className, Class<T> required);
}
