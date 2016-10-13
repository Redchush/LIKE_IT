package by.epam.like_it.dao.mysql.util;

import by.epam.like_it.model.bean.User;
import org.junit.Test;

import java.sql.Types;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class ResourceNavigatorTest {
    @Test
    public void getRefTable() throws Exception {

    }

    @Test
    public void getReferencedClass() throws Exception {

    }

    @Test
    public void getAllBeanTypes() throws Exception {

    }

    @Test
    public void getALLTypessList() throws Exception {

    }

    @Test
    public void getAllTableTypes() throws Exception {

    }

    @Test
    public void getAllTableTypesList() throws Exception {
        List<Integer> users = ResourceNavigator.getAllTableTypesList("users");
        int longType = users.get(0);
        assertEquals(Types.BIGINT, longType);
    }

    @Test
    public void getJoinOn() throws Exception {

    }

    @Test
    public void getReferencedBeanField1() throws Exception {

    }

    @Test
    public void getRefTableField() throws Exception {

    }


    @Test
    public void getReferencedTable() throws Exception {
        String referencedTable = ResourceNavigator.getRefTable(User.class);
        System.out.println(referencedTable);
        assertEquals(referencedTable, "users");
    }

    @Test
    public void getReferencedTable1() throws Exception {

    }

    @Test
    public void getRefencedClass() throws Exception {

    }

    @Test
    public void isReferenceComplex() throws Exception {

    }

    @Test
    public void getFullTableName() throws Exception {

    }

    @Test
    public void getAllFieldsDirectly() throws Exception {

    }

    @Test
    public void getAllFieldsDirectly1() throws Exception {

    }

    @Test
    public void getALLFields() throws Exception {

    }

    @Test
    public void getALLFieldsList() throws Exception {

    }

    @Test
    public void getALLFieldsStream() throws Exception {

    }

    @Test
    public void getReferencedBeanField() throws Exception {
        String refField = ResourceNavigator.getRefTableField(User.class.getSimpleName(), "banned");
        System.out.println(refField);
        assertEquals(refField, "banned");
    }

    @Test
    public void getRefencesTableField() throws Exception {

    }

    @Test
    public void getAttrCount() throws Exception {

    }

    @Test
    public void getKeyForJoin() throws Exception {

    }

    @Test
    public void getOrderedKeyForJoin() throws Exception {

    }

    @Test
    public void getKeyForClassLink() throws Exception {

    }

    @Test
    public void getAnotherTableByRefTable1() throws Exception {

    }

    @Test
    public void getAnotherTableByTable() throws Exception {

    }

    @Test
    public void isClassMaintainInterface() throws Exception {

    }

    @Test
    public void getFullClassName() throws Exception {

    }

//    @Test
//    public void getAnotherTableByRefTable() throws Exception {
//        String anotherTable = ResourceNavigator.getAnotherTableByRefTable("users", "favorite_user_tags");
//        assertEquals("tags", anotherTable);
//
//        String another2 = ResourceNavigator.getAnotherTableByRefTable("categories", "categories_tags");
//        assertEquals("tags", another2);
//    }
//
//    @Test
//    public void getAnotherTableByClass() throws Exception{
//        String refClasses = ResourceNavigator.getRefencedClass("favorite_user_tags");
//        String anotherTable = ResourceNavigator.getAnotherTableByTable("users", refClasses);
//        assertEquals("tags", anotherTable);
//    }
}