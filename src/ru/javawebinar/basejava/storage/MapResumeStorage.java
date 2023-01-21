package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{

    private final Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        mapResumes.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        mapResumes.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object resume) {
        mapResumes.values().remove((Resume) resume);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapResumes.get(uuid);
    }


    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(mapResumes.values());
    }

    @Override
    public int size() {
        return mapResumes.size();
    }
}
