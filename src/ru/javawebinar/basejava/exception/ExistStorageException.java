package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super(uuid, "ERROR: resume " + uuid + " already exists");
    }
}
