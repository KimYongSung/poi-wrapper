package com.kys.poi.read.row;

import com.kys.poi.mapping.CellInfo;
import com.kys.poi.mapping.CellInfos;
import com.kys.poi.read.PoiRowHandler;
import com.kys.poi.read.cell.CellHandler;
import com.kys.poi.read.cell.BasicCelllHandlerFactory;
import com.kys.util.ReflectionUtil;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.lang.reflect.Field;

/**
 * 기본 PoiReader
 * @param <T>
 */
public class BasicPoiRowReader<T> implements PoiRowReader<T> {

    /**
     * 매핑할 row 정보
     */
    private final Class<T> clazz;

    private final CellInfos cellInfos;

    private boolean isSingleton;

    private BasicCelllHandlerFactory cellHandlers;

    private PoiRowHandler<T> rowHandler;

    private T obj;

    /**
     * BasicPoiReader 생성자
     * @param clazz 매핑할 VO 클래스
     * @param cellInfos cell매핑 정보
     */
    public BasicPoiRowReader(Class<T> clazz, CellInfos cellInfos) {
        super();
        this.clazz = clazz;
        this.cellInfos = cellInfos;
    }

    @Override
    public void setCellHandlers(BasicCelllHandlerFactory cellHandlers) {
        this.cellHandlers = cellHandlers; 
    }

    @Override
    public void setRowHandler(PoiRowHandler<T> rowHandler) {
        this.rowHandler = rowHandler;
    }

    @Override
    public void isSingleton(boolean isSingleton) {
        this.isSingleton = isSingleton;
    }

    @Override
    public void start(){
        obj = ( obj == null ) ? ReflectionUtil.newInstance(clazz) :
                ( isSingleton ) ? obj : ReflectionUtil.newInstance(clazz);
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {

        if(!cellInfos.hasNext()){
            // not support cell
            return;
        }

        CellInfo cellInfo = cellInfos.next();

        Field field = cellInfo.getField();

        if(field == null){
            field = ReflectionUtil.findField(clazz, cellInfo.getFieldName());
            cellInfo.setField(field);
        }

        CellHandler handler = cellHandlers.lookup(field.getType());

        Object value = handler.nullSafeValue(formattedValue);

        ReflectionUtil.setField(field, this.obj, value);
    }

    @Override
    public void header(String text, String tagName) {
    }

    @Override
    public void footer(String text, String tagName) {
    }

    @Override
    public void end(){
        cellInfos.reset();
        rowHandler.handler(obj);
    }
}
