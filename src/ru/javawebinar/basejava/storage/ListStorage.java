package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> listResumes = new ArrayList<>();

    @Override
    public void clear() {
        listResumes.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        listResumes.set((int) index, r);
    }

    @Override
    protected void doSave(Resume r, Object index) {
        listResumes.add(r);
    }

    @Override
    protected void doDelete(Object index) {
        listResumes.remove((int) index);
    }

    @Override
    protected Resume doGet(Object index) {
        return listResumes.get((int) index);
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
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

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(listResumes);
    }
    @Override
    public int size() {
        return listResumes.size();
    }
}
