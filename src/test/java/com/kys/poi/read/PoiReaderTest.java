package com.kys.poi.read;

import com.kys.poi.mapping.CellInfo;
import com.kys.poi.mapping.CellInfos;
import com.kys.util.ResourceUtil;
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

        CellInfos cellinfos = cellInfos.add(CellInfo.builder()
                                                    .fieldName("test1")
                                                    .build())
                                        .add(CellInfo.builder()
                                                     .fieldName("test2")
                                                     .build());

        PoiReader reader = PoiReader.builder(TestVO.class)
                                    .url(ResourceUtil.getURL(getFile().getAbsolutePath() + "/streming_excel_test.xlsx"))
                                    .cellNames(cellinfos)
                                    .singletonObject()  // TestVO 를 singleton 으로 사용함
                                    .firstRowSkip()     // 첫번째 row는 skip 함
                                    .poiRowHandler(rowHandler)
                                    .build();

        reader.read();
        reader.close();
    }

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

        public String getTest1() {
            return test1;
        }

        public void setTest1(String test1) {
            this.test1 = test1;
        }

        public String getTest2() {
            return test2;
        }

        public void setTest2(String test2) {
            this.test2 = test2;
        }

        public String getTest3() {
            return test3;
        }

        public void setTest3(String test3) {
            this.test3 = test3;
        }
    }
}
