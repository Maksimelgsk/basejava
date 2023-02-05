package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.StrategyStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StrategyStream storageStrategy;

    protected PathStorage(String dir, StrategyStream storageStrategy) {
        this.directory = Path.of(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(storageStrategy, "storageStrategy must not be null");
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "isn't directory or writable");
        }
        this.storageStrategy = storageStrategy;
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            storageStrategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path update error", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Path update error:", getFileName(path), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error:", getFileName(path), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path get error", getFileName(path), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public void clear() {
        getFiles().forEach(this::doDelete);
    }

/*
    Example using code with lambda expression:

        try (Stream<Path> files = Files.list(directory)) {
  1)
            Consumer<Path> pathConsumer  = this::doDelete;
            files.forEach(pathConsumer);

  2) Unanimous class
            files.forEach(new Consumer<Path>() {
                @Override
                public void accept(Path path) {
                    doDelete(path);
                }
            });

  3) Lambda
            files.forEach(path -> doDelete(path));

  4) Reference method:
            files.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path directory error", getFileName(directory), e);
        }
        */

    @Override
    public int size() {
        return getFiles().toList().size();
    }

    @Override
    protected List<Resume> doGetAll() {
        return getFiles().map(this::doGet).collect(Collectors.toList());
    }

    private Stream<Path> getFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path directory error", getFileName(directory), e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
