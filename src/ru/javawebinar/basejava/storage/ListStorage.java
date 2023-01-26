package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> listResumes = new ArrayList<>();

    @Override
    public void clear() {
        listResumes.clear();
    }

    @Override
    protected void doUpdate(Resume r, Integer index) {
        listResumes.set(index, r);
    }

    @Override
    protected void doSave(Resume r, Integer index) {
        listResumes.add(r);
    }

    @Override
    protected void doDelete(Integer index) {
        listResumes.remove((int) index);
    }

    @Override
    protected Resume doGet(Integer index) {
        return listResumes.get(index);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
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
