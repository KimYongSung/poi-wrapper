package com.kys.poi.read.cell;

import com.kys.util.StringUtil;

/**
 * Long 핸들러
 */
public class LongHandler implements CellHandler{

    @Override
    public boolean isSupport(Class<?> clazz) {
        return clazz == null ? false : isLong(clazz);
    }

    @Override
    public Object nullSafeValue(String value) {
        return StringUtil.isNullorEmpty(value) ? 0 : Long.parseLong(value);
    }

    private boolean isLong(Class<?> clazz) {
        return Long.TYPE.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz);
    }
}
