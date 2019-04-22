package com.kys.poi.mybatis;
import java.util.List;
import java.util.Map;

import com.kys.poi.mapping.ExcelMappingInfo;
import com.kys.poi.write.PoiWriter;
import com.kys.util.ObjectUtils;
import com.kys.util.StringUtil;

/**
 * Map 형식 지원 
 * 
 * @author kys0213
 * @date   2019. 2. 11.
 */
public class MapExcelResultHandler extends GenericExcelResultHandler<Map<?,?>> {
	
	private final List<ExcelMappingInfo> mappingList;

	/**
	 * MapExcelRowHandler 생성자
	 * @param poiWriter
	 * @param sheetName
	 * @throws Exception
	 */
	public MapExcelResultHandler(PoiWriter poiWriter, String sheetName, List<ExcelMappingInfo> mappings) throws Exception {
		super(poiWriter, sheetName, mappings);
		this.mappingList = mappings;
		mappingInfoCheck();
	}
	
	/**
	 * MapExcelRowHandler 사용을 위한 필수 값 정보 체크
	 */
	private void mappingInfoCheck() {
		for (ExcelMappingInfo excelMappingInfo : mappingList) {

			if(StringUtil.isNull(excelMappingInfo.getFieldName())){ 
				throw new IllegalArgumentException(excelMappingInfo.getTitleName() +"의 Field 명이 누락되었습니다.");
			}
			
			if(ObjectUtils.isNull(excelMappingInfo.getDataType())){
				throw new IllegalArgumentException(excelMappingInfo.getTitleName() +"의 DataType이 누락되었습니다.");
			}
		}
	}

	@Override
	public void addRow(Map<?, ?> row) {
		for (ExcelMappingInfo excelMappingInfo : mappingList) {
			
			String key = excelMappingInfo.getFieldName();
			Object value = row.get(key);
			
			if(excelMappingInfo.isNumber()){
				poiWriter.addCell(Double.parseDouble(StringUtil.nullToStr(value, "0")));
			}else{
				poiWriter.addCell(StringUtil.nullToEmpty(value));
			}
		}
	}

}
