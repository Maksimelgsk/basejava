package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{

    private final Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        mapResumes.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapResumes.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapResumes.values().remove((Resume) searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapResumes.get(uuid);
    }

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    public Resume[] getAll() {
        int size = mapResumes.size();
        List<Resume> resumes = new ArrayList<>(mapResumes.values());
        return resumes.toArray(new Resume[size]);
    }

    @Override
    public int size() {
        return mapResumes.size();
    }
}
