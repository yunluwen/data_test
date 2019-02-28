package com.zyh.house_scala.concurrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping("/cont/test")
    @ResponseBody
    public String test(){
        return "test_concurrent";
    }

}
