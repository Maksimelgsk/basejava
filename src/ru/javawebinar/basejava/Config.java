package ru.javawebinar.basejava;

import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("/Users/maksimkonovalov/Desktop/basejava/config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            storage = new SqlStorage(url, user, password);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    private static File getHomeDir(){
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if(!homeDir.isDirectory()){
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }
}