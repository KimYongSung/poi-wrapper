package com.kys.poi.read.cell;

import com.kys.util.StringUtil;

/**
 * String 클래스 핸들러
 *
 * @author kys0213
 * @since  2019. 8. 1.
 */
public class StringHandler implements CellHandler {

    private static final Class<String> CLAZZ = String.class;

    @Override
    public boolean isSupport(Class<?> clazz) {
        return clazz == null ? false : CLAZZ.isAssignableFrom(clazz);
    }

    @Override
    public Object nullSafeValue(String value) {
        return StringUtil.nullToEmpty(value);
    }
}
