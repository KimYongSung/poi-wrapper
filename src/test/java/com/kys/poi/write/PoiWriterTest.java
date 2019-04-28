package com.kys.poi.write;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.junit.Test;

public class PoiWriterTest {

    @Test
    public void 엑셀파일생성() throws Exception {
        PoiWriter writer = PoiWriter.getBuilder()
                                    .setXSSFWorkbook()
                                    .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\excel_test.xlsx"))
                                    .build();

        writer.createSheet("testSheet");

        for (int index = 0; index < 10000; index++) {
            writer.addCell(String.valueOf(index))
                  .addCell(index)
                  .nextRow();
        }

        writer.write()
              .close();
    }
    
    @Test
    public void 스트링엑셀파일생성() throws Exception {
        PoiWriter writer = PoiWriter.getBuilder()
                                    .setSXSSFWorkbook(100)
                                    .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\streming_excel_test.xlsx"))
                                    .build();

        writer.createSheet("testSheet");

        for (int index = 0; index < 2000000; index++) {
            writer.addCell(String.valueOf(index))
                  .addCell(index)
                  .nextRow();
        }

        writer.write()
              .close();
    }
    
    @Test
    public void 구버전엑셀파일생성() throws Exception {
        PoiWriter writer = PoiWriter.getBuilder()
                                    .setHSSFWorkBook()
                                    .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\old_excel_test.xls"))
                                    .build();

        writer.createSheet("testSheet");

        for (int index = 0; index < 1000; index++) {
            writer.addCell(String.valueOf(index))
                  .addCell(index)
                  .nextRow();
        }

        writer.write()
              .close();
    }
    
    @Test
    public void 사용자지정스타일생성() throws Exception {
        
        PoiWriter writer = PoiWriter.getBuilder()
                                    .setHSSFWorkBook()
                                    .setStringStyleBuilder((workbook) ->{
                                        
                                        CellStyle cellStyle = workbook.createCellStyle();
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderTop(BorderStyle.THIN);
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                    
                    
                                        Font font = workbook.createFont();
                                        font.setFontName("굴림");
                                        font.setFontHeight((short) (10 * 20));
                                        font.setBold(true);
                    
                                        cellStyle.setFont(font);
                                        
                                        return cellStyle;
                                    })
                                    .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\style_test.xls"))
                                    .build();

        CellStyle numberStyle = writer.styleBuild((workbook) ->{
            
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
            
            Font font = workbook.createFont();
            font.setFontName("Tahoma");
            font.setFontHeight((short) (10 * 20));
            font.setBold(true);
            
            cellStyle.setFont(font);
            
            return cellStyle;
        });
        
        writer.createSheet("testSheet");
        
        for (int index = 0; index < 10; index++) {
            writer.addCell(String.valueOf(index))
                  .addCell(index, numberStyle)
                  .nextRow();
        }
        
        writer.write()
              .close();
            
    }
}
