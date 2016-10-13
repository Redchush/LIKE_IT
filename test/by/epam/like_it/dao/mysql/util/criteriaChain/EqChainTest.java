package by.epam.like_it.dao.mysql.util.criteriaChain;

import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple.EqChain;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class EqChainTest {
    private static InitialPostCriteria criteria;
    private static EqChain instance;
    private static EqConstriction<Post, Boolean> postEqConstriction;

    @BeforeClass
    public static void logIn(){
        instance = EqChain.getInstance();
        criteria = new InitialPostCriteria();
        postEqConstriction = new EqConstriction<Post, Boolean>(Post.class, "banned", Collections.singleton(false));
        criteria.putConstriction(postEqConstriction);
    }

    @Test
    public void name() throws Exception {
        StringBuilder builder = new StringBuilder("");
        StringBuilder builder1 = instance.doSubChain(builder, criteria.getConstrictions(), false);
        assertEquals("\nposts.banned='false'", builder1.toString());
        System.out.println(builder);
    }

    @Test
    public void collectALLTest() throws Exception {
        List<Constriction> eqs = criteria.getConstrictions().get(ConstrictionType.EQ);

        Method collectAll = instance.getClass().getDeclaredMethod("collectAll", List.class);
        collectAll.setAccessible(true);
        Object invoke = collectAll.invoke(instance, eqs);
        collectAll.setAccessible(false);
        assertEquals("\nposts.banned='false'", invoke);
    }

    @Test
    public void collectOneTest() throws Exception {
        Method collectOne = instance.getClass().getDeclaredMethod("collectOne", EqConstriction.class);
        collectOne.setAccessible(true);
        Object invoke = collectOne.invoke(instance, postEqConstriction);
        collectOne.setAccessible(false);
        assertEquals("posts.banned='false'", invoke);
    }
}