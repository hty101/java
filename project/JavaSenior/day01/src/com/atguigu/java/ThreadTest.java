package com.atguigu.java;

/*
多线程的创建 方式一：继承于Thread类
 1，创建一个继承于Thread类的子类
 2.重写Thread类中的run()方法
 3.创建Thread类的子类对象
 4.通过此对象调用start()

 例子：遍历100以内的所有的偶数
 */
class MyThread extends Thread{
    //2.重写Thread类中的run()方法


    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }

        }
    }
}
public class ThreadTest {
    public static void main(String[] args) {
        //3.创建Thread类的子类对象
        MyThread t1 = new MyThread();
        // 4.通过此对象调用start()1启动当前线程2.调用当前线程的run()
        t1.start();
        /*
        问题一：不可以直接调用run()的方式启动线程
         */
        //t1.run();

        //问题而：再启动一个线程，遍历100内的偶数。不可以让已经start的线程去执行，会报异常
        //我们需要重新创建一个线程的对象，再去start
        MyThread t2 = new MyThread();
        t2.start();

        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }

        }
    }
}
