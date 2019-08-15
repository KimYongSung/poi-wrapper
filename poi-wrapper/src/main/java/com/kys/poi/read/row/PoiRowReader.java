package com.kys.poi.read.row;

import com.kys.poi.read.PoiRowHandler;
import com.kys.poi.read.cell.BasicCelllHandlerFactory;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * 엑셀파일 읽을때 row 별로 핸들링
 * 
 * @author kys0213
 * @date 2019. 3. 25.
 */
public interface PoiRowReader<T> {
    
    /**
     * cell Handler 정보 설정
     * @param cellHandlers
     */
    void setCellHandlers(BasicCelllHandlerFactory cellHandlers);
    
    /**
     * RowHandler 설정
     * @param rowHandler
     */
    void setRowHandler(PoiRowHandler<T> rowHandler);

    /**
     * Row 파싱 시작
     */
    void start();

    /**
     * 엑셀 cell mapping
     * 
     * @param cellReference
     * @param formattedValue
     * @param comment
     */
    void cell(String cellReference, String formattedValue, XSSFComment comment);

    /**
     * Header mapping
     * @param text
     * @param tagName
     */
    void header(String text, String tagName);

    /**
     * footer mapping
     * @param text
     * @param tagName
     */
    void footer(String text, String tagName);
    
    /**
     * Row 파싱 종료
     * @return
     */
    void end();
}
