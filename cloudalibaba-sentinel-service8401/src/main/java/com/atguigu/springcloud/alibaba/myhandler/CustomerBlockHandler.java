package com.atguigu.springcloud.alibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;

/**
 * 全局兜底的方法
 */
public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(4444,"按客户自定义,GlobalHandler,-----1");
    }
    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(4444,"按客户自定义,GlobalHandler,-----2");
    }
}
