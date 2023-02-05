package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super(uuid, "ERROR: resume " + uuid + " is not exist");
    }
}
