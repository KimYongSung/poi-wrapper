package com.kys.poi.mapping;

import java.lang.reflect.Field;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CellInfo {

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
    
    @Getter
    @Setter
    private Field field;

    /**
     * 해당 셀이 정수형 타입 여부
     * @return
     */
    public boolean isNumber() {
        return this.dataType.equals(DataType.NUMBER);
    }

    /**
     * 해당 셀이 문자형 타입 여부
     * @return
     */
    public boolean isString() {
        return this.dataType.equals(DataType.STRING);
    }
}
