package com.kys.poi.mybatis;

import java.util.List;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import com.kys.poi.mapping.ExcelMappingInfo;
import com.kys.poi.write.PoiWriter;

import lombok.Getter;

public abstract class GenericExcelResultHandler<T> implements ResultHandler<T>{
	
	protected final PoiWriter poiWriter;
	
	/**
	 * db cursor 별로 실행한 건수
	 */
	@Getter
	private int executeCnt = 0;
	
	/**
	 * GenericExcelResultHandler 생성자
	 * @param poiWriter
	 * @param sheetName
	 * @throws Exception
	 */
	public GenericExcelResultHandler(PoiWriter poiWriter, String sheetName, List<ExcelMappingInfo> mappingInfos){
		this.poiWriter = poiWriter;
		poiWriter.createSheet(sheetName, mappingInfos);
	}

	/**
	 * row 추가
	 * @param t
	 */
	public abstract void addRow(T t);
	
	@Override
	public void handleResult(ResultContext<? extends T> resultContext) {
		executeCnt++;
		addRow(resultContext.getResultObject());
		poiWriter.nextRow();
	}
}
