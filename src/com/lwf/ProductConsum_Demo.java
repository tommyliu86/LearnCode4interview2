package com.lwf;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductConsum_Demo {
    public static void main(String[] args) {
        tranditionPS();
    }
    static void testforLock(){
        shareData sd=new shareData();
        new Thread(()->{
            sd.testForLock1();
        },"t1").start();
        new Thread(()->{
            sd.testForLock1();
        },"t2").start();
    }
    public static void tranditionPS() {
        shareData sdate=new shareData();
        //生产者
        new Thread(()->{
            for (int i = 0; i < 5; i++) {

                try {
                    sdate.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"P1").start();
        //生产者
        new Thread(()->{
            for (int i = 0; i < 5; i++) {

                try {
                    sdate.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"P2").start();
        //消费者
        new Thread(()->{
            for (int i = 0; i < 5; i++) {

                try {
                    sdate.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C1").start();
        //消费者
        new Thread(()->{
            for (int i = 0; i < 5; i++) {

                try {
                    sdate.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C2").start();
    }


}
class shareData {
    private volatile int count = 0;

    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void testForLock1(){
     lock.lock();
     try{
         System.out.println(Thread.currentThread().getName() + "\t" + "休息10秒");
         TimeUnit.SECONDS.sleep(10);

     }catch(Exception e){
         e.getStackTrace();
     }finally {
         lock.unlock();
     }
    }
    public void testForLock2(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t" + "休息10秒");
            TimeUnit.SECONDS.sleep(10);
        }catch(Exception e){
            e.getStackTrace();
        }finally{
            lock.unlock();
        }

    }
    public void increment() throws Exception {
        lock.lock();
        try {

            //判断
            System.out.println(Thread.currentThread().getName() + "\t" + "获取到锁");
            while (count != 0) {
                condition.await();
            }
            //干活
            count++;
            System.out.println(Thread.currentThread().getName() + "\t" + count);
            //通知
            condition.signalAll();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void decrement() throws Exception {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "获取到锁");
            while (count != 1) {
                condition.await();
            }
            count--;
            System.out.println(Thread.currentThread().getName() + "\t" + count);
            condition.signalAll();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
