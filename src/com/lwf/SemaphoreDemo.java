package com.lwf;

import java.text.MessageFormat;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore sema=new Semaphore(3);
        for (int i = 1; i <= 10; i++) {
            final  int x=i;
            new Thread(()->{
                System.out.println(MessageFormat.format("线程：{0}开始进行信号申请", Thread.currentThread().getName()));
                try {
                    sema.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sema.release();
                System.out.println(MessageFormat.format("线程：{0}信号申请成功", Thread.currentThread().getName()));
            },String.valueOf(x)).start();
        }
        System.out.println(MessageFormat.format("剩余的信号量是：{0}", sema.availablePermits()));
        System.out.println(MessageFormat.format("排队的线程数量是：{0}",sema.getQueueLength()));
        while (sema.hasQueuedThreads()) {
            System.out.println(MessageFormat.format("排队的线程数量是：{0}",sema.getQueueLength()));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sema.release(3);
        }
        System.out.println(" all done");

    }
}

