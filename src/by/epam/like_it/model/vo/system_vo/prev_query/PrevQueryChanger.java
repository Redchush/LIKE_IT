package by.epam.like_it.model.vo.system_vo.prev_query;


import by.epam.like_it.model.vo.page_vo.BeanPair;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.impl.system.PrevQueryServiceImpl;

import java.util.HashMap;

public class PrevQueryChanger {

    public final PrevQueryService service = PrevQueryServiceImpl.getInstance();
    private String result;
    private PrevQuery prevQuery;

    private HashMap<String, String[]> complexChangeMap;
    private BeanPair simpleChange;
    private HashMap<String, String> simpleChangeMap;

    public PrevQueryChanger() {}

    public String getResult() {
        if (result == null){
            return service.getFullPrevQuery(prevQuery);
        }
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PrevQuery getPrevQuery() {
        return prevQuery;
    }

    public void setPrevQuery(PrevQuery prevQuery) {
        this.prevQuery = prevQuery;
    }

    public HashMap<String, String[]> getComplexChangeMap() {
        return complexChangeMap;
    }

    public void setComplexChangeMap(HashMap<String, String[]> complexChangeMap) {
       this.complexChangeMap = complexChangeMap;
       this.result = service.makeQueryByAddingParams(prevQuery, complexChangeMap);
    }

    public void setSimpleChangeMap(HashMap<String, String> simpleChangeMap){
        this.result = service.makeQueryByAddingParams(prevQuery, complexChangeMap);
    }

    public void setSimpleChange(BeanPair pair){
        this.result = service.makeQueryByAddingParams(prevQuery, pair.getKey(), pair.getValue());
    }
}
