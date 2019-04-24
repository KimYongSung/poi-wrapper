package com.kys.poi.read;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFComment;

import com.kys.util.ReflectionUtil;
import com.kys.util.StringUtil;

public abstract class ReflactionPoiRowHandler implements PoiRowHandler {

    private final Class<?> clazz;

    private final List<String> mappings;

    public ReflactionPoiRowHandler(Class<?> clazz, List<String> mappings) {
        super();
        this.clazz = clazz;
        this.mappings = mappings;
    }

    @Override
    public Object getInstance() {
        return ReflectionUtil.newInstance(clazz);
    }

    @Override
    public void mapping(String cellReference, String formattedValue, XSSFComment comment, Object currentObj,
            int cellIndex) {
        // TODO 타입 매핑을 interface로 추상화하여 처리. 기본은 String
        Field field = ReflectionUtil.findField(clazz, mappings.get(cellIndex));

        if (ReflectionUtil.isTypeEquals(field, Integer.class) || ReflectionUtil.isTypeEquals(field, int.class)) {
            ReflectionUtil.setField(field, currentObj, Integer.parseInt(formattedValue));
        } else if (ReflectionUtil.isTypeEquals(field, Long.class) || ReflectionUtil.isTypeEquals(field, long.class)) {
            ReflectionUtil.setField(field, currentObj, Long.parseLong(formattedValue));
        } else if (ReflectionUtil.isTypeEquals(field, Double.class)|| ReflectionUtil.isTypeEquals(field, double.class)) {
            ReflectionUtil.setField(field, currentObj, Double.parseDouble(formattedValue));
        } else if (ReflectionUtil.isTypeEquals(field, Short.class) || ReflectionUtil.isTypeEquals(field, short.class)) {
            ReflectionUtil.setField(field, currentObj, Short.parseShort(formattedValue));
        } else {
            ReflectionUtil.setField(field, currentObj, StringUtil.nullToEmpty(formattedValue));
        }
    }

}
