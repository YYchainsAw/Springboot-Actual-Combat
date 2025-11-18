package com.yychainsaw;

import org.junit.Test;

public class ThreadLocalTest {

    @Test
    public void TestThreadLocal() {
        ThreadLocal tl = new ThreadLocal();

        new Thread(()->{
            tl.set("源氏");
            System.out.println(Thread.currentThread().getName()+":"+" "+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+" "+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+" "+tl.get());
        }, "蓝色").start();

        new Thread(()->{
            tl.set("半藏");
            System.out.println(Thread.currentThread().getName()+":"+" "+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+" "+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+" "+tl.get());
        }, "绿色").start();
    }
}
