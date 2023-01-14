package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public int getExistException(String uuid){
        int index = getIndex(uuid);
        if(!isExist(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            return index;
        }
    }

    public int getNotExistException(String uuid){
        int index = getIndex(uuid);
        if(isExist(index)) {
            throw new ExistStorageException(uuid);
        } else {
            return index;
        }
    }
    public void update(Resume r) {
        int index = getExistException(r.getUuid());
        doUpdate(r, index);
    }
    public void save(Resume r) {
        int index = getNotExistException(r.getUuid());
        doSave(r, index);
    }

    public void delete(String uuid) {
        int index = getExistException(uuid);
        doDelete(index);
    }

    public Resume get(String uuid) {
        int index = getExistException(uuid);
        return doGet(index);
    }

    protected abstract void doUpdate(Resume r, int index);
    protected abstract void doSave(Resume r, int index);
    protected abstract void doDelete(int index);
    protected abstract Resume doGet(int index);
    protected abstract boolean isExist(int index);
    protected abstract int getIndex(String uuid);
}
