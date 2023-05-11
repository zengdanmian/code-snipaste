package cn.lance.snipaste.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private JsonUtils() {}

    static {
        // 设置输入时忽略在 JSON 字符串中存在但 Java 对象实际没有的属性
        INSTANCE.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }

    public static String obj2json(Object obj) {
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Bean(String jsonStr, Class<T> clazz) {
        try {
            return INSTANCE.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Bean(String jsonStr, TypeReference<T> typeReference) {
        try {
            return INSTANCE.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return toList(json, ArrayList.class, clazz);
    }

    public static <T extends List<T>, V> T toList(String json, Class<T> listClazz, Class<V> contentClazz) {
        JavaType javaType = INSTANCE.getTypeFactory().constructParametricType(listClazz, contentClazz);
        try {
            return INSTANCE.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClazz, Class<V> valueClazz) {
        return toMap(json, HashMap.class, keyClazz, valueClazz);
    }

    public static <T extends Map<K, V>, K, V> T toMap(String json, Class<T> mapClazz, Class<K> keyClazz, Class<V> valueClazz) {
        JavaType javaType = INSTANCE.getTypeFactory().constructParametricType(mapClazz, keyClazz, valueClazz);
        try {
            return INSTANCE.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
