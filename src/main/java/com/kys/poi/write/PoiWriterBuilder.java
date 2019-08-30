package com.kys.poi.write;

import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kys.poi.style.CellStyleBuilder;
import com.kys.poi.style.DefaultCellStyles;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class PoiWriterBuilder {

    /**
     * 엑셀 2003 버전 대상의 최대 행 갯수
     */
    private static final int EXCEL2003_MAX_ROW = 60000;

    /**
     * 엑셀 2007 버전 이상의 최대 행 갯수
     */
    private static final int EXCEL2007_MAX_ROW = 1040000;

    /**
     * 사용할 Poi workbook
     */
    private Workbook workBook;

    /**
     * 엑셀을 생성할 outputStream
     */
    private OutputStream outputStream;

    /**
     * 엑셀 확장자별 sheet당 최대 열 수
     */
    private int maxrow;

    private CellStyleBuilder titleBuilder;
    private CellStyleBuilder numberBuilder;
    private CellStyleBuilder stringBuilder;

    public PoiWriter build() {

        if (workBook == null) {
            throw new IllegalAccessError("Workbook is null");
        } else if (workBook instanceof SXSSFWorkbook) {
            ((SXSSFWorkbook) workBook).setCompressTempFiles(true);
        }

        if (outputStream == null) {
            throw new IllegalAccessError("outputStream is null");
        }

        CellStyle titleStyle = titleBuilder == null ? DefaultCellStyles.defaultTitleBuilder.build(workBook)
                : titleBuilder.build(workBook);
        CellStyle numberStyle = numberBuilder == null ? DefaultCellStyles.defaultNumberBuilder.build(workBook)
                : numberBuilder.build(workBook);
        CellStyle stringStyle = stringBuilder == null ? DefaultCellStyles.defaultStringBuilder.build(workBook)
                : stringBuilder.build(workBook);

        WriterConfig config = new WriterConfig(maxrow, titleStyle, numberStyle, stringStyle);
        
        return new PoiWriter(config, workBook, outputStream);
    }

    /**
     * 엑셀파일을 출력할 OutputStream
     * @param outputStream
     * @return
     */
    public PoiWriterBuilder setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    /**
     * .xlsx 출력용 workbook
     * @return
     */
    public PoiWriterBuilder setXSSFWorkbook() {
        this.workBook = new XSSFWorkbook();
        this.maxrow = EXCEL2007_MAX_ROW;
        return this;
    }

    /**
     * .xlsx 출력용 workbook
     * <br> 
     * <li> Streaming Excel Write 객체 </li> 
     * <br>
     * @param fetchRow 메모리에 fetch 하는 row 수
     * @return
     */
    public PoiWriterBuilder setSXSSFWorkbook(int fetchRow) {
        this.workBook = new SXSSFWorkbook(fetchRow);
        this.maxrow = EXCEL2007_MAX_ROW;
        return this;
    }

    /**
     * .xls 출력용 workbook
     * @return
     */
    public PoiWriterBuilder setHSSFWorkBook() {
        this.workBook = new HSSFWorkbook();
        this.maxrow = EXCEL2003_MAX_ROW;
        return this;
    }

    /**
     * Title 스타일 설정
     * 
     * @param titleBuilder
     * @return
     */
    public PoiWriterBuilder setTitleStyleBuilder(CellStyleBuilder titleBuilder) {
        this.titleBuilder = titleBuilder;
        return this;
    }

    /**
     * 숫자 형식 스타일 설정
     * 
     * @param numberBuilder
     * @return
     */
    public PoiWriterBuilder setNumberStyleBuilder(CellStyleBuilder numberBuilder) {
        this.numberBuilder = numberBuilder;
        return this;
    }

    /**
     * 문자 형식 스타일 설정
     * 
     * @param stringBuilder
     * @return
     */
    public PoiWriterBuilder setStringStyleBuilder(CellStyleBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
        return this;
    }
}
