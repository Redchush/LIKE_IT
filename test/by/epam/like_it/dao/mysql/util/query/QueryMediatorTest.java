package by.epam.like_it.dao.mysql.util.query;

import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.Entity;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;
import testUtil.missalenious.PredefinedEntity;
import testUtil.missalenious.RandomEntity;
import testUtil.missalenious.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class QueryMediatorTest {



    private static final QueryMediator mediator = QueryMediator.getInstance();
    private static User userTestedWithBan;
    private static User userTestedWithoutBan;
    private static User userToUpdate;
    private static List<User> randomUsers;
    private static RandomEntity randomEntity;
    private static List<Class> allEntitiesClass;

    @BeforeClass
    public static void logIn() throws StringRandomizeInitException {
        userTestedWithBan = PredefinedEntity.userTestedWithBan;
        userTestedWithoutBan = PredefinedEntity.userTestedWithoutBan;
        userToUpdate = PredefinedEntity.userToUpdate;

        randomEntity = RandomEntity.getInstance();
        randomUsers = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Entity randomEntity = QueryMediatorTest.randomEntity.getRandomEntity(User.class);
            randomUsers.add((User) randomEntity);
        }
        allEntitiesClass = ReflectionHelper.findClassesInSamePackage(User.class, false);
    }

    @Test
    public void getSelectAll() throws Exception {
        for(Class clazz: allEntitiesClass){
            String expected = QueryMaker.Select.getSelectAll(clazz);
            String actual = mediator.getSelectAll(clazz);
            assertEquals(expected, actual);
        }

    }
    @Test
    public void getSelectByObject() throws Exception {
        String selectByObject = QueryMaker.Select.getSelectByObject(userTestedWithBan);
        String selectByObject1 = mediator.getSelectByObject(userTestedWithBan);
        assertEquals(selectByObject, selectByObject1);
        for(User user:randomUsers){
            String selectByObjectExp = QueryMaker.Select.getSelectByObject(user);
            String selectByObjectAct = mediator.getSelectByObject(user);
            assertEquals(selectByObjectExp, selectByObjectAct);
        }
    }

    @Test
    public void getUpdateByObject() throws Exception {
        for(User user:randomUsers){
            String selectByObjectExp = QueryMaker.Update.getUpdateByNotNullFields(user);
            String selectByObjectAct = mediator.getUpdateByNotNullFields(user);
            assertEquals(selectByObjectExp, selectByObjectAct);
        }
    }

    @Test
    public void getCreate() throws Exception {
        for(Class clazz: allEntitiesClass){
            System.out.println(clazz);
            boolean classMaintainInterface = ReflectionUtil.isClassMaintainInterface(clazz, DeletableByBan.class);
            String expected = QueryMaker.Create.getCreate(clazz, classMaintainInterface);
            String actual = mediator.getCreate(clazz, 1, classMaintainInterface);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void getSelectById() throws Exception {

    }

    @Test
    public void getPreperadPart() throws Exception {
        String preperadPart = mediator.getPreperadPart(1);
        assertEquals("?", preperadPart);
        String preperadPart2 = mediator.getPreperadPart(2);
        assertEquals("?, ?", preperadPart2);
        String preperadPart3 = mediator.getPreperadPart(3);
        assertEquals("?, ?, ?", preperadPart3);
    }

}