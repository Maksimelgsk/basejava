package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r, int index){
        int newIndex = Math.abs(index) - 1;
        System.arraycopy(storage, newIndex, storage, newIndex + 1,size - newIndex);
        storage[newIndex] = r;
    }
    @Override
    protected void fillDeleteResume(int index){
        System.arraycopy(storage, index + 1, storage, index,size - index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
