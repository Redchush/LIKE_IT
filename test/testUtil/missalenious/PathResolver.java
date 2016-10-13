package testUtil.missalenious;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathResolver {
    private static Pattern pattern = Pattern.compile("\\.\\.");

    public static Path getClassPathInFileSystem(Class clazz){
        String resource = clazz.getSimpleName() + ".class";
        String path1 = clazz.getResource(resource).getPath();
        return Paths.get(path1.substring(File.separator.length()));
    }


    public static Path getFileRelativeClass(Class clazz, String urlString){
        Path classPathInFileSystem = getClassPathInFileSystem(clazz);
        return getPathByUrlString(classPathInFileSystem.getParent(), urlString);
    }
    /**
     * get path by path of
     * @param path - relative which saved string
     * @param urlString - string without crops relative to main path
     * @return
     */

    public static Path getPathByUrlString(Path path, String urlString){
        int[] info = getLevel(urlString);
        for (int i = 0; i < info[0]; i++) {
            path = path.getParent();
        }
        File file = path.toFile();
        return new File(file, urlString.substring(info[1])).toPath();
    }

    private static int[] getLevel(String urlString){
        Matcher matcher = pattern.matcher(urlString);
        int result = 0;
        int last = 0;
        while (matcher.find()){
            result++;
            last = matcher.end();
        }
        return new int[]{result, last};
    }
}
