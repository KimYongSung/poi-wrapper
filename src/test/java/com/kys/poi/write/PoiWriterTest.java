package com.kys.poi.write;

import java.io.FileOutputStream;

import org.junit.Test;

public class PoiWriterTest {

	@Test
	public void 엑셀파일생성() throws Exception {
		PoiWriter writer = PoiWriter.getBuilder()
								    .setXSSFWorkbook()
								    .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\excel_test.xlsx"))
								    .build();

		writer.createSheet("testSheet");
		
		for (int index = 0; index < 100; index++) {
			writer.addCell(String.valueOf(index))
				  .addCell(index)
				  .nextRow();
		}
		
		writer.write()
			  .close();
	}
}
