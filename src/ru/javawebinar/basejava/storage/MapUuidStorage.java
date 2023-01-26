package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> mapUuid = new HashMap<>();

    @Override
    public void clear() {
        mapUuid.clear();
    }

    @Override
    protected void doUpdate(Resume r, String uuid) {
        mapUuid.put(uuid, r);
    }

    @Override
    protected void doSave(Resume r, String uuid) {
        mapUuid.put(uuid, r);
    }

    @Override
    protected void doDelete(String uuid) { mapUuid.keySet().remove(uuid);}

    @Override
    protected Resume doGet(String uuid) {
        return mapUuid.get(uuid);
    }

    @Override
    protected boolean isExist(String uuid) {
       return mapUuid.containsKey(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
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
