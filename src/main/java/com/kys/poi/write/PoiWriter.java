package com.kys.poi.write;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kys.poi.mapping.ExcelMappingInfo;
import com.kys.poi.style.CellStyleBuilder;
import com.kys.util.ResourceUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * org.apache.poi 를 이용한 excel 생성 클래스 <br><br>
 * <strong>주의 : thread에 안전하지 않습니다. 싱글톤으로 사용하지 마세요 </strong><br><br>
 * <li>.xlsx 확장자는 row 1,040,000건을 넘어 갈 경우 다음 sheet에 데이터 출력 </li>
 * <li>.xls 확장자는 row 60,000건을 넘어 갈 경우 다음 sheet에 데이터 출력 </li>
 * <br>참조 : {@link PoiWriterTest}
 * <br>
 * <br><br>
 * @author kys0213
 * @since  2018. 4. 30.
 */
@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
public class PoiWriter implements Closeable{
	
	/**
	 * sheet에 최대 출력 가능한 row 수
	 */
	private final int MAX_ROW;
	
	/**
	 * 통합 문서 객체
	 */
	private final Workbook workBook;
	
	/**
	 * 출력 stream
	 */
	private final OutputStream outputStream;
	
	/**
	 * title Style 정보
	 */
	private final CellStyle titleStyle;
	
	/**
	 * number Style 정보
	 */
	private final CellStyle numberStyle;
	
	/**
	 * string Style 정보
	 */
	private final CellStyle stringStyle;
	
	/**
	 * 엑셀 매핑 정보
	 */
	private List<ExcelMappingInfo> titleList;
	
	/**
	 * excel 통합문서상에 작성중인 sheet
	 */
	private Sheet sheet;
	
	/**
	 * Sheet 이름
	 */
	private String sheetName;
	
	/**
	 * 현재 sheet의 처리하는 row 
	 */
	private Row row;
	
	/**
	 * 현재 row의 처리하는 cell 
	 */
	private Cell cell;
	
	/**
	 * 통합문서의 sheetIndex
	 */
	private int sheetIndex = 0;
	
	/**
	 * 열 위치
	 */
	private int rowIndex = 0;
	
	/**
	 * 행 위치
	 */
	private int columnIndex = 0;
	
	/**
	 * Builder 생성
	 * @return PoiWriterBuilder
	 */
	public static PoiWriterBuilder getBuilder(){
		return new PoiWriterBuilder();
	}
	
	/**
	 * sheet 생성
	 * @param sheetName
	 */
	public PoiWriter createSheet(String sheetName){
		sheetIndex = 0;
		this.sheetName = sheetName;
		createSheet();
		return this;
	}
	
	/**
	 * sheet 생성
	 * @param sheetName
	 * @param mappingList
	 */
	public PoiWriter createSheet(String sheetName, List<ExcelMappingInfo> mappingList) {
		this.createSheet(sheetName);
		this.setTitle(mappingList);
		return this;
	}
	
	/**
	 * 타이틀 셋팅
	 * @param mappingList
	 */
	public PoiWriter setTitle(List<ExcelMappingInfo> mappingList){
		this.titleList = mappingList; 
		addTitles();
		nextRow();
		return this;
	}
	
	/**
	 * 셀 병합
	 * @param startRow    병합 시작 열
	 * @param endRow	  병합 종료 열
	 * @param startColumn 병합 시작 행
	 * @param endColumn   병합 종료 행
	 */
	public PoiWriter cellMerged(int startRow, int endRow, int startColumn, int endColumn){
		sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startColumn, endColumn));
		return this;
	}
	
	/**
	 * 현재 열에서 행만 병합
	 * @param startColumn
	 * @param endColumn
	 */
	public PoiWriter currentRowCellMerged(int startColumn, int endColumn){
		cellMerged(rowIndex, rowIndex, startColumn, endColumn);
		return this;
	}
	
	/**
	 * 현재 행에서 열만 병합
	 * @param startRow
	 * @param endRow
	 */
	public PoiWriter currentCellRowMerged(int startRow, int endRow){
		cellMerged(startRow, endRow, columnIndex, columnIndex);
		return this;
	}
	
	/**
	 * 다음 열로 이동 
	 */
	public PoiWriter nextRow(){
		
		// MAX 열 초과시 sheet 생성
		if(rowIndex > MAX_ROW){
			createSheet();
			setTitle(titleList);
		}else{
			// row 추가
			rowIndex++;
			createRow(rowIndex);
		}
		return this;
	}
	
	/**
	 * 현재 row 위치
	 * @return
	 */
	public int currentRow(){
		return rowIndex;
	}
	
	/**
	 * 현재 cell 위치
	 * @return
	 */
	public int currentColumn(){
		return columnIndex;
	}
	
	/**
	 * 현재 sheet의 마지막 row 위치
	 * @return
	 */
	public int lastRowNum(){
		return sheet.getLastRowNum();
	}

	/**
	 * 열 위치 변경
	 * @param rowIndex 변경할 위치
	 * @throws IllegalArgumentException 음수일 경우 발생
	 */
	public PoiWriter moveRow(int rowIndex){
		if(rowIndex < 0){
			throw new IllegalArgumentException("음수가 들어왔습니다. ( " + rowIndex + " )");
		}
		
		this.rowIndex = rowIndex;
		createRow(this.rowIndex);
		return this;
	}

	/**
	 * 다음 셀로 이동
	 * @return
	 */
	public PoiWriter nextCell() {
		columnIndex++;
		return this;
	}

	/**
	 * 행 위치 변경
	 * @param cellIndex 변경할 위치
	 * @throws IllegalArgumentException 음수일 경우 발생
	 */
	public PoiWriter moveCell(int cellIndex){
		if(cellIndex < 0){
			throw new IllegalArgumentException("음수가 들어왔습니다. ( " + cellIndex + " )");
		}
		
		columnIndex = cellIndex;
		return this;
	}

	/**
	 * 엑셀 타이틀 추가
	 * @param data
	 */
	public PoiWriter addTitle(String data){
		addCell(data,titleStyle);
		return this;
	}
	
	/**
	 * 문자형식 데이터 추가
	 * @param data
	 */
	public PoiWriter addCell(String data){
		addCell(data, stringStyle);
		return this;
	}
	
	/**
	 * 숫자형식 데이터 추가
	 * @param data
	 */
	public PoiWriter addCell(double data){
		addCell(data, numberStyle);
		return this;
	}
	
	/**
	 * 데이터 추가
	 * @param data
	 * @param customStyle
	 */
	public PoiWriter addCell(double data, CellStyle customStyle){
		createCell(CellType.NUMERIC, customStyle);
		cell.setCellValue(data);
		nextCell();
		return this;
	}
	
	/**
	 * 데이터 추가
	 * @param data
	 * @param customStyle
	 */
	public PoiWriter addCell(String data, CellStyle customStyle){
		createCell(CellType.STRING, customStyle);
		cell.setCellValue(data);
		nextCell();
		return this;
	}
	
	/**
	 * 공백 데이터 추가
	 * @return
	 */
	public PoiWriter addEmptyCell(){
		return addCell("", stringStyle);
	}

	/**
	 * 공백 데이터 추가
	 * @return
	 */
	public PoiWriter addEmptyCell(CellStyle customStyle){
		return addCell("", customStyle);
	}

	/**
	 * 함수 추가
	 * <li>정수형 스타일 적용</li>
	 * <br>
	 * @param strFormula 엑셀 함수
	 * @return
	 */
	public PoiWriter addFunction(String strFormula){
		return addFunction(strFormula, numberStyle);
	}
	
	/**
	 * 함수 추가
	 * @param strFormula 엑셀 함수
	 * @param customStyle 해당 cell style
	 * @return
	 */
	public PoiWriter addFunction(String strFormula, CellStyle customStyle){
		createCell(CellType.FORMULA, customStyle);
		cell.setCellFormula(strFormula);
		nextCell();
		return this;
	}
	
	/**
	 * 셀 가로 길이 조정
	 * @param index
	 * @param width
	 */
	public PoiWriter cellWidth(int index, int width) {
		sheet.setColumnWidth(index, width * 500);
		return this;
	}

	/**
	 * Excel 파일 출력
	 * @throws IOException
	 */
	public PoiWriter write() throws IOException{
		workBook.write(outputStream);
		
		// SXSS 일 경우 dispose 호출
		if(workBook instanceof SXSSFWorkbook){
			((SXSSFWorkbook) workBook).dispose();
		}
		
		return this;
	}

	/**
	 * Stream close
	 */
	@Override
	public void close(){
		ResourceUtil.close(outputStream);
	}

	/**
	 * Cell Style 생성
	 * @param builder {@link CellStyleBuilder} 구현체
	 * @return 구현체에서 작성된 CellStyle 객체
	 */
	public CellStyle styleBuild(CellStyleBuilder builder){
		return builder.build(workBook);
	}
	
	/**
	 * 타이틀 추가
	 */
	private void addTitles() {
		
		for (ExcelMappingInfo mappingInfo : titleList) {
			addTitle(mappingInfo.getTitleName(), mappingInfo.getWidth());
		}
	}
	
	/**
	 * 엑셀 타이틀 추가
	 * @param data
	 */
	private PoiWriter addTitle(String data, int width){
		addCell(data, titleStyle);
		cellWidth(currentColumn(), width);
		nextCell();
		return this;
	}

	/**
	 * sheet 생성
	 */
	private void createSheet(){
		
		String sheetName = sheetIndex == 0 ? this.sheetName : this.sheetName + sheetIndex;
		
		// 1. sheet 생성
		sheet = workBook.createSheet(sheetName);
		
		// 2. sheet 인덱스 증가
		sheetIndex++;
		
		// 3. 열 위치 초기화
		rowIndex = 0;
		
		// 4. row 생성
		createRow(rowIndex);
	}

	/**
	 * cell 생성
	 * @param type
	 * @param cellStype
	 * @return
	 */
	private void createCell(CellType type, CellStyle cellStype){
		
		cell = row.getCell(columnIndex);
		
		if(cell == null){
			cell = row.createCell(columnIndex, type);
			cell.setCellStyle(cellStype);
		}
	}

	/**
	 * row 생성
	 */
	private void createRow(int rowIndex) {
		
		// 1. 행 위치 초기화
		columnIndex = 0;
	
		// 2. row 생성
		row = sheet.getRow(rowIndex);
		if(row == null){
			row = sheet.createRow(rowIndex);
		}
	}
}
