package by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple;

import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.InConstriction;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class InChainTest {
    //    private String collectAll(List<Constriction> constrictions){
//    private String collectOne(Constriction constriction)
//    private String applyFormat(InConstriction.InType type, String tableName, String fieldName, String value)

    private static InChain inChain;


    @BeforeClass
    public static void logIn(){
        inChain = InChain.getInstance();
    }

    @Test
    public void doSubChain() throws Exception {

    }

    @Test
    public void processSingleOnePart() throws Exception {

    }

    @Test
    public void collectAllTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        List<Constriction> list = new ArrayList<>();
        InConstriction<Tag, Long> emptyIn1 = new InConstriction<>(Tag.class, "id", Collections.emptySet());
        InConstriction<Tag, Long> emptyIn2 = new InConstriction<>(Tag.class, "id", Collections.emptySet());
        list.add(emptyIn1);
        list.add(emptyIn2);

        Method collectAll = inChain.getClass().getDeclaredMethod("collectAll", List.class);
        collectAll.setAccessible(true);
        String  invoke = (String) collectAll.invoke(inChain, list);
        assertTrue(invoke.isEmpty());
        collectAll.setAccessible(false);
    }

}