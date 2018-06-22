package com.jadyer.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 玄玉<http://jadyer.cn/> on 2017/9/24 15:40.
 */
@Controller
public class ViewController {
    @ResponseBody
    @GetMapping({"", "/"})
    Map<String, String> index(){
        return new HashMap<String, String>(){
            private static final long serialVersionUID = 9196385657295432874L;
            {
                put("demo", "gradle");
            }
        };
    }
}