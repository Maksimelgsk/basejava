package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectStreamFileStorage extends AbstractStorage<File> {

    private final File directory;
    private final StrategyStream storageStrategy;

    protected ObjectStreamFileStorage(File directory, StrategyStream storageStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(storageStrategy, "storageStrategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "isn't directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "isn't readable/writeable");
        }
        this.directory = directory;
        this.storageStrategy = storageStrategy;
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            storageStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
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
            return storageStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
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
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("I/O directory error", directory.getName());
        } else{
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] filesList = directory.list();
        if (filesList != null) {
            return filesList.length;
        }
        return 0;
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        List<Resume> list = new ArrayList<>();
        if (files == null) {
            throw new StorageException("I/O directory error", directory.getName());
        } else {
            for (File file : files) {
                list.add(doGet(file));
            }
        }
        return list;
    }
}
