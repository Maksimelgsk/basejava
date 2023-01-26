package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume>{

    private final Map<String, Resume> mapResumes = new HashMap<>();

    @Override
    public void clear() {
        mapResumes.clear();
    }

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        mapResumes.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        mapResumes.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume resume) {
        mapResumes.values().remove(resume);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
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
