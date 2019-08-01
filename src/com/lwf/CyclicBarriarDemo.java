package com.lwf;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * author Administrator
 * time 2019-08-01
 */
public class CyclicBarriarDemo {
    public static void main(String[] args) {

        MySource mySource=new MySource(new CyclicBarrier(5,()->{
            System.out.println("全部等待到达，回调开始");
        }));
        for (int i = 0; i < 5; i++) {
            new Thread(mySource::donePart, String.valueOf(i)).start();
        }
        System.out.println("--------"+"\r\n 执行完成");
    }

}
class MySource{
    private CyclicBarrier _CB;

    public MySource(CyclicBarrier _CB) {
        this._CB = _CB;
    }
    public void donePart()  {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (_CB.getNumberWaiting()!= _CB.getParties()){
            try {
                _CB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"  等待完成，执行完毕");

    }


}
