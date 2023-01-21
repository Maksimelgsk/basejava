package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

    private final Map<String, Resume> mapUuid = new HashMap<>();

    @Override
    public void clear() {
        mapUuid.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object uuid) {
        mapUuid.put((String) uuid, r);
    }

    @Override
    protected void doSave(Resume r, Object uuid) {
        mapUuid.put((String) uuid, r);
    }

    @Override
    protected void doDelete(Object uuid) { mapUuid.keySet().remove((String) uuid);}

    @Override
    protected Resume doGet(Object uuid) {
        return mapUuid.get((String) uuid);
    }

    @Override
    protected boolean isExist(Object uuid) {
       return mapUuid.containsKey((String) uuid);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(mapUuid.values());
    }


    @Override
    public int size() {
        return mapUuid.size();
    }
}
