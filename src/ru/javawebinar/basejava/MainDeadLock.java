package ru.javawebinar.basejava;

public class MainDeadLock {
    private static final Object OBJECT_1 = new Object();
    private static final Object OBJECT_2 = new Object();

    public static void main(String[] args) {
        deadLock(OBJECT_1, OBJECT_2);
        deadLock(OBJECT_2, OBJECT_1);
    }

    private static void deadLock(Object object1, Object object2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " starts");
            synchronized (object1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " grabs object1");

                synchronized (object2) {
                    System.out.println(Thread.currentThread().getName() + " grabs object2");
                }
                System.out.println(Thread.currentThread().getName() + " ends");
            }
        }).start();
    }
}
