package by.epam.like_it.model.vo.system_vo.prev_query;


import java.util.HashMap;
import java.util.Map;

public class PrevQuery {

    private String uri;
    private Map<String, String[]> params;
    private String backQuery;

    private PrevQueryMode mode;

    public PrevQuery() {
        params = new HashMap<>(0);
        this.mode = PrevQueryMode.SINGLE_PARAM;
    }

    public PrevQuery(String uri, Map<String, String[]> params) {
        this.uri = uri;
        this.params = params;
        if (params == null){
            this.params = new HashMap<>(0);
        }
        this.mode = PrevQueryMode.SINGLE_PARAM;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String[]> getParams() {
        return params;
    }

    public void setParams(Map<String, String[]> params) {
        this.params = params;
    }

    /**
     *
     * @return  PrevQueryMode  -
     *              PrevQueryMode.SINGLE_PARAM - means that the name of param correlate only one value and in case of
     *              update params the initial param will be replaced by new value
     *              MULTIPLE_PARAM - then name of param correlates more than one value. In case of update params
     *              new param will be added criteria_to array of param values
     */

    public PrevQueryMode getMode() {
        return mode;
    }

    /**
     *
     * @param mode -
     *              PrevQueryMode.SINGLE_PARAM - means that the name of param correlate only one value and in case of
     *              update params the initial param will be replaced by new value
     *              MULTIPLE_PARAM - then name of param correlates more than one value. In case of update params
     *              new param will be added criteria_to array of param values
     */
    public void setMode(PrevQueryMode mode) {
        this.mode = mode;
    }


    public String getBackQuery() {
        return backQuery;
    }

    public void setBackQuery(String backQuery) {
        this.backQuery = backQuery;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrevQuery{");

        sb.append("uri='").append(uri).append('\'');
        sb.append(", params=").append(params);
        sb.append(", backQuery='").append(backQuery).append('\'');
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}
