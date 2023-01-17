package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> listResumes = new ArrayList<>();

    @Override
    public void clear() {
        listResumes.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        listResumes.set((int) searchKey, r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        listResumes.add(r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        listResumes.remove((int) searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return listResumes.get((int) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public Resume[] getAll() {
        Resume[] r = new Resume[listResumes.size()];
        return listResumes.toArray(r);
    }

    @Override
    public int size() {
        return listResumes.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < listResumes.size(); i++) {
            if (listResumes.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
