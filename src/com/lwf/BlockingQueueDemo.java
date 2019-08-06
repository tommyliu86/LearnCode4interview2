package com.lwf;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> _BQ=new ArrayBlockingQueue<Integer>(3);
        _BQ.add(1);
        _BQ.remove();
//        _BQ.element();

        _BQ.offer(2);
        _BQ.poll();
        _BQ.peek();

        _BQ.put(3);
        _BQ.take();


      _BQ.offer(4,4, TimeUnit.SECONDS);
        _BQ.poll(4,TimeUnit.SECONDS);

        SynchronousQueue<Integer> integers = new SynchronousQueue<>();
//        integers.put(1);
        integers.offer(2,10,TimeUnit.SECONDS);
        System.out.println("can not be here");
        Lock lock=new ReentrantLock();
        lock.lockInterruptibly();
        Condition condition=lock.newCondition();

    }
}
