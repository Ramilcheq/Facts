package Facts;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;

public class RuleResultHandler implements ResultHandler{
    Map<Integer, String> inMap = new HashMap<Integer, String>();

    public Map<Integer, String> getIdNameMap() {
        return inMap;
    }

    @Override
    public void handleResult(ResultContext rc) {
        @SuppressWarnings("unchecked")
        Map<String, Object> m = (Map<String, Object>) rc.getResultObject();
        inMap.put((Integer) getFromMap(m, "expression_id"),
                (String) getFromMap(m, "result_fact"));
    }

    // see note at bottom of answer as to why I include this method
    private Object getFromMap(Map<String, Object> map, String key) {
        if (map.containsKey(key.toLowerCase())) {
            return map.get(key.toLowerCase());
        } else {
            return map.get(key.toUpperCase());
        }
    }
}
