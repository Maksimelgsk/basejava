import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        System.out.println("The resume storage is cleared");
        storageSize = 0;
    }

    public void save(Resume r) {
        if (storageSize < storage.length) {
            storage[storageSize++] = r;
            System.out.print("Saved uuid = " + r + "\n");
        } else {
            System.out.println("The resume storage is full");
        }
    }

    public Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume == null || resume.uuid.equals(uuid)) {
                System.out.print("Selected uuid = ");
                return resume;
            }
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (storage[index].uuid.equals(uuid)) {
            storageSize--;
            System.arraycopy(storage, index + 1, storage, index, storageSize - index);
            storage[storageSize] = null;
            System.out.println("Deleted uuid = " + uuid);
        } else {
            System.out.println("The uuid = " + uuid + " is not exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        System.out.println("All uuid in resume storage:");
        return Arrays.copyOf(storage, storageSize);
    }

    public int size() {
        while (storage[storageSize] != null) {
            storageSize++;
        }
        System.out.println("Size resume storage: ");
        return storageSize;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return (storageSize - 1);
    }
}
