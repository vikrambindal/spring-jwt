package com.vikram.helper;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    public static final String USER_ACCOUNT = "USER_ACCOUNT";
    public static final String GREET_RESPONSE = "GREET_RESPONSE";
    public static final String TOKEN_RESPONSE = "TOKEN_RESPONSE";
    private final Map<String, Object> dataMap = new HashMap<>();

    public void addToContext(String key, Object o) {
        dataMap.put(key, o);
    }

    public Object getFromContext(String key) {
        return dataMap.get(key);
    }
}
