package productivity;

import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.model.bean.User;
import org.junit.Assert;
import org.junit.Test;
import testUtil.missalenious.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class TestCashe {

    private static ConcurrentHashMap<String, String> cashMap = new ConcurrentHashMap<>();

    @Test
    public void testEq(){

        String simpleName = User.class.getSimpleName();
        String simpleName1 = new User().getClass().getSimpleName();
        User user = new User();
        user.setAboutMe("blabla");

        String simpleName3 = user.getClass().getSimpleName();
        int code1 = simpleName.hashCode();
        int code2 = simpleName1.hashCode();
        int code3 = simpleName3.hashCode();

        Assert.assertEquals(code1, code2);
        Assert.assertEquals(code2, code3);

        User user2 = new User();
        user2.setAboutMe("blabla");
        Assert.assertEquals(user.hashCode(), user2.hashCode());
    }

    /**
     * 2000 ms in average for create one Create
     */
    @Test
    public void testFind(){
        System.out.println(getCreateTime(User.class));
    }
    @Test
    public void testFindALL() {
        List<Class> allBeanClass = getAllBeanClass();
        System.out.println("size " + allBeanClass.size() + " " + getCreateAllTime(allBeanClass));
    }
    /**
     * about 1700 - one
     * about 3065 - 1000[damn caching]
     */
    @Test
    public void testFindAllMultiple(){
        List<Class> allBeanClass = getAllBeanClass();
        System.out.println("size " + allBeanClass.size() + " " + getCreateAllTime(allBeanClass));
        forTest = new ArrayList<>();
        System.out.println(getCreateMultiple(1000, allBeanClass));
        forTest = new ArrayList<>();
    }

    /**
     * 16 - create from cash
     */
    @Test
    public void testCompare(){
        forTest = new ArrayList<>();
        List<Class> allBeanClass = getAllBeanClass();
        fillCaching(allBeanClass);
        long createFromCash = getCreateFromCash(1000, allBeanClass);
        System.out.println(createFromCash);
    }

    private static List<String> forTest = new ArrayList<>();

    public void fillCaching(List<Class> classes){
        for (Class clazz : classes){
            String create = QueryMaker.Create.getCreate(clazz, false);
            cashMap.put(classes.getClass().getSimpleName(), create);
        }
    }
    public long getCreateFromCash(int count, List<Class> classes){
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            getFromMapList(classes);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public void getFromMapList(List<Class> classes){
        for(Class cl: classes){
            getFromMap(cl);
        }
    }

    public void getFromMap(Class clazz){
        String s = cashMap.get(clazz.getSimpleName());
        forTest.add(s);
    }

    public long getCreateMultiple(int count, List<Class> classes){
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            getCreate(classes);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public void getCreate(List<Class> classes){
        for (Class clazz : classes){
            String create = QueryMaker.Create.getCreate(clazz, false);
            forTest.add(create);
        }
    }

    public long getCreateTime(Class clazz){
        long start = System.currentTimeMillis();
        QueryMaker.Create.getCreate(clazz, false);
        long end = System.currentTimeMillis();
        return end - start;
    }



    public long getCreateAllTime(List<Class> classes){
        long start = System.currentTimeMillis();
        getCreate(classes);
        long end = System.currentTimeMillis();
        return end - start;
    }

    public List<Class> getAllBeanClass(){
        return ReflectionHelper.findClassesInSamePackage(User.class, false);
    }



}
