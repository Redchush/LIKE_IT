package testUtil.metadata_api.randomizer;

import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;
import testUtil.missalenious.PathResolver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringRandom {

    private static final String default_ru = "/data/dict_ru_UTF8.txt";
    private static final  String default_en = "/data/dict_en_UTF8.txt";
    private static final  String defalultEmailPath = "/data/email_appendix.txt";

    private List<String> dictionary_ru;
    private List<String> dictionary_en;
    private List<String> emailAppendix;
    private int maxStringLength;


    private static StringRandom instance;
    private volatile boolean isInit;

    private StringRandom() {
        isInit = false;
    }

    public static StringRandom getInstance() {

        if (instance == null)
            synchronized (StringRandom.class) {
                if (instance == null)
                    instance = new StringRandom();
            }
        return instance;
    }

    public static void destroy(){
        synchronized (StringRandom.class) {
            instance = null;
        }
    }

    public void init(String dictionary_ru_path, String dictionary_en_path, String emailAppendixPath)
            throws StringRandomizeInitException {
       try {
           Path parentPath = PathResolver.getClassPathInFileSystem(this.getClass()).getParent();
           Path path_ru_Dictionary = (dictionary_ru_path != null) ? Paths.get(dictionary_ru_path)
                                                       : PathResolver.getPathByUrlString(parentPath, default_ru);
           Path path_en_Dictionary = (dictionary_ru_path != null) ? Paths.get(dictionary_en_path)
                                                       : PathResolver.getPathByUrlString(parentPath, default_en);
           Path pathEmail = (emailAppendixPath != null) ? Paths.get(emailAppendixPath)
                                                        :PathResolver.getPathByUrlString(parentPath, defalultEmailPath);
           dictionary_ru = Files.lines(path_ru_Dictionary).collect(Collectors.toList());
           dictionary_en = Files.lines(path_en_Dictionary).collect(Collectors.toList());
           emailAppendix = Files.lines(pathEmail).collect(Collectors.toList());
       } catch (IOException e) {
           throw new StringRandomizeInitException("Invalid paths of dictionaries", e);
       }
       synchronized (this){
           isInit = true;
       }
    }

    public String getEnglishRandom(int max) {
        return getTextRandom(max, dictionary_en);
    }

    public String getRussianRandom(int max){
       return getTextRandom(max, dictionary_ru);
    }

    private String getTextRandom(int lenght, List<String> dict){
        StringBuilder builder = new StringBuilder();
        if (lenght<40){
            appendRandom(builder, dict);
        } else {
            int i1 = lenght / 3;
            for (int i = 0; i < i1; i++) {
                appendRandom(builder, dict).append(" ");
                if (((int) (Math.random()*lenght)) %i1 == 0){
                    break;
                }
            }
        }
        String result = builder.toString();
        if (builder.length() > lenght){
            result = builder.substring(0, lenght);
        }
        return result;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }

    public void setMaxStringLength(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    private StringBuilder appendRandom(StringBuilder builder, List<String> dictionary){
        int random = (int) (Math.random()* (dictionary.size()-1));
        return builder.append(dictionary.get(random));
    }

    public String getRandomEmail(int length){

        StringBuilder builder = new StringBuilder();
        appendRandom(builder,dictionary_en);
        appendRandom(builder, emailAppendix);
        String result = builder.toString();
        int diff = result.length() - length;
        if (diff > 0){
            result = result.substring(diff + 1, result.length());
        }
        return result;
    }

    public boolean isInit() {
        return isInit;
    }
}
