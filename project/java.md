# 一、多线程—进程

## 1.基本概念

程序（program）是为完成特定任务、用某种语言编写的一组指令的集合。即指一段静态的代码，静态对象。

进程（process）是程序的一次性执行过程，**或是正在运行的一个程序**。是一个动态的过程：有它自身的产生、存在和消亡的过程。——生命周期

线程（thread），进程可进一步细化为线程，是一个程序内部的一条执行路径。
	**线程作为调度和执行的单位，每个线程拥有独立的运行栈和程序计数器（pc)**,线程切换开销小。
	一个进程中的多个线程共享相同的内存单元/内存地址空间->他们从同一堆中分配对象，可以访问相同的变量和对象。这就使得线程间更简便、高效。但是多个线程操作共享的系统资源就会带来**安全隐患。**

## 2.线程的创建和使用

方式一：继承Thread类

```
多线程的创建 方式一：继承于Thread类
1，创建一个继承于Thread类的子类
2.重写Thread类中的run()方法
3.创建Thread类的子类对象
4.通过此对象调用start()
```

```
创建多线程的方式二：实现Runnable接口
1.创建一个实现Runnable接口的类
2.实现类去实现Runnable中的抽象方法：run()
3.创建实现类的对象
4.将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
5.通过Thread类的对象调用start()

比较创建多线程的两种方式。
开发中优先选择Runnable接口的方式
原因：1.实现的方式没有类的单继承性的局限性
     2.实现的方式更适合处理多线程有共享数据的情况

联系：Thread类本身也实现了Runnable接口
相同点：两种方式都需要重写run(),将线程要执行的逻辑声明在run()中
	  这两种方法启动线程时都需要使用start()
```

Thread类的常用方法：

```
1.start():启动当前线程；调用当前线程的run()
2.run():通常需要重写Thread类中的此方法，将创建的线程要执行的操作声明在此方法中
3.currentThread():静态方法，返回执行当前代码的线程
4.getName():获取当前线程的名字
5.setName():设置当前线程的名字
6.yield():释放当前cpu的执行权
7.join():在线程A中调用线程B的join()，此时线程A进入阻塞状态，直到线程B完全执行完后，线程A才结束阻塞状态
8.stop():已过时，当执行此方法时，强制结束此当前线程。
9.sleep(long millitime):让当前线程“睡眠”指定的millitime毫秒。在指定的millitime毫秒时间内，当前线程是阻塞状态
10.isAlive():判断当前线程是否存活

线程的优先级
1.
MAX_PRIORITY：10
NORM_PRIORITY:5 ---->默认优先级
MIN_PRIORITY:1
2.如何获取和设置当前线程的优先级：
    getPriority()：获取线程的优先级
    setPriority():设置线程优先级
  说明：高优先级的线程要抢占低优先级线程cpu的执行权。但是只是从概率上讲，高优先级的线程高概率的情况下被执行。
  并不意味这只有高优先级的线程执行完以后，低优先级才执行。
  
 线程的通信：wait()/notify()/notifyAll()；此三个方法定义在Object类中的。
```

## 3.生命周期

JDK中用Thread.State类定义了线程的几种状态：
		新建：一个Thread类或其子类的对象被声明并创建时，该对象为新建状态
		就绪:调用start()后，进入线程队列等待cpu时间片，已具备运行条件，但未分配CPU资源
		运行：就绪的线程被调度并获得CPU资源，便进入运行状态，run()方法定义了线程的操作和功能
		阻塞：在某种特殊情况下，被人为挂起或执行输入输出操作时，让出CPU并临时中断自己的执行，进入阻塞状态
		死亡：线程完成了他的全部工作或线程被提前强制性中止或出现异常导致结束

说明：
			1.生命周期关注两个概念：状态、相应的方法
			2.关注：状态a-->状态b：哪些方法执行了（回调方法）
							某个方法的主动调用：状态a-->状态b
			3.阻塞：临时状态，不可以作为最终状态
			  死亡：最终状态

## 4.线程的同步

解决线程的安全问题
	多个线程执行的不确定性引起执行结果的不稳定
	多个线程对账本的共享，会造成操作的不完整性，会破坏数据。
	java通过同步机制解决线程安全问题：

```
方式一：同步代码块

    synchronized(同步监视器){
    //需要被同步的代码

    }
    说明：1.操作共享数据的代码，即为需要被同步的代码 -->不能包含代码多了，也不能包含代码少了
         2.共享数据：多个线程共同操作的变量。比如:ticket就是共享数据
         3.同步监视器，俗称：锁。任何一个类的对象都可以充当锁
            要求：多个线程必须要共用同一把锁

         补充：再实现Runnable接口创建多线程的方式中，我们可以考虑使用this充当同步监视器
方式二：同步方法
     如果操作共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明同步的。

5. 同步的方式，解决了线程的安全问题。-----好处
   操作同步代码时，只能有一个线程参与，其他线程等待。相当于是一个单线程的过程，效率低。----局限性
   
方式三：Lock锁 ---JDK5.0新增
实现ReentranLock接口
 
 1.面试题：synchronized与lock的异同？
 	同：二者都可以解决线程安全问题
    不同：synchronized机制在执行完相应的同步代码以后，自动的释放同步监视器
    lock需要手动的启动同步(lock())，同时结束同步也需要手动的实现(unlock())
 
 使用的优先顺序：
 	lock-->同步代码块(已经进入了方法题，分配了相应资源)-->同步方法(在方法体之外)
```

线程的死锁问题
	死锁：不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己需要的同步资源，就形成了线程的死锁。
				出现死锁后，不会出现异常，也不会出现提示，只是所有的线程都处于阻塞状态，无法继续。
	解决办法：专门的算法、原则
						尽量减少同步资源的定义
						尽量避免嵌套同步

利弊：
			同步的方式，解决了线程的安全问题。-----好处
   		操作同步代码时，只能有一个线程参与，其他线程等待。相当于是一个单线程的过程，效率低。----局限性

优先使用顺序：Lock->同步代码块->同步方法

## 5.线程的通信

```
1.涉及到的三个方法：
* wait():一旦执行此方法，当前线程就进入阻塞状态，并释放同步监视器
* notify():一旦执行此方法。就会唤醒被wait的一个线程。如果有多个线程被wait，就唤醒优先级高的那个
* notifyALL():一旦执行此方法。就会唤醒所有被wait的线程。
*
2. 说明：
* 1.wait(),notify(),notifyALl()这三个方法必须使用在同步代码块或同步方法中。
* 2.wait(),notify(),notifyALL()这三个方法的调用者必须是同步代码块或同步方法中的同步监视器。
*     否则会出现IllegalMonitorStateException异常
* 3.wait(),notify(),notifyALL()这三个方法定义在java.lang.Object类中
*
* 面试题：sleep()与wait()的异同？
* 相同点：一旦执行方法，都可以使得当前线程进入阻塞状态
* 不同点：1)两个方法声明的位置不同：Thread类中声明sleep()，Object类中声明wait()
*        2)调用的要求不同：sleep()可以再任何需要的场景下调用。wait()必须使用同步代码块或同步方法中。
*        3）关于是否释放同步监视器：若两个方法都使用在同步代码块或同步方法中，sleep()不会释放同步监视器,wait()会释放同步监视器
```

4.小结

**哪些操作不会释放锁**
				1.线程执行同步代码块或同步方法时，程序调用Thread.sleep()、Thread.yield()方法暂停当前线程的执行
				2.线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程挂起，该线程不会释放锁（同步监视器）
						应尽量避免使用suspend()和resume()来控制程序

**哪些操作会释放锁**：
				1.当前线程的同步方法、同步代码块执行结束
				2.当前线程在同步代码块、同步方法中遇到break，return终止了该代码块、该方法的继续执行。
				3.当前线程在同步代码块、同步方法中出现了未处理的Error或Exception，导致异常结束。
				4.当前线程在同步代码块、同步方法中执行力线程对象的wait()方法，当前线程暂停，并释放锁。

## 6.JDK5.0新增的线程创建方式

方式一：实现Callable接口
					与使用Runnable接口相比，Callable功能更强大
						1.相比于run()方法，可以有返回值
						2.方法可以抛异常
						3.支持泛型的返回值
						4.需要借助FutureTask类，比如获取返回结果

```
//创建一个实现Callable的实现类
class NumThread implements Callable {
    //2.实现call方法，将此线程需要执行的操作声明在call()中
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                sum += i;
                System.out.println(i);
            }
        }
        return sum;
    }
}

public class ThreadNew {
    public static void main(String[] args) {
        //3.创建Callable接口实现类的对象
        NumThread numThread = new NumThread();
        //4.将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
        FutureTask futureTask = new FutureTask(numThread);
        //5.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
        new Thread(futureTask).start();

        try {
            //6.获取Callable中call方法的返回值
            //get()返回值即为FutureTask构造器参数Callable实现类重写的Call()返回值
            Object sum=futureTask.get();
            System.out.println("总和为"+sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
```

方式二：使用线程池
			背景：经常创建和销毁、使用量特别大的资源，比如并发情况下的线程，对线程影响很大
			思路：提前创建好多个线程，放入线程池中，使用时直接获取，使用玩放回池中。可以避免频繁创建销毁、实现重复利用。类似于生活中的公共交通工具。
			好处：提升响应速度（减少了创建新线程的时间）
						降低资源消耗（重复利用线程池中线程，不需要每次都创建）
						便于线程管理
									corePoolSize：核心池的大小
									maximumPoolSize：最大线程数
									keepAliveTime：线程没有任务时最多保持多长时间后会中止

```
class NumberThread implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}

class NumberThread1 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if(i%2!=0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}

public class ThreadPool {
    public static void main(String[] args) {
        //1.提供指定线程数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor service1= (ThreadPoolExecutor) service;

        //设置线程池的属性
//        service1.setCorePoolSize(15);
//        service1.setKeepAliveTime();

        //2.执行指定线程的操作。需要提供实现Runnable接口或者Callable接口实现类的对象
        service.execute(new NumberThread());//适合使用Runnable
        service.execute(new NumberThread1());//适合使用Runnable

//        service.submit();//适合使用与Callable
        //关闭连接池
        service.shutdown();
    }
}
```

# 二、常用类

## 1.字符串相关的类

String类，代表字符串；是final类，代表**不可变的字符序列**。

## 2.JDK8之前的日期时间API



## 3.JDK8中新增的日期时间API



## 4.Java比较器



## 5.System类



## 6.Math类



## 7.BigInteger与BigDecimal



