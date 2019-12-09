package com.kys.poi.read;

import com.kys.poi.read.row.PoiRowReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

public class PoiSheetHandler implements SheetContentsHandler {

    /**
     * Poi row reader
     */
    private final PoiRowReader<?> reader;

    /**
     * 엑셀 첫번째 로우 스킵 여부
     */
    private boolean isFirstRowSkip = true;

    /**
     * 현재 진행하는 row skip 여부
     */
    private boolean isSkip = false;


    public PoiSheetHandler(PoiRowReader<?> reader) {
        this.reader = reader;
    }

    /**
     * PoiSheetHandler
     * 
     * @param reader
     * @param isFirstRowSkip
     */
    public PoiSheetHandler(PoiRowReader<?> reader, boolean isFirstRowSkip) {
        this(reader);
        this.isFirstRowSkip = isFirstRowSkip;
    }

    /**
     * Row 파싱 시작시 호출
     */
    public void startRow(int rowNum) {
        isSkip(rowNum);
        if(isSkip) return; 
        reader.start();
    }

    /**
     * Row 파싱 종료
     */
    public void endRow(int rowNum) {
        if (isSkip) return;
        reader.end();
    }

    /**
     * Cell 매핑
     */
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if (isSkip) return;
        reader.cell(cellReference, formattedValue, comment);
    }

    /**
     * Header와 footer 매핑
     */
    public void headerFooter(String text, boolean isHeader, String tagName) {
        if(isHeader){
            reader.header(text, tagName);
        }else{
            reader.footer(text, tagName);
        }
    }

    private void isSkip(int rowNum) {
        isSkip = (rowNum == 0 && isFirstRowSkip) ? true : false;
    }
}
