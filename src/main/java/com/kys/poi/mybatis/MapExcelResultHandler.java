package com.kys.poi.mybatis;

import java.util.Map;

import com.kys.poi.mapping.CellInfo;
import com.kys.poi.mapping.CellInfos;
import com.kys.poi.write.PoiWriter;
import com.kys.util.ObjectUtils;
import com.kys.util.StringUtil;

/**
 * Map 형식 지원
 * 
 * @author kys0213
 * @date 2019. 2. 11.
 */
public class MapExcelResultHandler extends GenericExcelResultHandler<Map<?, ?>> {

    private final CellInfos cellInfos;

    /**
     * MapExcelRowHandler 생성자
     * 
     * @param poiWriter
     * @param sheetName
     * @throws Exception
     */
    public MapExcelResultHandler(PoiWriter poiWriter, String sheetName, CellInfos cellInfos)
            throws Exception {
        super(poiWriter, sheetName, cellInfos);
        this.cellInfos = cellInfos;
        mappingInfoCheck();
    }

    /**
     * MapExcelRowHandler 사용을 위한 필수 값 정보 체크
     */
    private void mappingInfoCheck() {
        for (CellInfo cellInfo : cellInfos) {

            if (StringUtil.isNull(cellInfo.getFieldName())) {
                throw new IllegalArgumentException(cellInfo.getTitleName() + "의 Field 명이 누락되었습니다.");
            }

            if (ObjectUtils.isNull(cellInfo.getDataType())) {
                throw new IllegalArgumentException(cellInfo.getTitleName() + "의 DataType이 누락되었습니다.");
            }
        }
    }

    @Override
    public void addRow(Map<?, ?> row) {
        
        for (CellInfo cellInfo : cellInfos) {
            
            String key = cellInfo.getFieldName();
            Object value = row.get(key);
            
            if (cellInfo.isNumber()) {
                poiWriter.addCell(Double.parseDouble(StringUtil.nullToStr(value, "0")));
            } else {
                poiWriter.addCell(StringUtil.nullToEmpty(value));
            }
        }
    }

}
