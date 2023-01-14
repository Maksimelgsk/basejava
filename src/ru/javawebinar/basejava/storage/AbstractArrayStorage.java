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
    protected void doUpdate(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    protected void doSave(Resume r, int index) {
        if (size >= STORAGE_SIZE) {
            throw new StorageException(r.getUuid(), "ERROR: resume storage is filled");
        } else {
            insertResume(r, index);
            size++;
        }
    }
    @Override
    protected void doDelete(int index){
        fillDeleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(int index){
        return storage[index];
    }

    @Override
    protected boolean isExist(int index){
        return index >= 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
    protected abstract void insertResume(Resume r, int index);
    protected abstract void fillDeleteResume(int index);

}
