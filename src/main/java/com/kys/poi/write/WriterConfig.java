package com.kys.poi.write;

import org.apache.poi.ss.usermodel.CellStyle;

public class WriterConfig {

    /**
     * sheet에 최대 출력 가능한 row 수
     */
    private final int MAX_ROW;

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
     * 최대 row 여부
     * @param rowIndex
     * @return
     */
    public boolean isMaxRow(int rowIndex) {
        return MAX_ROW <= rowIndex;
    }

    protected WriterConfig(int MAX_ROW, CellStyle titleStyle, CellStyle numberStyle, CellStyle stringStyle) {
        this.MAX_ROW = MAX_ROW;
        this.titleStyle = titleStyle;
        this.numberStyle = numberStyle;
        this.stringStyle = stringStyle;
    }

    public CellStyle getTitleStyle() {
        return titleStyle;
    }

    public CellStyle getNumberStyle() {
        return numberStyle;
    }

    public CellStyle getStringStyle() {
        return stringStyle;
    }
}
