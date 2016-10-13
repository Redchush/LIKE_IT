package by.epam.like_it.service;

import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;

import java.util.Map;


public interface PrevQueryService {

    String makeQueryByAddingParams(PrevQuery prev, Map<String, String[]> changes);

    String makeQueryByAddingParams(PrevQuery prev, Map<String, String> changes, boolean notComplex);

    String makeQueryByAddingParams(PrevQuery prev, String key, String value);

    String getFullPrevQuery(PrevQuery prevQuery);
}
