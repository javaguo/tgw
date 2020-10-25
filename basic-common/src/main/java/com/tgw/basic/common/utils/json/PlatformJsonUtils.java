package com.tgw.basic.common.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgw.basic.common.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

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
            logger.error("Object to jsonString error:", e);
            throw new PlatformException("Object to jsonString error");
        }
    }

    /**
     * 字符串转换为对象
     *
     * @param json  字符串
     * @param clazz 类类型
     * @return clazz类型的对象
     */
    public static <R> R stringToObject(String json, Class<R> clazz) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true); //允许使用未带引号的字段名
            om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许使用单引号
            return om.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("jsonString to Object error,json: "+json, e);
            throw new PlatformException("jsonString to Object error,json: "+json);
        }
    }

    /**
     * json字符串转为Map对象
     * @param json
     * @return
     */
    public static Map stringToMap(String json) {
        return PlatformJsonUtils.stringToObject(json,Map.class);
    }

    /**
     * json字符串转为List对象
     * @param json
     * @return
     */
    public static List stringToList(String json) {
        return PlatformJsonUtils.stringToObject(json,List.class);
    }

    /**
     * 字节转换为对象
     *
     * @param jsonBuffer 字节
     * @param clazz      类类型
     * @return clazz类型的对象
     */
    public static <R> R byteToObject(byte[] jsonBuffer, Class<R> clazz) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true); //允许使用未带引号的字段名
            om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许使用单引号
            return om.readValue(jsonBuffer, clazz);
        } catch (Exception e) {
            logger.error("jsonByte to Object error", e);
            throw new PlatformException("jsonByte to Object error");
        }
    }

    // 已验证通过的测试示例
    /*public static void main(String[] args){
        StringBuilder s1 = new StringBuilder();
        // 最外层结构是map对象
        s1.append("{");
        // 属性为字符串、整数、小数
        s1.append("\"attr1\":\"attr1Value\",\"attr2\":123,\"attr3\":123.456");
        // 属性为数组，数组里的对象继续嵌套字符串、整数、数组、map结构对象
        s1.append(",\"attr4\":[{\"a1\":\"a1Value\",\"a2\":\"a2Value\",\"a3\":[{\"a1\":\"abc\",\"a2\":12}]},{\"b1\":\"b1Value\",\"b2\":\"b2Value\",\"b3\":{\"b1\":\"abcd\",\"b2\":1234}}]");
        // 属性为map结构对象，map结构对象里继续嵌套字符串、整数、数组、map结构对象
        s1.append(",\"attr5\":{\"a51\":\"a51Value\",\"a52\":345,\"a54\":[{\"a\":\"abc\",\"b\":987},{\"a\":\"abc\",\"b\":987}],\"a53\":{\"a1\":\"asd\",\"a2\":321}}");
        s1.append("}");
        Map map1 = stringToMap(s1.toString());

        StringBuilder s2 = new StringBuilder();
        // 最外层为数组
        s2.append("[");
        // 对象属性为字符串、整数、小数
        s2.append("{\"a1\":\"abc\",\"a2\":123,\"a3\":12.34}");
        // 对象属性为数组，数组里继续嵌套字符串、数组
        s2.append(",{\"a1\":[{\"a1\":\"abc\"},{\"a2\":[{\"a21\":\"abc\",\"a22\":23.45}]}]}");
        // 对象里继续嵌套对象
        s2.append(",{\"a1\":{\"a1\":\"abc\",\"a2\":12},\"b1\":{\"a1\":\"abc\",\"a2\":12.2}}");
        s2.append("]");
        List list = stringToList(s2.toString());
    }*/

}
