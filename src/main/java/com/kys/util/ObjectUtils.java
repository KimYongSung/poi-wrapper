package com.kys.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ObjectUtil
 * 
 * @author kys0213
 * @Date 2016. 10. 17.
 */
public class ObjectUtils {

    /**
     * 해당 Object null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        if (isString(obj))
            return StringUtil.isNull((String) obj);
        else
            return obj == null;
    }

    /**
     * 해당 Object null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNull(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * 해당 Object null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNull(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * 해당 Object null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNull(Object[] obj) {
        return obj == null || Array.getLength(obj) == 0;
    }

    /**
     * 해당 Object not null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 해당 Object not null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNotNull(Collection<?> obj) {
        return !isNull(obj);
    }

    /**
     * 해당 Object not null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNotNull(Map<?, ?> obj) {
        return !isNull(obj);
    }

    /**
     * 해당 Object not null 체크
     * 
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object[] obj) {
        return !isNull(obj);
    }

    /**
     * 해당 Object가 {@link String} 객체인지 확인
     * 
     * @param obj
     * @return
     */
    public static boolean isString(Object obj) {
        return isObject(obj, String.class);
    }

    /**
     * 해당 클래스가 String class 인지 확인
     * 
     * @param clazz
     * @return
     */
    public static boolean isString(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    /**
     * 해당 Object가 {@link Number} 객체인지 확인
     * 
     * @param obj
     * @return
     */
    public static boolean isNumber(Object obj) {
        return isObject(obj, Number.class);
    }

    /**
     * 해당 Object가 {@link Map} 객체인지 확인
     * 
     * @param obj
     * @return
     */
    public static boolean isMap(Object obj) {
        return isObject(obj, Map.class);
    }

    /**
     * 해당 Object가 {@link Collection} 객체인지 확인
     * 
     * @param obj
     * @return
     */
    public static boolean isCollection(Object obj) {
        return isObject(obj, Collection.class);
    }

    /**
     * 해당 Object가 {@link List} 객체인지 확인
     * 
     * @param obj
     * @return
     */
    public static boolean isList(Object obj) {
        return isObject(obj, List.class);
    }

    /**
     * 해당 Object가 배열인지 확인
     * 
     * @param obj
     * @return
     */
    public static boolean isArray(Object obj) {
        return isObject(obj, Object[].class);
    }

    /**
     * Object instance 체크
     * 
     * @param obj
     * @param clazz
     * @return
     */
    public static <T> boolean isObject(Object obj, Class<T> clazz) {
        return clazz.isInstance(obj);
    }

    /**
     * Object instance 체크
     * 
     * @param obj
     * @param clazz
     * @return
     */
    public static <T> boolean isNotObject(Object obj, Class<T> clazz) {
        return !isObject(obj, clazz);
    }

    /**
     * Object type cast
     * 
     * @param obj
     * @param clazz
     * @return 일치하는 타입이 아닐 경우 null 리턴
     */
    public static <T> T cast(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }

    /**
     * Object 복사
     * 
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyOf(T obj) {
        if (isNull(obj))
            throw new NullPointerException("obj is null");
        else if (isString(obj))
            return (T) copyOf((String) obj);
        else if (isNumber(obj))
            return (T) copyOf((Number) obj);
        else if (isList(obj))
            return (T) copyOf((List<?>) obj);
        else if (isMap(obj))
            return (T) MapUtil.copyOf((Map<?, ?>) obj);
        else
            return beanCopy(obj);
    }

    /**
     * List 복사
     * 
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> copyOf(List<T> list) {
        Object[] newArray = Arrays.copyOf(list.toArray(), list.size());
        return (List<T>) Arrays.asList(newArray);
    }

    /**
     * VO 형식의 Object 복사
     * 
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T beanCopy(T obj) {

        Class<T> clazz = (Class<T>) obj.getClass();
        T newInstance = ReflectionUtil.newInstance(clazz);
        Field[] fields = ReflectionUtil.findFields(clazz);

        for (Field field : fields) {
            Object fieldValue = ReflectionUtil.getField(field, obj);
            ReflectionUtil.setField(field, newInstance, fieldValue);
        }
        return newInstance;
    }

    /**
     * {@link Number} 자식 클래스 복사
     * 
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T copyOf(T obj) {

        if (obj instanceof Integer) {
            return (T) new Integer(obj.intValue());
        } else if (obj instanceof Long) {
            return (T) new Long(obj.longValue());
        } else if (obj instanceof Float) {
            return (T) new Float(obj.floatValue());
        } else if (obj instanceof Double) {
            return (T) new Double(obj.doubleValue());
        } else {
            return (T) ReflectionUtil.newInstance(obj.getClass(), obj.toString());
        }
    }

    /**
     * String 복사
     * 
     * @param obj
     * @return
     */
    public static String copyOf(String obj) {
        return String.copyValueOf(obj.toCharArray());
    }

}
