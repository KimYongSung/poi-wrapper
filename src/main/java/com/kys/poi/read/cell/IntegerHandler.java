package com.kys.poi.read.cell;

import com.kys.util.StringUtil;

public class IntegerHandler implements CellHandler{

    @Override
    public boolean isSupport(Class<?> clazz) {
        return clazz == null ? false : isInteger(clazz);
    }

    @Override
    public Object nullSafeValue(String value) {
        return StringUtil.isNullorEmpty(value) ? 0 : Integer.parseInt(value);
    }

    private boolean isInteger(Class<?> clazz) {
        return Integer.TYPE.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz);
    }
}
