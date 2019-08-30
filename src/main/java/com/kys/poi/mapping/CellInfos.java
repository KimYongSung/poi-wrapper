package com.kys.poi.mapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 셀 매핑 정보
 *
 * @author kys0213
 * @date   2019. 4. 24.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellInfos implements Iterator<CellInfo>, Iterable<CellInfo>{

    private final List<CellInfo> mappings = new ArrayList<CellInfo>();

    private int index = 0;

    public static CellInfos newInstance(){
        return new CellInfos();
    }

    @Override
    public void remove() {
        // unsupport operation
    }

    /**
     * 셀 매핑 정보 등록
     * @param cellInfo
     * @return
     */
    public CellInfos add(CellInfo cellInfo) {
        mappings.add(cellInfo);
        return this;
    }
    
    @Override
    public boolean hasNext() {
        return index < mappings.size();
    }

    @Override
    public CellInfo next() {
        CellInfo cellInfo = mappings.get(index);
        index++;
        return cellInfo;
    }

    public void reset(){
        this.index = 0;
    }

    @Override
    public Iterator<CellInfo> iterator() {
        return mappings.iterator();
    }

}
