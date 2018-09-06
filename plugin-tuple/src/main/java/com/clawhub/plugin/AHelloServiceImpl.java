package com.clawhub.plugin;

import com.clawhub.plugin.api.IHelloService;

/**
 * <Description> <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/6 <br>
 */
public class AHelloServiceImpl implements IHelloService {
    @Override
    public String hello(String msg) {
        return "A:" + msg;
    }
}
