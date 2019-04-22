package com.kys.poi.read;

import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class PoiSheetHandler implements SheetContentsHandler{
	
	private final PoiRowHandler handler;
	
	private int cellIndex;
	
	/**
	 * 엑셀 첫번째 로우 스킵 여부
	 */
	@Setter
	private boolean isFirstRowSkip = true;
	
	/**
	 * 현재 진행하는 row skip 여부
	 */
	private boolean isSkip = false;
	
	private Object currentObj;
	
	/**
	 * PoiSheetHandler
	 * @param handler
	 * @param isFirstRowSkip
	 */
	public PoiSheetHandler(PoiRowHandler handler, boolean isFirstRowSkip) {
		this(handler);
		this.isFirstRowSkip = isFirstRowSkip;
	}

	@Override
	public void startRow(int rowNum) {
		this.currentObj = handler.getInstance();
		this.cellIndex = 0;
		isSkip = (rowNum == 0 && isFirstRowSkip) ? true : false;
	}

	@Override
	public void endRow(int rowNum) {
		
		if(isSkip) return;
		handler.handling(currentObj);
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if(isSkip) return;
		handler.mapping(cellReference, formattedValue, comment, currentObj, cellIndex);
		cellIndex++;
	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {
		
	}
}
