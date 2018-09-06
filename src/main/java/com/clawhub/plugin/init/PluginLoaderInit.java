package com.clawhub.plugin.init;

import com.clawhub.plugin.api.IHelloService;
import com.clawhub.plugin.core.PluginClassLoader;
import com.clawhub.plugin.core.PluginManager;
import com.clawhub.plugin.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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

        System.out.println("==================测试====================");
        //后期从DB或配置文件读取
        List<String> classNames = new ArrayList<>();
        classNames.add("com.clawhub.plugin.example.HelloServiceAImpl");
        classNames.add("com.clawhub.plugin.example.HelloServiceBImpl");
        while (true) {
            try {
                Thread.sleep(3 * 1000L);
                for (String className : classNames) {
                    IHelloService service = pluginService.getPlugin(className, IHelloService.class);
                    System.out.println(service.hello("plugin demo!"));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
