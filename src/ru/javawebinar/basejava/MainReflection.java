package ru.javawebinar.basejava;
import ru.javawebinar.basejava.model.Resume;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Class<? extends Resume> rClass = r.getClass();
        Field field = rClass.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println("Rename field Resume: " + r);
        Method method = rClass.getMethod("toString");
        System.out.println("Get method toString: " + method);
        System.out.println("Invoke method toString: " + method.invoke(r));
    }
}
