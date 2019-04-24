package com.kys.poi.read;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

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

import com.kys.util.ResourceUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * POI Straming 엑셀 리더 <br>
 * <br>
 * <strong>.xlsx 확장자만 가능 합니다.</strong> <br>
 * 
 * @author kys0213
 * @date 2019. 3. 25.
 */
@Slf4j
public class PoiReader implements Closeable {

    private final OPCPackage opk;

    private final ReadOnlySharedStringsTable tables;

    private final StylesTable styles;

    private final SheetIterator sheets;

    private final XSSFReader xssfReader;

    /**
     * PoiReader 생성
     * 
     * @param filePath
     * @throws Exception
     */
    public PoiReader(String filePath) throws Exception {

        URL excelUrl = ResourceUtil.getURL(filePath);

        this.opk = OPCPackage.open(excelUrl.getPath(), PackageAccess.READ);
        this.xssfReader = new XSSFReader(this.opk);
        this.tables = new ReadOnlySharedStringsTable(this.opk);
        this.styles = xssfReader.getStylesTable();
        this.sheets = (SheetIterator) xssfReader.getSheetsData();
    }

    /**
     * 다음 sheet 가 있는지 확인
     * 
     * @return
     */
    public boolean hasNextSheet() {
        return sheets.hasNext();
    }

    /**
     * sheet 읽기
     * 
     * @param handler
     * @throws Exception
     */
    public void read(PoiRowHandler rowHandler) throws Exception {

        InputStream stream = null;

        try {
            stream = sheets.next();

            log.debug("sheetName : {} ", sheets.getSheetName());
            processSheet(new PoiSheetHandler(rowHandler), stream);

        } finally {
            ResourceUtil.close(stream);
        }
    }

    /**
     * 전체 sheet 읽기
     * 
     * @param handler
     * @throws Exception
     */
    public void allSheetRead(PoiRowHandler rowHandler) throws Exception {
        while (hasNextSheet())
            read(rowHandler);
    }

    /**
     * sheet 읽기
     * 
     * @param sheetHandler
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

    @Override
    public void close() throws IOException {
        ResourceUtil.close(opk);
    }
}
