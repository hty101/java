package com.atguigu.java;

/*
使用同步方法解决实现Runnable接口的线程安全问题
 */
class window3 implements Runnable{

    private int ticket=100;
    Object obj=new Object();

    @Override
    public void run() {
        while (true){
            show();
        }
    }

    private synchronized void show(){//同步监视器：this
        if (ticket>0){

            System.out.println(Thread.currentThread().getName()+":卖票，票号为"+ticket--);
        }
    }
}

public class WindowTest3 {
    public static void main(String[] args) {
        window3 w = new window3();
        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);

        t1.setName("窗口1：");
        t2.setName("窗口2：");
        t3.setName("窗口3：");

        t1.start();
        t2.start();
        t3.start();
    }
}
