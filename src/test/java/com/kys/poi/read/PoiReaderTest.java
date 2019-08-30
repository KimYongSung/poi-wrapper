package com.kys.poi.read;

import com.kys.poi.mapping.CellInfo;
import com.kys.poi.mapping.CellInfos;
import com.kys.util.ResourceUtil;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.io.File;

public class PoiReaderTest {

    private File getFile() {
        return new File("excel");
    }

    @Test
    public void 엑셀파일읽기테스트() throws Exception {

        File file = getFile();

        CellInfos cellInfos = CellInfos.newInstance();

        PoiRowHandler<TestVO> rowHandler = new PoiRowHandler<TestVO>() {
            public void handler(TestVO obj) {
                System.out.println(obj.toString());
            }
        };

        PoiReader reader = PoiReader.builder(TestVO.class)
                                    .url(ResourceUtil.getURL(getFile().getAbsolutePath() + "/streming_excel_test.xlsx"))
                                    .cellNames(cellInfos.add(CellInfo.builder()
                                                                    .fieldName("test1")
                                                                    .build())
                                                        .add(CellInfo.builder()
                                                                    .fieldName("test2")
                                                                    .build()))
                                    .singletonObject()
                                    .firstRowSkip()
                                    .poiRowHandler(rowHandler)
                                    .build();

        reader.read();
        reader.close();
    }

    @Data
    public static class TestVO {

        private String test1;

        private String test2;

        private String test3;

        @Override
        public String toString() {
            return "TestVO{" +
                    "test1='" + test1 + '\'' +
                    ", test2='" + test2 + '\'' +
                    ", test3='" + test3 + '\'' +
                    '}';
        }
    }
}
