package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(file, r);
        } catch (IOException e) {
            throw new StorageException("I/O update error", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("I/O save error:", file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doDelete(File file) {
        if (file.delete()) {
            System.out.println("File" + file.getName() + "successfully deleted");
        } else {
            throw new StorageException("I/O delete error", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("I/O delete error", file.getName());
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        List<Resume> list = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                try {
                    list.add(doRead(file));
                } catch (IOException e) {
                    throw new StorageException("I/O directory error", file.getName(), e);
                }
            }
        }
        return list;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files != null) {
            return files.length;
        } else {
            throw new StorageException("I/O directory error", directory.getName());
        }
    }

    protected abstract void doWrite(File file, Resume r) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
