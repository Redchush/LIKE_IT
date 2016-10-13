package testUtil.missalenious;



import by.epam.like_it.dao.AbstractRealEntityDao;
import by.epam.like_it.model.vo.db_vo.EntityVO;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class ReflectionHelper {

    public static String packagePath (Class clazz){
        String PREFIX = clazz.getPackage().getName();
        return PREFIX;
    }

    public static List<Class> findClassesInSamePackage(Class clazz, boolean isSuperclass)  {
        ArrayList<Class> classes = new ArrayList<>();

        URL resource =  clazz.getProtectionDomain().getCodeSource().getLocation();

        String total = resource.toString() + clazz.getName().replace('.', '/') + ".class";
        URI uri = URI.create(total);

        Path classPath = Paths.get(uri);
        Path packagePath = classPath.getParent();
        String PREFIX = clazz.getPackage().getName();
        try {
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
        } catch (IOException e) {
            System.out.println("can't make class");
        }
        return classes;
    }


    public static void createIntanses(List<AbstractRealEntityDao> instanseList, List<Class> classes, Connection connection) {
        for (Class clazz : classes) {
            try {
                AbstractRealEntityDao dao = (AbstractRealEntityDao) clazz.newInstance();
                instanseList.add(dao);
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("Check the package with daos");
            }
        }
    }
}
