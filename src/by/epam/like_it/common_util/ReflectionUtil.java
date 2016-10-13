package by.epam.like_it.common_util;


import by.epam.like_it.model.bean.User;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtil {

    private static final String ID_FIELD_NAME = "id";

    public static boolean containsField(Class clazz, String field){
        boolean result = false;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].getName().equals(field)){
                result = true;
            }
        }
        return result;
    }

    public static List<String> getNotNullFieldsList(Object entity) {
        return getNotNullFieldsStream(entity).collect(Collectors.toList());
    }

    public static Stream<String> getNotNullFieldsStream(Object entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        return Arrays.stream(declaredFields).filter(s -> {
            s.setAccessible(true);
            try {
                return s.get(entity) != null;
            } catch (IllegalAccessException e) {
                return false;
            } finally {
                s.setAccessible(false);
            }
        }).map(Field::getName);
    }

    public static Stream<String> getNotNullFieldsExceptIdStream(Object entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
//        boolean isIdFind = false;
        return Arrays.stream(declaredFields).filter(s -> {
            s.setAccessible(true);
            try {
                return !isIdField(s) && s.get(entity) != null;
            } catch (IllegalAccessException e) {
                return false;
            } finally {
                s.setAccessible(false);
            }
        }).map(Field::getName);
    }

    public static List<String> getNotNullFieldsExceptIdList(Object entity) {
        return getNotNullFieldsExceptIdStream(entity).collect(Collectors.toList());
    }

    public static boolean isIdField(Field field){
        return field.getName().equals(ID_FIELD_NAME);
    }

    public static boolean isClassMaintainInterface (Class classToCheck, Class infTocCheck) {
        Class[] inf = classToCheck.getInterfaces();
        return checkInfRecursively(infTocCheck, inf);
    }

    private static boolean checkInfRecursively(Class infToCheck, Class[] interfaces){
        boolean result = false;
        for (Class cl : interfaces) {
            if (cl.equals(infToCheck)) {
                return true;
            }
            Class[] interfaces1 = cl.getInterfaces();
            if (interfaces1.length > 0){
                return checkInfRecursively(infToCheck, interfaces1);
            }
        }
        return result;
    }

    public static List<Class> getAllBeanClasses() throws IOException {
        return findClassesInSamePackage(User.class, false);
    }

    public static List<Class> findClassesInSamePackage(Class clazz, boolean isSuperclass) throws IOException {
        ArrayList<Class> classes = new ArrayList<>();
        URL resource =  clazz.getProtectionDomain().getCodeSource().getLocation();

        String total = resource.toString() + clazz.getName().replace('.', '/') + ".class";
        URI uri = URI.create(total);

        Path classPath = Paths.get(uri);
        Path packagePath = classPath.getParent();
        String PREFIX = clazz.getPackage().getName();

        Class finalC = clazz;
        Files.walkFileTree(packagePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                String path = file.getFileName().toString();
                String className = path.substring(0, path.length() - 6);
                String fullClassName = PREFIX + "." +className;
                try {
                    Class clazz = Class.forName(fullClassName);
                    if (isSuperclass){
                        if (clazz.getSuperclass().equals(finalC)) {
                            classes.add(clazz);
                        }
                    } else {
                        classes.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                        /*NOP because it's not a class*/
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
        return classes;
    }

}
