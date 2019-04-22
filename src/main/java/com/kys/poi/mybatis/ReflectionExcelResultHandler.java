package com.kys.poi.mybatis;

import java.lang.reflect.Field;
import java.util.List;

import com.kys.poi.mapping.ExcelMappingInfo;
import com.kys.poi.write.PoiWriter;
import com.kys.util.ObjectUtils;
import com.kys.util.ReflectionUtil;
import com.kys.util.StringUtil;

/**
 * VO 형식 클래스 Reflection으로 처리
 * 
 * @author kys0213
 * @since  2018. 4. 23.
 */
public class ReflectionExcelResultHandler extends GenericExcelResultHandler<Object>{
	
	/**
	 * Mapping 정보 List
	 */
	private final List<ExcelMappingInfo> mappingList;
	
	/**
	 * 설정된 resultType 클래스
	 */
	private final Class<?> clazz;
	
	/**
	 * ReflectionExcelRowHandler 생성자
	 * @param poiWriter 엑셀 생성자
	 * @param sheetName sheet 명
	 * @param clazz     mybatis parameterType class 정보
	 * @param mappings  엑셀파일 매핑 정보
	 * @throws Exception
	 */
	public ReflectionExcelResultHandler(PoiWriter poiWriter, String sheetName, Class<?> clazz, List<ExcelMappingInfo> mappings) {
		super(poiWriter, sheetName, mappings);
		this.mappingList = mappings;
		this.clazz = clazz;
		
		init();
	}
	
	/**
	 * ReflectionExcelRowHandler 사용을 위한 필수 값 정보 체크 및 field 캐싱
	 */
	private void init() {
		
		for (ExcelMappingInfo excelMappingInfo : mappingList) {

			if(StringUtil.isNull(excelMappingInfo.getFieldName())){ 
				throw new IllegalArgumentException(excelMappingInfo.getTitleName() +"의 Field 명이 누락되었습니다.");
			}
			
			if(ObjectUtils.isNull(excelMappingInfo.getDataType())){
				throw new IllegalArgumentException(excelMappingInfo.getTitleName() +"의 DataType이 누락되었습니다.");
			}
			
			String fieldName = excelMappingInfo.getFieldName();
			
			Field findField = ReflectionUtil.findField(clazz, fieldName);
			
			if(ObjectUtils.isNull(findField)){
				throw new IllegalArgumentException(excelMappingInfo.getTitleName() +"("+excelMappingInfo.getFieldName()+") 필드 존재하지 않습니다.");
			}
			
			excelMappingInfo.setField(findField);
		}
	}


	@Override
	public void addRow(Object vo) {
		for (ExcelMappingInfo excelMappingInfo : mappingList) {
			
			Object value = ReflectionUtil.getField(excelMappingInfo.getField(), vo);
			
			if(excelMappingInfo.isNumber()){
				poiWriter.addCell(Double.parseDouble(StringUtil.nullToStr(value, "0")));
			}else{
				poiWriter.addCell(StringUtil.nullToEmpty(value));
			}
		}
	}
}
