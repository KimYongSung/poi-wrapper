package com.kys.poi.mybatis;

import com.kys.poi.mapping.CellInfo;
import com.kys.poi.mapping.CellInfos;
import com.kys.poi.write.PoiWriter;
import com.kys.util.ObjectUtils;
import com.kys.util.StringUtil;

import java.util.Map;

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

        init();
    }

    private void init(){
        for (CellInfo cellInfo: cellInfos) {
            if(StringUtil.isNotNullorEmpty(cellInfo.getFieldName())){
                throw new IllegalArgumentException("fieldName is null - " + cellInfo.toString());
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
