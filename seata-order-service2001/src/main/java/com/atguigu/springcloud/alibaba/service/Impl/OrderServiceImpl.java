package com.atguigu.springcloud.alibaba.service.Impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("----开始创建订单");
        orderDao.create(order);
        log.info("----创建订单成功");
        log.info("--------->订单微服务开始条用库存,做扣减count");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("--------->订单微服务开始条用库存,做扣减end");
        log.info("--------->订单微服务开始条用账户,做扣减money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("--------->订单微服务开始条用账户,做扣减end");

        //修改订单状态，从0-1，1：已完成
        log.info("--------->修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("--------->修改订单状态完成");

        log.info("--------->下订单结束了，O(∩_∩)O哈哈~");
    }

    @Override
    public void update() {

    }
}
