package com.lwf;

import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductConsumer_BlockQueue_Demo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue=new ArrayBlockingQueue<>(3);
        SharedData shareData = new SharedData(blockingQueue);
        new Thread(()->{
            try {
                shareData.product();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"producer").start();
        new Thread(()->{
            try {
                shareData.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();

        TimeUnit.SECONDS.sleep(10);
        shareData.stop();
    }
}

class   SharedData{

    private  volatile boolean flag=true;

    private AtomicInteger aInteger=new AtomicInteger(0);
    BlockingQueue<String> blockingQueue;

    public SharedData(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass());
    }
    public void product() throws InterruptedException {
        String in=null;
        boolean isOk;
        while (flag ){

            in= String.valueOf( aInteger.incrementAndGet());
           isOk= blockingQueue.offer(in,2, TimeUnit.SECONDS);
           if (isOk){
               System.out.println(Thread.currentThread().getName()+"\t"+"插入"+in+" ok");
           }else    {
               System.out.println(Thread.currentThread().getName()+"\t"+"插入"+in+" not ok");

           }
            Thread.sleep(1000);
        }
    }
    public void consume() throws InterruptedException {
        String in=null;
        boolean isOk;
        while (flag ){

            try{

                in= blockingQueue.poll(2, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName()+"\t"+"从队列获取到"+in+" ok");
            }catch(Exception e){
                flag=false;
                System.out.println(Thread.currentThread().getName()+"\t"+"从队列获取失败，设置flag=false");


            }
            Thread.sleep(1000);

        }
    }
    public void stop(){
        if (flag    ){
            flag=false;
        }
        System.out.println(Thread.currentThread().getName()+"\t"+"设置flag=false,主动结束");

    }
}