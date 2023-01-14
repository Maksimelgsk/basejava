package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage{
    List<Resume> listResumes = new ArrayList<>();

    @Override
    public void clear() {
        listResumes.clear();
    }

    @Override
    protected void doUpdate(Resume r, int index) {
        listResumes.set(index, r);
    }

    @Override
    protected void doSave(Resume r, int index) {
        listResumes.add(r);
    }

    @Override
    protected void doDelete(int index) {
        listResumes.remove(index);
    }
    @Override
    protected Resume doGet(int index) {
        return listResumes.get(index);
    }

    @Override
    protected boolean isExist(int index) {
        return index >= 0;
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
    protected int getIndex(String uuid) {
        for (int i = 0; i < listResumes.size(); i++){
            if(listResumes.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
