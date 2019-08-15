package com.kys.poi.read.cell;

import java.util.ArrayList;
import java.util.List;

public class BasicCelllHandlerFactory {

    private List<CellHandler> handlers = new ArrayList<CellHandler>();

    public BasicCelllHandlerFactory(){
        init();
    }

    private void init() {
        handlers.add(new DoubleHandler());
        handlers.add(new IntegerHandler());
        handlers.add(new LongHandler());
        handlers.add(new ShortHandler());
        handlers.add(new StringHandler());
    }

    /**
     * Handler 등록
     * @param cellHandler
     */
    public void addHandler(CellHandler cellHandler){

        if(cellHandler == null){
            throw new IllegalArgumentException("cellHandler is null");
        }

        handlers.add(cellHandler);
    }

    /**
     * Handler 서치
     * @param clazz
     * @return
     */
    public CellHandler lookup(Class<?> clazz){
        for (CellHandler handler : handlers) {
            if(handler.isSupport(clazz)){
                return handler;
            }
        }

        throw new IllegalStateException("unsupport type - " + clazz.getName());
    }
}
