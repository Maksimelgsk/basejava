package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> {
        if (o1.getFullName().compareTo(o2.getFullName()) == 0) {
            return o1.getUuid().compareTo(o2.getUuid());
        } else {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    };

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumeList = doGetAll();
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exists");
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract void doUpdate(Resume r, SK searchKey);

    protected abstract void doSave(Resume r, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract List<Resume> doGetAll();

}
