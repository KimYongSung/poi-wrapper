package com.kys.poi.mapping;

import java.lang.reflect.Field;

/**
 * cell 매핑 정보
 */
public class CellInfo {

    /**
     * 엑셀 sheet 셀명
     */
    private String cellName;

    /**
     * DB 데이터 필드 Key 명
     */
    private String fieldName;

    /**
     * DataType
     */
    private DataType dataType;

    /**
     * 엑셀 열 너비
     */
    private int width;

    /**
     * Field 정보
     */
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

    public static CellInfoBuilder builder(){
        return new CellInfoBuilder();
    }

    public String getCellName() {
        return cellName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public int getWidth() {
        return width;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    private CellInfo(String cellName, String fieldName, DataType dataType, int width) {
        this.cellName = cellName;
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.width = width;
    }

    public static class CellInfoBuilder{
        /**
         * 엑셀 sheet 셀명
         */
        private String cellName;

        /**
         * DB 데이터 필드 Key 명
         */
        private String fieldName;

        /**
         * DataType
         */
        private DataType dataType;

        /**
         * 엑셀 열 너비
         */
        private int width;

        public CellInfoBuilder cellName(String cellName){
            this.cellName = cellName;
            return this;
        }

        public CellInfoBuilder fieldName(String fieldName){
            this.fieldName = fieldName;
            return this;
        }

        public CellInfoBuilder dataType(DataType dataType){
            this.dataType = dataType;
            return this;
        }

        public CellInfoBuilder width(int width){
            this.width = width;
            return this;
        }

        public CellInfo build(){
            return new CellInfo(cellName, fieldName, dataType, width);
        }

    }

}
