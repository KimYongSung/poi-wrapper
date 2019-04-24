package com.kys.poi.write;

/**
 * 엑셀 함수 관리
 * 
 * @author kys0213
 * @date 2019. 2. 14.
 */
public class ExcelFunction {

    private static final String FUNCTION_SUM = "SUM";

    /**
     * excel SUM 함수 리턴 <br>
     * <br>
     * 
     * <pre>
     * ex) sum('A', 1, 4 ) = SUM(A1:A4)
     * </pre>
     * 
     * @param column   엑셀 column 순서 ( A ~ )
     * @param startRow 시작할 row 위치
     * @param endRow   종료할 row 위치
     */
    public static String sum(String column, int startRow, int endRow) {
        return make(FUNCTION_SUM, column, startRow, endRow);
    }

    /**
     * excel SUM 함수 리턴 <br>
     * <br>
     * 
     * <pre>
     * ex) sum('A1', 'A2', 'B3' ) = SUM(A1,A2,B3)
     * </pre>
     * 
     * @param columns 함수 적용할 column 위치
     */
    public static String sum(String... columns) {
        return make(FUNCTION_SUM, columns);
    }

    /**
     * excel SUM 함수 리턴 <br>
     * <br>
     * 
     * <pre>
     * ex) sum(1, 1, 4 ) = SUM(A1:A4)
     * </pre>
     * 
     * @param column   엑셀 column 순서 ( A ~ )
     * @param startRow 시작할 row 위치
     * @param endRow   종료할 row 위치
     */
    public static String sum(int column, int startRow, int endRow) {
        return make(FUNCTION_SUM, column, startRow, endRow);
    }

    /**
     * excel의 row 범위 지정 함수 생성 <br>
     * <br>
     * 
     * <pre>
     * ex) make('SUM','A', 1, 4 ) = SUM(A1:A4)
     * </pre>
     * 
     * @param function 엑셀 함수 명
     * @param column   엑셀 column 순서 ( A ~ )
     * @param startRow 시작할 row 위치
     * @param endRow   종료할 row 위치
     * @return
     */
    private static String make(String function, String column, int startRow, int endRow) {
        return new StringBuilder(function).append("(").append(column).append(startRow).append(":").append(column)
                .append(endRow).append(")").toString();
    }

    /**
     * excel의 row 범위 지정 함수 생성 <br>
     * <br>
     * 
     * <pre>
     * ex) make('SUM',1, 1, 4 ) = SUM(A1:A4)
     * </pre>
     * 
     * @param function 엑셀 함수 명
     * @param column   엑셀 column 순서 ( A ~ )
     * @param startRow 시작할 row 위치
     * @param endRow   종료할 row 위치
     * @return
     */
    private static String make(String function, int column, int startRow, int endRow) {
        return make(function, columLookup(column), startRow, endRow);
    }

    /**
     * excel의 cell 지정 함수 생성 <br>
     * <br>
     * 
     * <pre>
     * ex) make('SUM','A1', 'A2', 'B3' ) = SUM(A1,A2,B3)
     * </pre>
     * 
     * @param function 엑셀 함수 명
     * @param columns  함수 적용할 column 위치
     * @return
     */
    private static String make(String function, String... columns) {
        StringBuilder strBuilder = new StringBuilder(function).append("(");

        for (String column : columns) {
            strBuilder.append(column).append(",");
        }

        strBuilder.delete(strBuilder.length() - 1, strBuilder.length());
        strBuilder.append(")");

        return strBuilder.toString();
    }

    /**
     * 엑셀 컬럼 위치 변환
     * 
     * @param columnIndex 컬럼 위치
     * @return 컬럼 위치에 해당하는 알파벳
     */
    private static String columLookup(int columnIndex) {

        if (columnIndex <= 26) {
            return String.valueOf((char) (columnIndex + 64));
        }

        StringBuilder strBuilder = new StringBuilder();

        while ((columnIndex / 26) != 0) {
            strBuilder.append((char) (columnIndex % 26 + 64));
            columnIndex /= 26;
        }

        strBuilder.append((char) (columnIndex + 64));

        return strBuilder.reverse().toString();
    }
}
