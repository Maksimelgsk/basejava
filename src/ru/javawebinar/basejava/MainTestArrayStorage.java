package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.MapResumeStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    /**
     * MainTestArrayStorage implement ListStorage class
     * For run MainTestArrayStorage also can be used codes below:
     * private static final Storage ARRAY_STORAGE = new ArrayStorage();
     * private static final Storage ARRAY_STORAGE = new SortedArrayStorage();
     * private static final Storage ARRAY_STORAGE = new ListStorage();
     * private final static Storage ARRAY_STORAGE = new MapUuidStorage();
     */
    private static final Storage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {
        final Resume r3 = new Resume("uuid3", "Name3");
        final Resume r2 = new Resume("uuid2", "Name2");
        final Resume r1 = new Resume("uuid1", "Name1");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.update(r2);
        ARRAY_STORAGE.update(r3);
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
