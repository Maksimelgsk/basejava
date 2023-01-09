package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_NOT_EXIST = "dumm";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume1 = new Resume(UUID_1);
        Resume newResume2 = new Resume(UUID_2);
        Resume newResume3 = new Resume(UUID_3);
        storage.update(newResume1);
        storage.update(newResume2);
        storage.update(newResume3);
        Assert.assertEquals(3, storage.size());
        assertSame(newResume1, storage.get(UUID_1));
        assertSame(newResume2, storage.get(UUID_2));
        assertSame(newResume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume newResume = new Resume("dummy");
        storage.update(newResume);
    }

    @Test
    public void save() {
        Resume r = new Resume("uuid_4");
        storage.save(r);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume r = new Resume("uuid_1");
        storage.save(r);
    }

    @Test(expected = StorageException.class)
    public void saveFilled() {
        for (int i = 4; i <= AbstractArrayStorage.STORAGE_SIZE; i++) {
            try {
                storage.save(new Resume("uuid_" + i));
            } catch (StorageException e) {
                Assert.fail(e.getMessage());
            }
        }
        storage.save(new Resume());
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());

    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid_4");
    }

    @Test
    public void get() {
        Resume r1 = new Resume(UUID_1);
        Resume r2 = new Resume(UUID_2);
        Resume r3 = new Resume(UUID_3);
        Assert.assertEquals(r1, storage.get(r1.getUuid()));
        Assert.assertEquals(r2, storage.get(r2.getUuid()));
        Assert.assertEquals(r3, storage.get(r3.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume r1 = new Resume(UUID_1);
        Resume r2 = new Resume(UUID_2);
        Resume r3 = new Resume(UUID_3);
        Resume[] resume = storage.getAll();
        Assert.assertEquals(r1, resume[0]);
        Assert.assertEquals(r2, resume[1]);
        Assert.assertEquals(r3, resume[2]);
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}