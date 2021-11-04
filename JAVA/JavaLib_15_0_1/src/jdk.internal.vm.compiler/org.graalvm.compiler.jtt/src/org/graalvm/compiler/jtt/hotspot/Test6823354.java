/*
 * Copyright (c) 2011, 2020, Oracle and/or its affiliates. All rights reserved.
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


package org.graalvm.compiler.jtt.hotspot;

import java.net.URLClassLoader;

//@formatter:off

/**
 * These methods can be instrinsified by using bit scan, bit test, and population count instructions.
 *
 * @test
 * @bug 6823354
 * @run main/othervm -Xcomp -XX:CompileOnly=Test6823354.lzcomp,Test6823354.tzcomp,.dolzcomp,.dotzcomp Test6823354
 */
// Checkstyle: stop
public class Test6823354 {
    // Arrays of corner case values.
    static final int[]  ia = new int[]  { 0,  1,  -1,  Integer.MIN_VALUE, Integer.MAX_VALUE };
    static final long[] la = new long[] { 0L, 1L, -1L, Long.MIN_VALUE,    Long.MAX_VALUE    };

    public static void main(String[] args) throws Exception {
        // Load the classes and the methods.
        Integer.numberOfLeadingZeros(0);
        Integer.numberOfTrailingZeros(0);
        Long.numberOfLeadingZeros(0);
        Long.numberOfTrailingZeros(0);

        lz();
        tz();
    }

    static void lz() throws Exception {
        // int

        // Test corner cases.
        for (int i = 0; i < ia.length; i++) {
            int x = ia[i];
            check(x, lzcomp(x), lzint(x));
        }

        // Test all possible return values.
        for (int i = 0; i < Integer.SIZE; i++) {
            int x = 1 << i;
            check(x, lzcomp(x), lzint(x));
        }

        String classname = Test6823354.class.getName() + "$lzconI";

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < ia.length; i++) {
            testclass(classname, ia[i]);
        }

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < Integer.SIZE; i++) {
            int x = 1 << i;
            testclass(classname, x);
        }


        // long

        // Test corner cases.
        for (int i = 0; i < ia.length; i++) {
            long x = la[i];
            check(x, lzcomp(x), lzint(x));
        }

        // Test all possible return values.
        for (int i = 0; i < Long.SIZE; i++) {
            long x = 1L << i;
            check(x, lzcomp(x), lzint(x));
        }

        classname = Test6823354.class.getName() + "$lzconL";

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < la.length; i++) {
            testclass(classname, la[i]);
        }

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < Long.SIZE; i++) {
            long x = 1L << i;
            testclass(classname, x);
        }
    }

    static void tz() throws Exception {
        // int

        // Test corner cases.
        for (int i = 0; i < ia.length; i++) {
            int x = ia[i];
            check(x, tzcomp(x), tzint(x));
        }

        // Test all possible return values.
        for (int i = 0; i < Integer.SIZE; i++) {
            int x = 1 << i;
            check(x, tzcomp(x), tzint(x));
        }

        String classname = Test6823354.class.getName() + "$tzconI";

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < ia.length; i++) {
            testclass(classname, ia[i]);
        }

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < Integer.SIZE; i++) {
            int x = 1 << i;
            testclass(classname, x);
        }


        // long

        // Test corner cases.
        for (int i = 0; i < la.length; i++) {
            long x = la[i];
            check(x, tzcomp(x), tzint(x));
        }

        // Test all possible return values.
        for (int i = 0; i < Long.SIZE; i++) {
            long x = 1L << i;
            check(x, tzcomp(x), tzint(x));
        }

        classname = Test6823354.class.getName() + "$tzconL";

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < la.length; i++) {
            testclass(classname, la[i]);
        }

        // Test Ideal optimizations (constant values).
        for (int i = 0; i < Long.SIZE; i++) {
            long x = 1L << i;
            testclass(classname, x);
        }
    }

    static void check(int value, int result, int expected) {
        if (result != expected)
            throw new InternalError(value + " failed: " + result + " != " + expected);
    }

    static void check(long value, long result, long expected) {
        if (result != expected)
            throw new InternalError(value + " failed: " + result + " != " + expected);
    }

    static int lzint( int i)  { return Integer.numberOfLeadingZeros(i); }
    static int lzcomp(int i)  { return Integer.numberOfLeadingZeros(i); }

    static int lzint( long l) { return Long.numberOfLeadingZeros(l); }
    static int lzcomp(long l) { return Long.numberOfLeadingZeros(l); }

    static int tzint( int i)  { return Integer.numberOfTrailingZeros(i); }
    static int tzcomp(int i)  { return Integer.numberOfTrailingZeros(i); }

    static int tzint( long l) { return Long.numberOfTrailingZeros(l); }
    static int tzcomp(long l) { return Long.numberOfTrailingZeros(l); }

    static void testclass(String classname, int x) throws Exception {
        System.setProperty("value", "" + x);
        loadandrunclass(classname);
    }

    static void testclass(String classname, long x) throws Exception {
        System.setProperty("value", "" + x);
        loadandrunclass(classname);
    }

    @SuppressWarnings({"deprecation","unused"})
    static void loadandrunclass(String classname) throws Exception {
        Class<?> cl = Class.forName(classname);
        URLClassLoader apploader = (URLClassLoader) cl.getClassLoader();
        ClassLoader loader = new URLClassLoader(apploader.getURLs(), apploader.getParent());
        Class<?> c = loader.loadClass(classname);
        Runnable r = (Runnable) c.newInstance();
        r.run();
    }

    public static class lzconI implements Runnable {
        static final int VALUE;

        static {
            int value = 0;
            try {
                value = Integer.decode(System.getProperty("value"));
            } catch (Throwable e) {}
            VALUE = value;
        }

        @Override
        public void run() { check(VALUE, lzint(VALUE), dolzcomp()); }
        static int dolzcomp() { return lzcomp(VALUE); }
    }

    public static class lzconL implements Runnable {
        static final long VALUE;

        static {
            long value = 0;
            try {
                value = Long.decode(System.getProperty("value"));
            } catch (Throwable e) {}
            VALUE = value;
        }

        @Override
        public void run() { check(VALUE, lzint(VALUE), dolzcomp()); }
        static int dolzcomp() { return lzcomp(VALUE); }
    }

    public static class tzconI implements Runnable {
        static final int VALUE;

        static {
            int value = 0;
            try {
                value = Integer.decode(System.getProperty("value"));
            } catch (Throwable e) {}
            VALUE = value;
        }

        @Override
        public void run() { check(VALUE, tzint(VALUE), dotzcomp()); }
        static int dotzcomp() { return tzcomp(VALUE); }
    }

    public static class tzconL implements Runnable {
        static final long VALUE;

        static {
            long value = 0;
            try {
                value = Long.decode(System.getProperty("value"));
            } catch (Throwable e) {}
            VALUE = value;
        }

        @Override
        public void run() { check(VALUE, tzint(VALUE), dotzcomp()); }
        static int dotzcomp() { return tzcomp(VALUE); }
    }
}
