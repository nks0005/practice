/*
 * Copyright (c) 2007, 2018, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
/*
 */


package org.graalvm.compiler.jtt.threads;

import org.graalvm.compiler.jtt.JTTTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class Object_wait04 extends JTTTest {

    @Rule public TestRule timeout = createTimeoutSeconds(20);

    private static class TestClass implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {

            }
            synchronized (object) {
                done = true;
                object.notifyAll();
            }
        }
    }

    static volatile boolean done;
    static final Object object = new Object();
    static int sleep;

    public static boolean test(int i) throws InterruptedException {
        done = false;
        sleep = i * 50;
        synchronized (object) {
            new Thread(new TestClass()).start();
            dowait(i);
        }
        return done;
    }

    private static void dowait(int i) throws InterruptedException {
        if (i == 0) {
            while (!done) {
                object.wait(100);
            }
        } else {
            synchronized (object) {
                dowait(i - 1);
            }
        }
    }

    @Test
    public void run0() throws Throwable {
        runTest("test", 0);
    }

    @Test
    public void run1() throws Throwable {
        runTest("test", 1);
    }

    @Test
    public void run2() throws Throwable {
        runTest("test", 2);
    }

    @Test
    public void run3() throws Throwable {
        runTest("test", 3);
    }

    @Test
    public void run4() throws Throwable {
        runTest("test", 4);
    }

    @Test
    public void run5() throws Throwable {
        runTest("test", 5);
    }

}