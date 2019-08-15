package com.kys.poi.read.cell;

import com.kys.util.StringUtil;

public class ShortHandler implements CellHandler{

    @Override
    public boolean isSupport(Class<?> clazz) {
        return clazz == null ? false : isShort(clazz);
    }

    @Override
    public Object nullSafeValue(String value) {
        return StringUtil.isNullorEmpty(value) ? 0 : Short.parseShort(value);
    }

    private boolean isShort(Class<?> clazz) {
        return Short.TYPE.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz);
    }
}
