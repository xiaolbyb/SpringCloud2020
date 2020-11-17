package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        try{
            TimeUnit.MILLISECONDS.sleep(800);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "-------testA";
    }

    @GetMapping("/testB")
    public String testB(){
//        log.info(Thread.currentThread().getName()+"\t"+"testB");
        System.out.println(Thread.currentThread().getName()+"\t"+"testB");
        return "-------testB";
    }

    @GetMapping("/testD")
    public String testD()
    {
//        try{
//            TimeUnit.SECONDS.sleep(1);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        log.info("testD 测试RT");

        log.info("testD 测试异常比例");
        int age = 10/0;
        return "------testD";
    }

    @GetMapping("/testE")
    public String testE()
    {

        log.info("testE 测试异常数");
        int age = 10/0;
        return "------testD";
    }

    @GetMapping("/testHotKey") //热点限留，要加兜底方法
    @SentinelResource(value = "testHoyKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2){
//        int age = 10/0;
        return "-----testHotKey";
    }

    //兜底方法
    public String deal_testHotKey(String p1, String p2, BlockException exception){
        return "---兜底deal_testHotKey,哈哈";
    }
}
