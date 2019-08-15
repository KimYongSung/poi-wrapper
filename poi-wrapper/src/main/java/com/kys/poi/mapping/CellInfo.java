package com.kys.poi.mapping;

import com.kys.util.ObjectUtils;
import com.kys.util.StringUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;

/**
 * cell 매핑 정보
 */
@ToString
public class CellInfo {

    /**
     * 엑셀 sheet 셀명
     */
    @Getter
    private String cellName;

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
     * Field 정보
     */
    @Setter
    @Getter
    private Field field;

    /**
     * 해당 셀이 정수형 타입 여부
     * @return 정수형 여부
     */
    public boolean isNumber() {
        return dataType == null ? false : this.dataType.equals(DataType.NUMBER);
    }

    /**
     * 해당 셀이 문자형 타입 여부
     * @return 문자형 여부
     */
    public boolean isString() {
        return dataType == null ? true : this.dataType.equals(DataType.STRING);
    }

    @Builder
    private CellInfo(String cellName, String fieldName, DataType dataType, int width) {

        this.cellName = cellName;
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.width = width;
    }
}
