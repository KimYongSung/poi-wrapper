package com.kys.poi.read;

import com.kys.poi.read.row.PoiRowReader;
import com.kys.util.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * POI Straming 엑셀 리더 <br>
 * <br>
 * <strong>.xlsx 확장자만 가능 합니다.</strong> <br>
 * @author kys0213
 * @date 2019. 3. 25.
 */
@Slf4j
public class PoiReader implements Closeable {

    private final OPCPackage opk;

    private final ReadOnlySharedStringsTable tables;

    private final StylesTable styles;

    private final SheetIterator sheets;

    private final PoiRowReader<?> rowReader;
    
    private final PoiSheetHandler handler;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> PoiReaderBuilder<T> builder(Class<T> clazz){
        return new PoiReaderBuilder(clazz);
    }

    /**
     * PoiReader 생성
     * 
     * @param excelUrl 엑셀파일 정보
     * @param rowHandler PoiRowHandler 정보
     * @param isFirstRowSkip 첫번째 row skip 여부
     * @throws Exception
     */
    protected PoiReader(URL excelUrl, PoiRowReader<?> rowHandler, boolean isFirstRowSkip) throws Exception {

        this.opk = OPCPackage.open(excelUrl.getPath(), PackageAccess.READ);

        XSSFReader xssfReader = new XSSFReader(this.opk);
        this.styles = xssfReader.getStylesTable();
        this.sheets = (SheetIterator) xssfReader.getSheetsData();
        this.tables = new ReadOnlySharedStringsTable(this.opk);
        this.rowReader = rowHandler;
        this.handler = new PoiSheetHandler(rowReader, isFirstRowSkip);
    }

    public boolean hasNext() {
        return sheets.hasNext();
    }

    /**
     * sheet 읽기
     * 
     * @throws Exception
     */
    public void read() throws Exception {

        InputStream stream = null;

        try {
            stream = sheets.next();

            log.debug("sheetName : {} ", sheets.getSheetName());
            
            processSheet(handler, stream);

        } finally {
            ResourceUtil.close(stream);
        }
    }

    /**
     * 전체 sheet 읽기
     * 
     * @throws Exception
     */
    public void allSheetRead() throws Exception {
        while (hasNext()) read();
    }

    /**
     * sheet 읽기
     * 
     * @param sheetInputStream
     * @throws IOException
     * @throws SAXException
     */
    private void processSheet(SheetContentsHandler handler, InputStream sheetInputStream)
            throws IOException, SAXException {

        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);

        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, null, tables, handler, formatter, false));
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    public void close() throws IOException {
        ResourceUtil.close(opk);
    }
}
