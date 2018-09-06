package com.clawhub.plugin.controller;

import com.alibaba.fastjson.JSONObject;
import com.clawhub.plugin.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
