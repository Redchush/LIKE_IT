package by.epam.like_it.service.impl;

import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.impl.system.PrevQueryServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;


public class PrevQueryServiceImplTest {
    private static PrevQueryService instance;

    @BeforeClass
    public static void logIn(){
        instance = PrevQueryServiceImpl.getInstance();
    }

    @Test
    public void pevTest(){

        PrevQuery query = new PrevQuery("/inex", null);
        System.out.println(query);

        HashMap<String, String[]> map = new HashMap<>();
        map.put("lang", new String[]{"ru"});

        query.setParams(map);
        System.out.println(query);

        String s = instance.makeQueryByAddingParams(query, "lang", "en");
        System.out.println(s);
        System.out.println(query);
    }


}