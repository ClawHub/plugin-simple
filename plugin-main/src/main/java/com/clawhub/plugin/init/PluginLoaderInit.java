package com.clawhub.plugin.init;

import com.clawhub.plugin.core.PluginClassLoader;
import com.clawhub.plugin.core.PluginManager;
import com.clawhub.plugin.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <Description> 类加载器初始化，并加载basePath下的jar包<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/5 <br>
 */
@Component
public class PluginLoaderInit {
    @Autowired
    private PluginService pluginService;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        System.out.println("插件初始化=====开始====》》》》");
        //初始化插件管理器，并加载basePath下的jar
        PluginManager.setPluginClassLoader(new PluginClassLoader("F:\\tmp\\plugin"));
        System.out.println("插件初始化=====结束====》》》》");
    }
}
