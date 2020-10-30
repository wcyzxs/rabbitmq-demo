package rabbitmq.demo.helloWorld;

import org.junit.Test;

/**
 * @ClassName
 * @Description Junit多线程测试
 * 问题： 主线程执行结束就会立刻结束，不会等待子线程得结束。
 * 解决思路： 在子线程执行结束前，阻塞主线程
 * 解决方案： 1.
 * @Autor wcy
 * @Date 2020/10/28 9:21
 */
public class JunitDemo {

    @Test
    public void threadTest() throws InterruptedException {
        //(1)阻塞主线程
        for (int i = 0; i < 3; i ++) {
            new Thread(new ThreadDemo()).start();
        }
        Thread.sleep(100000);

        //(2)使用join
//        DemoThread  thread1 = new DemoThread ();
//        DemoThread  thread2 = new DemoThread ();
//        thread1.start();
//        thread2.start();
//        thread1.join();
//        thread2.join();
       //(3)使用 CountDownLatch
//        CountDownLatch latch = new CountDownLatch(2);
//        DemoThread  thread1 = new DemoThread ();
//        DemoThread  thread2 = new DemoThread ();
//        thread1.start();
//        thread2.start();
//        latch.await();


    }

    class DemoThread  extends   Thread{
        @Override
        public void run() {
            for (int i = 0; i <3 ; i++) {
                System.out.println("当前线程-----"+Thread.currentThread().getId());
            }
        }
    }


    class ThreadDemo implements  Runnable{
        @Override
        public void run() {
            System.out.println("当前线程-----"+Thread.currentThread().getId());
        }
    }


}