package com.kys.poi.read;

import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * 엑셀파일 읽을때 row 별로 핸들링
 * 
 * @author kys0213
 * @date 2019. 3. 25.
 */
public interface PoiRowHandler {

    /**
     * row 셋팅 VO 객체 생성
     * 
     * @return
     */
    Object getInstance();

    /**
     * 파싱한 row 정보 핸들링
     * 
     * @param obj
     */
    void handling(Object obj);

    /**
     * 엑셀 cell mapping
     * 
     * @param cellReference
     * @param formattedValue
     * @param comment
     * @param currentObj
     * @param cellIndex
     */
    void mapping(String cellReference, String formattedValue, XSSFComment comment, Object currentObj, int cellIndex);
}
