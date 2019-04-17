package com.bling.dab.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author: hxp
 * @date: 2019/4/17 19:16
 * @description:
 */
@Component
public class Task {

    private static Random random = new Random();

    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一"+Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(300));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二"+Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(300));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三"+Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(300));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }

    @Async
    public Future<String> doTaskFour() throws Exception {
        System.out.println("开始做任务四"+Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(300));
        long end = System.currentTimeMillis();
        System.out.println("完成任务四，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务完成");
    }
}
