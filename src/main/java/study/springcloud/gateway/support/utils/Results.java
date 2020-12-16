package study.springcloud.gateway.support.utils;

import com.google.common.collect.Maps;

import java.util.Map;

public class Results {

    private static final String KEY_CODE = "code";

    private static final String KEY_DESC = "desc";

    private static final String KEY_DATA = "data";

    public static Map<String, Object> ok(Map<String, Object> data) {
        return build("0000", "成功", data);
    }

    public static Map<String, Object> ok() {
        return ok(null);
    }

    public static Map<String, Object> error(String code, String desc) {
        return build(code, desc, null);
    }

    private static Map<String, Object> build(String code, String desc, Map<String, Object> data) {
        Map<String, Object> result = Maps.newHashMap();
        result.put(KEY_CODE, code);
        result.put(KEY_DESC, desc);
        if (data == null) {
            data = Maps.newHashMap();
        }
        result.put(KEY_DATA, data);
        return result;
    }
}
