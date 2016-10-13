package testUtil.missalenious;

import testUtil.metadata_api.randomizer.StringRandom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


public class PathTip {

    public static void main(String[] args) throws IOException {
        showConstants();
        showFile();
        showSource();
        showResolve();


        System.out.println(StringRandom.class.getResource("StringRandom.class").getPath());

        String property = System.getProperty("java.class.path");
        System.out.println(property);
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        System.out.println(Arrays.toString(classpathEntries));
        String absolutePath = new File(".").getAbsolutePath();
        System.out.println(absolutePath);
        final File f = new File(StringRandom.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println(f.getPath());
        File file = new File("\\data\\dict_ru_UTF8.txt");

        String absolutePath1 = file.getAbsolutePath();
        System.out.println(absolutePath1);
//        List<String> collect = Files.lines(path).collect(Collectors.toList());
//        System.out.println(collect.size());
    }

    public static void showConstants(){
        System.out.println("File.pathSeparator: "+ File.pathSeparator);
        System.out.println("File.pathSeparatorChar: "+File.pathSeparatorChar);

        System.out.println("File.separator: " + File.separator);
        System.out.println("File.separatorChar: " + File.separatorChar);
    }

    public static void showFile(){
        File file = new File("/");
        System.out.println("file.getPath()" + file.getPath());
    }
    public static void showPath(){
        Path path = Paths.get("data\\test.txt");
        System.out.println(path.toAbsolutePath());
    }

    public static void showSource(){
        String path1 = PathTip.class.getResource("PathTip.class").getPath();
        System.out.println(" path1 "  + path1);
        Path pathSubstring_separator = Paths.get(path1.substring(File.separator.length()));
        System.out.println("pathSubstring_separator: " + pathSubstring_separator);
        System.out.println("pathSubstring_separator.getParent(): " + pathSubstring_separator.getParent());

    }
    public static void showResolve(){
        System.out.println("RESOLVE: ");
        Path classPathInFileSystem = PathResolver.getClassPathInFileSystem(PathTip.class);

        System.out.println("classPathInFileSystem: " + classPathInFileSystem);
        System.out.println("resolveSibling /data/test.txt: " + classPathInFileSystem.resolveSibling("/data/test.txt"));
        System.out.println("resolve /data/test.txt: " + classPathInFileSystem.resolve("/data/test.txt"));
    }

}
