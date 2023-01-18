package ru.javawebinar.basejava.storage;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_SIZE = 10000;
    protected final Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage[(int) searchKey] = r;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        if (size >= STORAGE_SIZE) {
            throw new StorageException(r.getUuid(), "ERROR: resume storage is filled");
        } else {
            insertResume(r, (int) searchKey);
            size++;
        }
    }
    @Override
    protected void doDelete(Object searchKey){
        fillDeleteResume((int)searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Object searchKey){
        return storage[(int)searchKey];
    }

    @Override
    protected boolean isExist(Object searchKey){
        return (int)searchKey >= 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void insertResume(Resume r, int searchKey);

    protected abstract void fillDeleteResume(int searchKey);

}
