package com.amazonaws.mobileconnectors.lambdainvoker;

import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class LambdaJsonBinder implements LambdaDataBinder {
    private final Gson gson = new Gson();

    public <T> T deserialize(byte[] content, Class<T> clazz) {
        if (content == null) {
            return null;
        }
        return this.gson.fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content), StringUtils.UTF8)), clazz);
    }

    public byte[] serialize(Object object) {
        return this.gson.toJson(object).getBytes(StringUtils.UTF8);
    }
}
