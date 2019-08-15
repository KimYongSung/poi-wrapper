package com.kys.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 빈 문자열 <code>""</code>.
     */
    public static final String EMPTY = "";

    /**
     * 빈 배열 <code>[]</code>.
     */
    public static final String[] EMPTY_ARR = new String[0];

    /**
     * null 일 경우 "" 으로 변경
     * 
     * @param str
     * @return
     */
    public static String nullToEmpty(String str) {
        return nullToStr(str, EMPTY).trim();
    }

    /**
     * null 일 경우 "" 으로 변경
     * 
     * @param str
     * @return
     */
    public static String nullToEmpty(Object obj) {
        return (obj == null) ? EMPTY : obj.toString();
    }

    /**
     * null 일 경우 replace 문자열로 변경
     * 
     * @param str
     * @param replace
     * @return
     */
    public static String nullToStr(String str, String replace) {
        if (str == null || str.length() == 0)
            return replace;
        else
            return str;
    }

    /**
     * null 일 경우 replace 문자열로 변경
     * 
     * @param str
     * @param replace
     * @return
     */
    public static String nullToStr(Object str, String nullStr) {
        if (ObjectUtils.isNull(str))
            return nullStr;
        else
            return str.toString();
    }

    /**
     * String 문자열 구분자로 잘라서 배열로 변경
     * 
     * @param str
     * @param delimiter
     * @return
     */
    public static String[] splite(String str, String delimiter) {

        if (isNullorEmpty(str))
            return EMPTY_ARR;

        StringTokenizer token = new StringTokenizer(str, delimiter);
        int tokenCount = token.countTokens();
        String[] arr = new String[tokenCount];

        for (int index = 0; index < tokenCount; index++) {
            arr[index] = token.nextToken();
        }
        return arr;
    }

    /**
     * 정수로 변경. <br>
     * 정수형이 아니거나 null 일 경우 defaultValue로 변환
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static int toInt(String value, int defaultValue) {
        if (isNullorEmpty(value) || !isNumber(value))
            return defaultValue;
        return Integer.parseInt(value);
    }

    /**
     * 정수로 변경. <br>
     * 정수형이 아니거나 null 일 경우 -1로 변환
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static int toInt(String value) {
        return toInt(value, -1);
    }

    public static boolean isNullorEmpty(Object value) {
        return (value == null) ? true : false;
    }

    /**
     * <p>
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * </p>
     * 
     * <pre>
     *  StringUtil.isEmpty(null)      = true
     *  StringUtil.isEmpty("")        = true
     *  StringUtil.isEmpty(" ")       = false
     *  StringUtil.isEmpty("bob")     = false
     *  StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * 
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
     */
    public static boolean isNullorEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * String이 비었거나("") 혹은 null이 아닌지 검증한다.
     * </p>
     * 
     * <pre>
     *  StringUtil.isEmpty(null)      = false
     *  StringUtil.isEmpty("")        = false
     *  StringUtil.isEmpty(" ")       = true
     *  StringUtil.isEmpty("bob")     = true
     *  StringUtil.isEmpty("  bob  ") = true
     * </pre>
     * 
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null이 아닌 경우
     */
    public static boolean isNotNullorEmpty(String str) {
        return !isNullorEmpty(str);
    }

    public static String trim(String value) {
        return (value == null) ? "" : value.trim();
    }

    public static String rPad(String str, int size, String fStr) {
        byte[] b = str.getBytes();
        int len = b.length;
        int tmp = size - len;

        StringBuilder strBuilder = new StringBuilder(str);
        for (int i = 0; i < tmp; i++) {
            strBuilder.append(fStr);
        }
        return strBuilder.toString();
    }

    public static String lPad(String str, int size, String fStr) {
        byte[] b = str.getBytes();
        int len = b.length;
        int tmp = size - len;

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < tmp; i++) {
            strBuilder.append(fStr);
        }
        strBuilder.append(str);
        return strBuilder.toString();
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
     * 
     * @param source  원본 문자열 배열
     * @param output  더할문자열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, String output, int slength) {
        String returnVal = null;
        if (source != null) {
            if (source.length() > slength) {
                returnVal = source.substring(0, slength) + output;
            } else
                returnVal = source;
        }
        return returnVal;
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
     * 
     * @param source  원본 문자열 배열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, int slength) {
        String result = null;
        if (source != null) {
            if (source.length() > slength) {
                result = source.substring(0, slength);
            } else
                result = source;
        }
        return result;
    }

    /**
     * <p>
     * 기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.remove(null, *)       = null
     * StringUtil.remove("", *)         = ""
     * StringUtil.remove("queued", 'u') = "qeed"
     * StringUtil.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str    입력받는 기준 문자열
     * @param remove 입력받는 문자열에서 제거할 대상 문자열
     * @return 제거대상 문자열이 제거된 입력문자열. 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String remove(String str, char remove) {
        if (isNullorEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * <p>
     * 문자열 내부의 콤마 character(,)를 모두 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.removeCommaChar(null)       = null
     * StringUtil.removeCommaChar("")         = ""
     * StringUtil.removeCommaChar("asdfg,qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str 입력받는 기준 문자열
     * @return " , "가 제거된 입력문자열 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeCommaChar(String str) {
        return remove(str, ',');
    }

    /**
     * <p>
     * 문자열 내부의 마이너스 character(-)를 모두 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.removeMinusChar(null)       = null
     * StringUtil.removeMinusChar("")         = ""
     * StringUtil.removeMinusChar("a-sdfg-qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str 입력받는 기준 문자열
     * @return " - "가 제거된 입력문자열 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }

    /**
     * <p>
     * <code>str</code> 중 <code>searchStr</code>의 시작(index) 위치를 반환.
     * </p>
     *
     * <p>
     * 입력값 중 <code>null</code>이 있을 경우 <code>-1</code>을 반환.
     * </p>
     *
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str       검색 문자열
     * @param searchStr 검색 대상문자열
     * @return 검색 문자열 중 검색 대상문자열이 있는 시작 위치 검색대상 문자열이 없거나 null인 경우 -1
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }

    /**
     * 전화번호 노출 포멧 변경
     * 
     * @param telNum
     * @return
     */
    public static String telNumberConverter(String telNum) {
        if (StringUtil.isNullorEmpty(telNum)) {
            return "";
        } else if (telNum.length() == 8) {
            return telNum.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        } else if (telNum.length() == 12) {
            return telNum.replaceFirst("^([0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        } else {
            return telNum.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
        }
    }

    /**
     * String array to String
     * 
     * @param arr
     * @param splitStr
     * @return
     */
    public static String arrayToString(String[] arr, String splitStr) {

        if (ObjectUtils.isNull(arr)) {
            return EMPTY;
        }

        StringBuilder strBuilder = new StringBuilder();

        for (String str : arr) {
            strBuilder.append(str).append(splitStr);
        }

        strBuilder.delete(strBuilder.length() - 1, strBuilder.length());

        return strBuilder.toString();
    }

    /**
     * 문자열에서 해당 정규식에 일치하는 문자열 찾기
     * 
     * @param str
     * @param regex
     * @return
     */
    public static List<String> findAll(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        List<String> list = new ArrayList<String>();

        while (matcher.find())
            list.add(matcher.group());

        return list;
    }

    /**
     * 문자열에서 해당 정규식에 일치하는 문자열 추출
     * 
     * @param str
     * @param regex
     * @return
     */
    public static String extractText(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * 정수 여부 확인
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isNullorEmpty(str))
            return false;
        return Pattern.matches("[0-9]+", str);
    }
}
