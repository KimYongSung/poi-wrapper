package com.kys.poi.write;

import org.apache.poi.ss.usermodel.CellStyle;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    
}
