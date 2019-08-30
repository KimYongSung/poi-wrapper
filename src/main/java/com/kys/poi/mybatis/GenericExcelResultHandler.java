package com.kys.poi.mybatis;

import com.kys.poi.mapping.CellInfo;
import com.kys.poi.mapping.CellInfos;
import com.kys.poi.write.PoiWriter;
import lombok.Getter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * 제네릭 엑셀 resultHandler
 *
 * @author kys0213
 * @date   2019. 4. 24.
 */
public abstract class GenericExcelResultHandler<T> implements ResultHandler<T> {

    protected final PoiWriter poiWriter;

    /**
     * db cursor 별로 실행한 건수
     */
    @Getter
    private int executeCnt = 0;

    /**
     * GenericExcelResultHandler 생성자
     * 
     * @param poiWriter
     * @param sheetName
     * @throws Exception
     */
    public GenericExcelResultHandler(PoiWriter poiWriter, String sheetName, CellInfos cellInfos) {
        this.poiWriter = poiWriter;
        poiWriter.createSheet(sheetName, cellInfos);


    }

    /**
     * row 추가
     * 
     * @param t
     */
    public abstract void addRow(T t);

    @Override
    public void handleResult(ResultContext<? extends T> resultContext) {
        executeCnt++;
        addRow(resultContext.getResultObject());
        poiWriter.nextRow();
    }
}
