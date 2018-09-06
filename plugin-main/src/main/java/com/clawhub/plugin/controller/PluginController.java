package com.clawhub.plugin.controller;

import com.alibaba.fastjson.JSONObject;
import com.clawhub.plugin.api.IHelloService;
import com.clawhub.plugin.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> 插件网关<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/5 <br>
 */
@RestController
@RequestMapping("/plugin")
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String loadJar(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        String baseDir = body.getString("baseDir");
        pluginService.addExternalJar(baseDir, null);
        return "jar加载成功！";
    }

    @RequestMapping(value = "/job", method = RequestMethod.GET)
    public void job() {
        System.out.println("==================测试====================");
        //后期从DB或配置文件读取
        List<String> classNames = new ArrayList<>();
        classNames.add("com.clawhub.plugin.BHelloServiceImpl");
        classNames.add("com.clawhub.plugin.AHelloServiceImpl");

        while (true) {
            try {
                Thread.sleep(1000L);
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
