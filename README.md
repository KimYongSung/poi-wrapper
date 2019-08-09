# poi-wrapper

## 1. 설명
* apache poi 를 좀더 편하게 사용하기 위한 wrapper class 입니다.
* servlet에서 사용시에는 FileOutPutStream이 아닌 ServletResponse에 OutputStream 사용하여 처리 가능 합니다.

## 2. 의존성

* poi 3.17 기준으로 작성되었으며, java6 이상에서 사용 가능합니다.

```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>3.17</version>
</dependency>

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>3.17</version>
</dependency>

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-scratchpad</artifactId>
    <version>3.17</version>
</dependency>

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml-schemas</artifactId>
    <version>3.17</version>
</dependency>
```
### 3. 사용예제

* XSSFWorkbook 사용예제
```java

@Test
public void 엑셀파일생성() throws Exception {
    PoiWriter writer = PoiWriter.getBuilder()
                                .setXSSFWorkbook()
                                .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\excel_test.xlsx"))
                                .build();

    writer.createSheet("testSheet");

    for (int index = 0; index < 100; index++) {
        writer.addCell(String.valueOf(index))
              .addCell(index)
              .nextRow();
    }

    writer.write()
          .close();
}

```

* SXSSFWorkbook 사용예제

    - SXSSFWorkbook의 경우 스트리밍 방식으로 엑셀을 출력하며, 대용량 엑셀 파일 생성시 사용에 적합합니다.

```java
@Test
public void 스트링엑셀파일생성() throws Exception {
    PoiWriter writer = PoiWriter.getBuilder()
                                .setSXSSFWorkbook(100)
                                .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\streming_excel_test.xlsx"))
                                .build();

    writer.createSheet("testSheet");

    for (int index = 0; index < 2000000; index++) {
        writer.addCell(String.valueOf(index))
              .addCell(index)
              .nextRow();
    }

    writer.write()
          .close();
}
```

* HSSFWorkBook 사용예제
    - .xlsx가 아닌.xls 확장자의 엑셀파일 생성시 사용합니다.

```java
@Test
public void 구버전엑셀파일생성() throws Exception {
    PoiWriter writer = PoiWriter.getBuilder()
                                .setHSSFWorkBook()
                                .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\old_excel_test.xls"))
                                .build();

    writer.createSheet("testSheet");

    for (int index = 0; index < 1000; index++) {
        writer.addCell(String.valueOf(index))
              .addCell(index)
              .nextRow();
    }

    writer.write()
          .close();
}
```

* 사용자 지정 셀 스타일 객체 생성 및 지정

    - interface CellStyleBuilder 구현

```java

    // 스타일 생성울 위한 builder interface
    @FunctionalInterface
    public interface CellStyleBuilder {
        public CellStyle build(Workbook workBook);
    }
    
    // 사용자 지정 스타일 생성 및 사용 
    @Test
    public void 사용자지정스타일생성() throws Exception {
        
        PoiWriter writer = PoiWriter.getBuilder()
                                    .setHSSFWorkBook()
                                    .setStringStyleBuilder((workbook) ->{
                                        
                                        CellStyle cellStyle = workbook.createCellStyle();
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderTop(BorderStyle.THIN);
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                    
                    
                                        Font font = workbook.createFont();
                                        font.setFontName("굴림");
                                        font.setFontHeight((short) (10 * 20));
                                        font.setBold(true);
                    
                                        cellStyle.setFont(font);
                                        
                                        return cellStyle;
                                    })
                                    .setOutputStream(new FileOutputStream("D:\\공부\\엑셀테스트\\style_test.xls"))
                                    .build();

        CellStyle numberStyle = writer.styleBuild((workbook) ->{
            
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
            
            Font font = workbook.createFont();
            font.setFontName("Tahoma");
            font.setFontHeight((short) (10 * 20));
            font.setBold(true);
            
            cellStyle.setFont(font);
            
            return cellStyle;
        });
        
        writer.createSheet("testSheet");
        
        for (int index = 0; index < 10; index++) {
            writer.addCell(String.valueOf(index))
                  .addCell(index, numberStyle)
                  .nextRow();
        }
        
        writer.write()
              .close();
            
    }
```
