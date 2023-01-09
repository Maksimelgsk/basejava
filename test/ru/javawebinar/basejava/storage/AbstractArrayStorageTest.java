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
    private static final String UUID_4 = "uuid_4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1= new Resume(UUID_1);
    private static final Resume RESUME_2= new Resume(UUID_2);
    private static final Resume RESUME_3= new Resume(UUID_3);
    private static final Resume RESUME_4= new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] resumes = storage.getAll();
        assertEquals(0, resumes.length);
    }

    @Test
    public void update() {
        storage.update(RESUME_1);
        storage.update(RESUME_2);
        storage.update(RESUME_3);
        assertSize(3);
        assertSame(RESUME_1, storage.get(UUID_1));
        assertSame(RESUME_2, storage.get(UUID_2));
        assertSame(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveFilled() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_SIZE; i++) {
            try {
                storage.save(new Resume("uuid_" + i));
            } catch (StorageException e) {
                Assert.fail(e.getMessage());
            }
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertGet(RESUME_1);

    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() {
        Resume[] expectedResumes = {RESUME_1, RESUME_2, RESUME_3};
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(expectedResumes[0], resumes[0]);
        Assert.assertEquals(expectedResumes[1], resumes[1]);
        Assert.assertEquals(expectedResumes[2], resumes[2]);
        assertSize(3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertSize(int size){
        assertEquals(size, storage.size());
    }

    public void assertGet(Resume r){
        assertEquals(r, storage.get(r.getUuid()));
    }
}