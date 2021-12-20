package com.atguigu.java;


/*
使用同步代码块解决继承Thread类的方式的线程安全问题
例子：三个窗口卖票，总票数100张 使用继承Thread类的方式实现

说明：在继承Thread类创建多线程的方式中，慎用this充当同步监视器，考虑使用当前类充当同步监视器
存在线程安全问题；待解决。
 */

class window2 extends Thread{

    private static int ticket =100;
    private static Object obj =new Object();


    @Override
    public void run() {
        while (true){
            //正确的
//            synchronized(obj) {
            synchronized (window2.class){//Class clazz=Window2.class,Window2.class只会加载一次
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(getName() + ":卖票，票号为：" + ticket--);
                } else {
                    break;
                }
            }
        }
    }
}

public class WindowTest2 {
    public static void main(String[] args) {
        window2 t1 = new window2();
        window2 t2 = new window2();
        window2 t3 = new window2();

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}