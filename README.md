# poi-wrapper
poi wrapper 

apache poi 를 좀더 편하게 사용하기 위한 util

> 사용예제
<pre><code>
    @Test
    public void 엑셀파일생성() throws Exception {
        PoiWriter writer = PoiWriter.getBuilder().setXSSFWorkbook()
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
</pre></code>
