package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ObjectStreamPathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StrategyStream storageStrategy;

    protected ObjectStreamPathStorage(String dir, StrategyStream storageStrategy) {
        directory = Path.of(dir);
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
            throw new StorageException("Path update error:", path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error:", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path get error", path.getFileName().toString(), e);
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
        try (Stream<Path> files = Files.list(directory)) {

//  -> Unanimous class
//            files.forEach(new Consumer<Path>() {
//                @Override
//                public void accept(Path path) {
//                    doDelete(path);
//                }
//            });
//  -> Lambda
//            files.forEach(path -> doDelete(path));
//  -> Reference method:
            files.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> files = Files.list(directory)) {
            return files.toList().size();
        } catch (IOException e) {
            throw new StorageException("Path size error", directory.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> list = new ArrayList<>();
        try (Stream<Path> files = Files.list(directory)) {
            files.forEach(path -> list.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("Path directory error", directory.getFileName().toString(), e);
        }
        return list;
    }
}
