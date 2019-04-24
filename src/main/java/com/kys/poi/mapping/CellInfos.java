package com.kys.poi.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 셀 매핑 정보
 *
 * @author kys0213
 * @date   2019. 4. 24.
 */
public class CellInfos implements Iterable<CellInfo>{

    private final List<CellInfo> mappings = new ArrayList<>();

    /**
     * 셀 매핑 정보 등록
     * @param cellInfo
     * @return
     */
    public CellInfos regist(CellInfo cellInfo) {
        mappings.add(cellInfo);
        return this;
    }
    
    @Override
    public Iterator<CellInfo> iterator() {
        return mappings.iterator();
    }
}
