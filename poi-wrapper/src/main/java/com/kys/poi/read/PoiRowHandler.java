package com.kys.poi.read;

/**
 * Excel Row handler.
 *
 * row 별로 매핑한 클래스 정보 핸들링
 *
 * @param <T>
 */
public interface PoiRowHandler<T>{

    /**
     * row 핸들링 처리
     * @param obj row 정보
     */
    void handler(T obj);
}