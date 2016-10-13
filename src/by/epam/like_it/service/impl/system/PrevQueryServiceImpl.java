package by.epam.like_it.service.impl.system;

import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQueryMode;
import by.epam.like_it.service.PrevQueryService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PrevQueryServiceImpl implements PrevQueryService {

    private static PrevQueryService instance;

    private PrevQueryServiceImpl(){}

    public static PrevQueryService getInstance(){

        if (instance == null)
            synchronized (PrevQueryServiceImpl.class){
                if (instance == null)
                    instance = new PrevQueryServiceImpl();
            }
        return instance;
    }

    @Override
    public String makeQueryByAddingParams(PrevQuery prev, Map<String, String[]> changes){
        if (changes == null){
            return getFullPrevQuery(prev);
        } else {
            Map<String, String[]> totalParams = new HashMap<>();
            if (prev.getParams() != null){
                totalParams.putAll(prev.getParams());
            }
            totalParams.putAll(changes);
            return getFullPrevQuery(new PrevQuery(prev.getUri(), totalParams));
        }
    }

    @Override
    public String makeQueryByAddingParams(PrevQuery prev, Map<String, String> changes, boolean notComplex){
        if (changes == null){
            return getFullPrevQuery(prev);
        } else {
            Map<String, String[]> totalParams = new HashMap<>();
            if (prev.getParams() != null){
                totalParams.putAll(prev.getParams());
            }
            for(Map.Entry<String, String> entry : changes.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                totalParams.put(key, new String[]{value});
            }
            return getFullPrevQuery(new PrevQuery(prev.getUri(), totalParams));
        }
    }

    @Override
    public String makeQueryByAddingParams(PrevQuery prev, String key, String value){
        if (key == null || value == null){
            return getFullPrevQuery(prev);
        } else {
            Map<String, String[]> totalParams = new HashMap<>();
            if (prev.getParams() != null){
                totalParams.putAll(prev.getParams());
            }
            totalParams.put(key, new String[]{value});
            return getFullPrevQuery(new PrevQuery(prev.getUri(), totalParams));
        }
    }



    /**
     * NPE-free method, that collect uri from PrevQuery bean;
     * @param prevQuery- PrevQuery bean
     * @return previous requested URI with parameters and index page if parameter is null or its uri is null.
     */

    @Override
    public String getFullPrevQuery(PrevQuery prevQuery){
        if (prevQuery == null || prevQuery.getUri() == null){
            return PageNavigator.getInstance().getPage(PageNavigator.Pages.INDEX);
        }
        StringBuilder builder = new StringBuilder(prevQuery.getUri());
        Map<String, String[]> params = prevQuery.getParams();
        if (params != null && !params.isEmpty()){
            if (prevQuery.getMode() == PrevQueryMode.SINGLE_PARAM){
                String queryString = params.entrySet().stream().map(s -> new StringBuilder(s.getKey()).append("=").append(s
                        .getValue()[0])).collect(Collectors.joining("&", "?", ""));
                builder.append(queryString);
            }
        }
        return builder.toString();
    }
}
