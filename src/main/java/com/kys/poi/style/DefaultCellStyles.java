package com.kys.poi.style;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;

public class DefaultCellStyles {

    /**
     * Default Title Format Builder
     */
    public static final CellStyleBuilder defaultTitleBuilder = new CellStyleBuilder() {
        @Override
        public CellStyle build(Workbook workBook) {
            CellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);

            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font font = workBook.createFont();
            font.setFontName("Tahoma");
            font.setFontHeight((short) (10 * 20));
            font.setBold(true);

            cellStyle.setFont(font);

            return cellStyle;
        }
    };

    /**
     * Default Number Format Builder
     */
    public static final CellStyleBuilder defaultNumberBuilder = new CellStyleBuilder() {
        @Override
        public CellStyle build(Workbook workBook) {
            CellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
            Font font = workBook.createFont();
            font.setFontName("Tahoma");
            font.setFontHeight((short) (10 * 20));

            cellStyle.setFont(font);

            return cellStyle;
        }
    };

    /**
     * Default String Format Builder
     */
    public static final CellStyleBuilder defaultStringBuilder = new CellStyleBuilder() {

        @Override
        public CellStyle build(Workbook workBook) {
            CellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            Font font = workBook.createFont();
            font.setFontName("Tahoma");
            font.setFontHeight((short) (10 * 20));

            cellStyle.setFont(font);

            return cellStyle;
        }
    };
}
