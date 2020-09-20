package com.tgw.basic.common.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zjg on 2020/7/12.
 */
public class PlatformJsonUtils {
    private static final Logger logger  = LoggerFactory.getLogger(PlatformJsonUtils.class);

    /**
     * Object对象转换为String
     *
     * @param data Object对象
     * @return Object对象对应的字符串
     */
    public static <T> String toJsonString(T data) {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(data);
        } catch (Exception e) {
            logger.error("json parse error:", e);
        }
        return "";
    }

    /**
     * 字符串转换为对象
     *
     * @param json  字符串
     * @param clazz 类类型
     * @return clazz类型的对象
     */
    public static <R> R json2Object(String json, Class<R> clazz) {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("json parse error:", e);
        }
        return null;
    }

    /**
     * 字节转换为对象
     *
     * @param jsonBuffer 字节
     * @param clazz      类类型
     * @return clazz类型的对象
     */
    public static <R> R json2Object(byte[] jsonBuffer, Class<R> clazz) {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.readValue(jsonBuffer, clazz);
        } catch (Exception e) {
            logger.error("json parse error:", e);
        }
        return null;
    }

}
