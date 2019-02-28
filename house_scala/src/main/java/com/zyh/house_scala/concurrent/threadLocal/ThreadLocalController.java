package com.zyh.house_scala.concurrent.threadLocal;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threadLocal")
public class ThreadLocalController {

    @GetMapping("/test")
    public Long getThreadId(){
        System.out.println(RequestHolder.getId());
        return RequestHolder.getId();
    }
}
