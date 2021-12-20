package com.atguigu.java1;

/*
使用同步机制将单例模式中的懒汉式改写成线程安全的
 */
public class BankTest {
}
class Bank{
    private static Bank instance=null;

    private Bank(){    }

    public static Bank getInstance(){
        //方式一：效率稍差
//        synchronized (Bank.class) {
//            if(instance==null){
//                instance=new Bank();
//            }
//            return instance;
//        }
        //方式二
        if (instance == null) {
            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }

            }
        }
        return instance;
    }
}