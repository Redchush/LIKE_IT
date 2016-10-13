package core_test;


import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.model.bean.User;
import org.junit.Test;
import testUtil.missalenious.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ReflectionTest {

    @Test
    public void allFieldsInWrittenOrder(){
        User user = new User();
        String aNull = user.toString().replace("User{", "")
                           .replaceAll("null", "").replaceAll("'", "")
                           .replaceAll("}", "").replaceAll("=", "")
                           .replaceAll(",", "").replaceAll("  ", " ");
        Field[] fields = User.class.getDeclaredFields();
        String collect = Arrays.stream(fields).map(Field::getName).collect(Collectors.joining(" "));
        assertEquals(aNull, collect);
        System.out.println(collect);
    }

    @Test
    public void mappinCorrect(){

        Field[] fields = User.class.getDeclaredFields();
        Stream<String> users = ResourceNavigator.getALLFieldsStream("users", false);
        StringBuilder builder = new StringBuilder();
        String usersField =
                users.map(s -> ResourceNavigator.getReferencedBeanField("users", s)).collect(Collectors.joining(" "));
        String collect = Arrays.stream(fields).map(Field::getName).collect(Collectors.joining(" "));
        assertEquals(collect, usersField);
    }

    @Test
    public void allMappinCorrect(){
        List<Class> models = ReflectionHelper.findClassesInSamePackage(User.class, false);
        System.out.println(models);
        for (Class clazz: models){
            checkClass(clazz);
        }

    }

    private void checkClass(Class clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        String referencedTable = ResourceNavigator.getRefTable(clazz);

        Stream<String> fieldsStream = ResourceNavigator.getALLFieldsStream(referencedTable, false);
        String usersField =
                fieldsStream.map(s -> ResourceNavigator.getReferencedBeanField(referencedTable, s))
                            .collect(Collectors.joining(" "));

        String collect = Arrays.stream(declaredFields).map(Field::getName)
                               .collect(Collectors.joining(" "));
        System.out.println(collect);
        System.out.println(usersField);
        assertEquals(collect, usersField);
    }
}
