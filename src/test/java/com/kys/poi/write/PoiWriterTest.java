package com.kys.poi.write;

import com.kys.poi.style.CellStyleBuilder;
import com.kys.util.ResourceUtil;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class PoiWriterTest {

    @Test
    public void 엑셀파일생성() throws Exception {

        File file = getFile();

        PoiWriter writer = PoiWriter.getBuilder()
                                    .setXSSFWorkbook()
                                    .setOutputStream(new FileOutputStream(file.getAbsolutePath() + "/excel_test.xlsx"))
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
    public void 스트림엑셀파일생성() throws Exception {

        // given
        File file = getFile();

        PoiWriter writer = PoiWriter.getBuilder()
                                    .setSXSSFWorkbook(100)
                                    .setOutputStream(new FileOutputStream(file.getAbsolutePath() + "/streming_excel_test.xlsx"))
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
    public void _07버전엑셀파일생성() throws Exception {
        PoiWriter writer = PoiWriter.getBuilder()
                                    .setHSSFWorkBook()
                                    .setOutputStream(new FileOutputStream(getFile().getAbsolutePath() + "/2007_excel_test.xls"))
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
                                    .setStringStyleBuilder(new CellStyleBuilder() {
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
                                            font.setFontName("굴림");
                                            font.setFontHeight((short) (10 * 20));
                                            font.setBold(true);

                                            cellStyle.setFont(font);

                                            return cellStyle;
                                        }
                                    })
                                    .setOutputStream(new FileOutputStream(getFile().getAbsolutePath() + "/custom_style_test.xls"))
                                    .build();

        CellStyle numberStyle = writer.styleBuild(new CellStyleBuilder() {
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
                font.setBold(true);

                cellStyle.setFont(font);

                return cellStyle;
            }
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

    private File getFile() {
        return new File("excel");
    }
}
