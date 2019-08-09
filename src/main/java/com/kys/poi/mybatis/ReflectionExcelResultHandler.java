package com.kys.poi.mybatis;

import java.lang.reflect.Field;

import com.kys.poi.mapping.CellInfo;
import com.kys.poi.mapping.CellInfos;
import com.kys.poi.write.PoiWriter;
import com.kys.util.ObjectUtils;
import com.kys.util.ReflectionUtil;
import com.kys.util.StringUtil;

/**
 * VO 형식 클래스 Reflection으로 처리
 * 
 * @author kys0213
 * @since 2018. 4. 23.
 */
public class ReflectionExcelResultHandler extends GenericExcelResultHandler<Object> {

    /**
     * Mapping 정보 List
     */
    private final CellInfos cellInfos;

    /**
     * 설정된 resultType 클래스
     */
    private final Class<?> clazz;

    /**
     * ReflectionExcelRowHandler 생성자
     * 
     * @param poiWriter 엑셀 생성자
     * @param sheetName sheet 명
     * @param clazz     mybatis parameterType class 정보
     * @param cellInfos  엑셀파일 매핑 정보
     * @throws Exception 초기화 중 에러 발생시
     */
    public ReflectionExcelResultHandler(PoiWriter poiWriter, String sheetName, Class<?> clazz, CellInfos cellInfos) {
        super(poiWriter, sheetName, cellInfos); 
        this.cellInfos = cellInfos;
        this.clazz = clazz;

        init();
    }

    /**
     * ReflectionExcelRowHandler 사용을 위한 필수 값 정보 체크 및 field 캐싱
     */
    private void init() {

        for (CellInfo cellInfo : cellInfos) {

            if (StringUtil.isNull(cellInfo.getFieldName())) {
                throw new IllegalArgumentException(cellInfo.getTitleName() + "의 Field 명이 누락되었습니다.");
            }

            if (ObjectUtils.isNull(cellInfo.getDataType())) {
                throw new IllegalArgumentException(cellInfo.getDataType() + "의 DataType이 누락되었습니다.");
            }

            String fieldName = cellInfo.getFieldName();

            Field findField = ReflectionUtil.findField(clazz, fieldName);

            if (ObjectUtils.isNull(findField)) {
                throw new IllegalArgumentException(
                        cellInfo.getTitleName() + "(" + cellInfo.getFieldName() + ") 필드 존재하지 않습니다.");
            }

            cellInfo.setField(findField);
        }
    }

    @Override
    public void addRow(Object vo) {
        for (CellInfo cellInfo : cellInfos) {

            Object value = ReflectionUtil.getField(cellInfo.getField(), vo);

            if (cellInfo.isNumber()) {
                poiWriter.addCell(Double.parseDouble(StringUtil.nullToStr(value, "0")));
            } else {
                poiWriter.addCell(StringUtil.nullToEmpty(value));
            }
        }
    }
}
