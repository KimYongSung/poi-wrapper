package com.kys.poi.read;

import com.kys.poi.mapping.CellInfos;
import com.kys.poi.read.cell.*;
import com.kys.poi.read.row.BasicPoiRowReader;
import com.kys.poi.read.row.PoiRowReader;

import java.net.URL;

/**
 * PoiReader builder 클래스
 *
 * @author kys0213
 * @since  2019. 8. 1.
 */
public class PoiReaderBuilder<T> {

    private Class<T> clazz;

    private URL url;

    private PoiRowReader<T> rowMapper;

    private CellInfos cellInfos;

    private boolean isFirstRowSkip = false;

    private PoiRowHandler<T> rowHandler;

    private BasicCelllHandlerFactory cellHandlers = new BasicCelllHandlerFactory();

    /**
     * PoiReaderBuilder 생성자
     * @param clazz row를 매핑할 클래스 정보
     */
    protected PoiReaderBuilder(Class<T> clazz){

        if(clazz == null){
            throw new IllegalArgumentException("clazz is null");
        }

        this.clazz = clazz;
    }

    /**
     * 엑셀 파일 정보
     * @param url
     * @return
     */
    public PoiReaderBuilder<T> url(URL url){
        this.url = url;
        return this;
    }

    /**
     * Cell 매핑지원 클래스
     * @param handler
     * @return
     */
    public PoiReaderBuilder<T> addCellHandler(CellHandler handler){
        cellHandlers.addHandler(handler);
        return this;
    }

    /**
     * RowReader 설정
     * @param rowReader
     * @return
     */
    public PoiReaderBuilder<T> rowReader(PoiRowReader<T> rowReader){

        if(rowReader == null){
            throw new IllegalArgumentException("rowReader is null");
        }

        this.rowMapper = rowReader;
        return this;
    }

    /**
     * 셀 매핑 정보 등록
     * @param cellinfos
     * @return
     */
    public PoiReaderBuilder<T> cellNames(CellInfos cellinfos){

        if(cellinfos == null){
            throw new IllegalArgumentException("CellInfos is null");
        }

        this.cellInfos = cellinfos;
        return this;
    }

    /**
     * 첫번째 row skip 여부
     * @return
     */
    public PoiReaderBuilder<T> firstRowSkip(){
        this.isFirstRowSkip = true;
        return this;
    }

    /**
     * RowHandler 설정
     * @param rowHandler
     * @return
     */
    public PoiReaderBuilder<T> poiRowHandler(PoiRowHandler<T> rowHandler){
        this.rowHandler = rowHandler;
        return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PoiReader build() throws Exception{

        if(rowMapper == null){
            rowMapper = new BasicPoiRowReader(clazz, cellInfos);
        }

        if(rowHandler == null){
            throw new IllegalArgumentException("rowHandler is null");
        }

        rowMapper.setCellHandlers(cellHandlers);
        rowMapper.setRowHandler(rowHandler);

        return new PoiReader(url, rowMapper, isFirstRowSkip);
    }
}
