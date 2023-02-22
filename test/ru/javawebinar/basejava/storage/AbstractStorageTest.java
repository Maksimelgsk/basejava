package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.ResumeTestData;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.model.ContactType.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final String FULL_NAME_1 = "Name_1";
    private static final String FULL_NAME_2 = "Name_2";
    private static final String FULL_NAME_3 = "Name_3";
    private static final String FULL_NAME_4 = "Name_4";

    static {
        RESUME_1 = ResumeTestData.createResume(UUID_1, FULL_NAME_1);
        RESUME_2 = ResumeTestData.createResume(UUID_2, FULL_NAME_2);
        RESUME_3 = ResumeTestData.createResume(UUID_3, FULL_NAME_3);
        RESUME_4 = ResumeTestData.createResume(UUID_4, FULL_NAME_4);
    }

    protected AbstractStorageTest(Storage storage) {
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
        List<Resume> resumes = storage.getAllSorted();
        assertEquals(0, resumes.size());
    }

    @Test
    public void update() {
        Resume r1 = new Resume(UUID_1, "newName1");
        Resume r2 = new Resume(UUID_2, "newName2");
        Resume r3 = new Resume(UUID_3, "newName3");
        RESUME_1.setContacts(PHONE, "+7-000-999-99-99");
        RESUME_1.setContacts(SKYPE, "@skype_Name_1");
        RESUME_1.setContacts(EMAIL, "mail@yahoo.com");
        RESUME_1.setContacts(LINKEDIN, "@linkedid_Name_1");
        RESUME_1.setContacts(GITHUB, "@gitHud_Name_1");
        RESUME_1.setContacts(STACKOVERFLOW, "@stackoverflow_Name_1");
        RESUME_1.setContacts(HOME_PAGE, "www.google.ru");
        storage.update(r1);
        storage.update(r2);
        storage.update(r3);
        assertSize(3);
        assertEquals(r1, storage.get(UUID_1));
        assertEquals(r2, storage.get(UUID_2));
        assertEquals(r3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST));
        storage.get(UUID_NOT_EXIST);
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
    public void getAllSorted() {
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(3, actualResumes.size());
        assertEquals(actualResumes, expectedResumes);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    public void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}