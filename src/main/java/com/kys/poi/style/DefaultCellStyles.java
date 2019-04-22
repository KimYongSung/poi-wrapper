package com.kys.poi.style;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class DefaultCellStyles {

	/**
	 * Default Title Format Builder
	 */
	public static final CellStyleBuilder defaultTitleBuilder = (workBook) -> {

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
		font.setFontHeight((short)(10*20));
		font.setBold(true);

		cellStyle.setFont(font);

		return cellStyle;
	};



	/**
	 * Default Number Format Builder
	 */
	public static final CellStyleBuilder defaultNumberBuilder = (workBook) ->{

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
		font.setFontHeight((short)(10*20));

		cellStyle.setFont(font);

		return cellStyle;
	};

	/**
	 * Default String Format Builder
	 */
	public static final CellStyleBuilder defaultStringBuilder = (workBook) -> {

		CellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		Font font = workBook.createFont();
		font.setFontName("Tahoma");
		font.setFontHeight((short)(10*20));

		cellStyle.setFont(font);

		return cellStyle;
	};
}
