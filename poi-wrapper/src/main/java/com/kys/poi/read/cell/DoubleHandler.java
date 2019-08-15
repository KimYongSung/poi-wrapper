package com.kys.poi.read.cell;

import com.kys.util.StringUtil;

public class DoubleHandler implements CellHandler{

    @Override
    public boolean isSupport(Class<?> clazz) {
        return clazz == null ? false : isDouble(clazz);
    }

    @Override
    public Object nullSafeValue(String value) {
        return StringUtil.isNullorEmpty(value) ? 0.0 : Double.parseDouble(value);
    }

    private boolean isDouble(Class<?> clazz) {
        return Double.TYPE.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz);
    }
}
