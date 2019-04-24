package com.kys.poi.mapping;

import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

public class ExcelMappingInfo {

    /**
     * 엑셀 sheet 타이틀 명
     */
    @Getter
    private String titleName;

    /**
     * DB 데이터 필드 Key 명
     */
    @Getter
    private String fieldName;

    /**
     * DataType
     */
    @Getter
    private DataType dataType;

    /**
     * 엑셀 열 너비
     */
    @Getter
    private int width;

    /**
     * Reflection 성능 향상을 위한 변수
     */
    @Getter
    @Setter
    private Field field;

    /**
     * 엑셀 생성 매핑 정보
     * 
     * @param titleName 엑셀상 타이틀 명
     * @param fieldName 매핑할 필드명
     * @param width     셀별 가로 길이
     * @param dataType  셀 dataType {@link DataType}
     */
    public ExcelMappingInfo(String titleName, String fieldName, int width, DataType dataType) {
        this(titleName, width);
        this.fieldName = fieldName;
        this.dataType = dataType;
    }

    /**
     * 엑셀 생성 매핑 정보
     * 
     * <br>
     * <br>
     * DataType.STRING 으로만
     * 
     * @param titleName 엑셀상 타이틀 명
     * @param fieldName 매핑할 필드명
     * @param width     셀별 가로 길이
     */
    public ExcelMappingInfo(String titleName, String fieldName, int width) {
        this(titleName, fieldName, width, DataType.STRING);
    }

    /**
     * 엑셀 생성 매핑 정보
     * 
     * @param titleName 엑셀상 타이틀 명
     * @param width     셀별 가로 길이
     */
    public ExcelMappingInfo(String titleName, int width) {
        super();
        this.titleName = titleName;
        this.width = width;
    }

    public boolean isNumber() {
        return this.dataType.equals(DataType.NUMBER);
    }

    public boolean isString() {
        return this.dataType.equals(DataType.STRING);
    }
}
