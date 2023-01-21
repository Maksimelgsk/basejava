package ru.javawebinar.basejava.storage;


import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import static org.junit.Assert.fail;

public class SortedArrayStorageTest extends AbstractStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_SIZE; i++) {
            try {
                storage.save(new Resume("uuid_" + i));
            } catch (StorageException e) {
                fail(e.getMessage());
            }
        }
        storage.save(new Resume("Error overflow"));
    }
}