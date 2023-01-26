package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_SIZE = 10000;
    protected final Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, Integer index) {
        storage[index] = r;
    }

    @Override
    protected void doSave(Resume r, Integer index) {
        if (size >= STORAGE_SIZE) {
            throw new StorageException(r.getUuid(), "ERROR: resume storage is filled");
        } else {
            insertResume(r, index);
            size++;
        }
    }

    @Override
    protected void doDelete(Integer index) {
        fillDeleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> doGetAll() {
        Resume[] newStorage = Arrays.copyOfRange(storage, 0, size);
        return Arrays.asList(newStorage);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void fillDeleteResume(int index);

}
