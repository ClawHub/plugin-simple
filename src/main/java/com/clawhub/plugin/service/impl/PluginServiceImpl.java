package com.clawhub.plugin.service.impl;

import com.clawhub.plugin.core.PluginManager;
import com.clawhub.plugin.service.PluginService;
import org.springframework.stereotype.Service;

import java.io.FileFilter;

/**
 * <Description> 插件服务<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/5 <br>
 */
@Service
public class PluginServiceImpl implements PluginService {
    @Override
    public void addExternalJar(String basePath, FileFilter filter) {
        PluginManager.addExternalJar(basePath, filter);
    }

    @Override
    public <T> T getPlugin(String className, Class<T> required) {
        return PluginManager.getPlugin(className, required);
    }
}
