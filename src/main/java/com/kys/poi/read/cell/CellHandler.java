package com.kys.poi.read.cell;

public interface CellHandler {

    /**
     * 해당 클래스 지원 여부
     * @param clazz
     * @return
     */
    boolean isSupport(Class<?> clazz);
    
    /**
     * null 처리 필수
     * @return
     */
    Object nullSafeValue(String value);
}
