package com.kys.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * java 리소스 처리 util
 *
 * @author kys0213
 * @since 2018. 12. 31.
 */
public class ResourceUtil {

    private static final String REPLACEMENT = "";

    /** stream 에서 읽어올 byte 단위 기본값 */
    private static final int DEFAULT_READ_LENGTH = 128;

    /** xml file 확장자 */
    private static final String XML_EXTENSION = ".xml";

    /** json file 확장자 */
    private static final String JSON_EXTENSION = ".json";

    /** Pseudo URL prefix for loading from the class path: "classpath:" */
    private static final String CLASSPATH_URL_PREFIX = "classpath:";

    /** URL prefix for loading from the file system: "file:" */
    private static final String FILE_URL_PREFIX = "file:";

    /**
     * 클래스 패스 여부
     * 
     * @param path
     * @return
     */
    private static boolean isClassPath(String path) {
        return StringUtil.isNullorEmpty(path) ? false : path.startsWith(CLASSPATH_URL_PREFIX);
    }

    /**
     * 파일 여부
     * 
     * @param path
     * @return
     */
    private static boolean isFile(String path) {
        return StringUtil.isNullorEmpty(path) ? false : path.startsWith(FILE_URL_PREFIX);
    }

    /**
     * classpath에 존재하는 리소스를 inputStream으로 변환
     * 
     * @param path 리소스 경로
     * @return 변환된 inputStream 정보
     */
    private static InputStream classPathToStream(String path) {
        String realPath = path.replace(CLASSPATH_URL_PREFIX, REPLACEMENT);
        InputStream is = ResourceUtil.class.getClassLoader().getResourceAsStream(realPath);

        if (ObjectUtils.isNull(is)) {
            throw new IllegalArgumentException("resource not found!! - " + path);
        }
        return is;
    }

    /**
     * File을 Stream으로 변환
     * 
     * @param path file 위치
     * @return file을 읽어온 inputStream 객체
     * @throws RuntimeException file이 존재하지 않을 경우 발생
     */
    private static InputStream fileToStream(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * classpath에 존재하는 리소스를 URL로 변환
     * 
     * @param path
     * @return URL 정보
     */
    private static URL classPathToUrl(String path) {
        path = path.substring(CLASSPATH_URL_PREFIX.length());

        URL url = ResourceUtil.class.getClassLoader().getResource(path);
        if (ObjectUtils.isNull(url)) {
            throw new IllegalArgumentException("resource not found!! - " + path);
        }

        return url;
    }

    /**
     * File을 URL 객체로 변경
     * 
     * @param file
     * @return
     * @throws RuntimeException 처리 중 예외 발생시
     */
    private static URL fileToUrl(String path) {
        try {
            return new File(path).toURI().toURL();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 리소스 inputStream으로 변환
     * 
     * @param path 리소스 경로
     * @return
     * @exception NullPointerException     path 값이 null 일 경우 발생
     * @exception IllegalArgumentException classpath에 resource가 없을 경우 발생
     * @exception RuntimeException         file이 없을 경우 발생
     */
    public static InputStream getStream(String path) {

        if (StringUtil.isNullorEmpty(path)) {
            throw new NullPointerException("path value is null");
        }

        if (isClassPath(path)) {
            return classPathToStream(path);
        } else if (isFile(path)) {
            return fileToStream(path.replace(FILE_URL_PREFIX, REPLACEMENT));
        } else {
            return fileToStream(path);
        }
    }

    /**
     * 특정 Stream을 Reader로 변환
     * 
     * @param is inputStream 객체
     * @return Reader로 변환
     * @throws IllegalArgumentException InputStream이 null 인 경우
     */
    public static Reader streamToReader(InputStream is) {
        if (ObjectUtils.isNull(is)) {
            throw new IllegalArgumentException("InputStream is null");
        }
        return new InputStreamReader(is);
    }

    /**
     * 특정 경로에 있는 파일을 Reader 객체로 가져오기
     * 
     * @param path
     * @return
     */
    public static Reader getReader(String path) {
        return streamToReader(getStream(path));
    }

    /**
     * 리소스를 URL로 변환
     * 
     * @param path
     * @return
     */
    public static URL getURL(String path) {

        if (StringUtil.isNullorEmpty(path)) {
            throw new NullPointerException("path value is null");
        }

        if (isClassPath(path)) {
            return classPathToUrl(path);
        } else if (isFile(path)) {
            return fileToUrl(path.substring(FILE_URL_PREFIX.length()));
        } else {
            return fileToUrl(path);
        }
    }

    /**
     * 파일 확장자가 .xml 인지 확인
     * 
     * @param path
     * @return
     */
    public static boolean isXML(String path) {
        return StringUtil.isNullorEmpty(path) ? false : path.toLowerCase().endsWith(XML_EXTENSION);
    }

    /**
     * 파일 확장자가 .json 인지 확인
     * 
     * @param path
     * @return
     */
    public static boolean isJSON(String path) {
        return StringUtil.isNullorEmpty(path) ? false : path.toLowerCase().endsWith(JSON_EXTENSION);
    }

    /**
     * 리소스 String로 변환
     * 
     * @param path    리소스 경로
     * @param charSet 파일 인코딩 정보
     * @return
     * @throws IOException stream에서 읽는 중 예외 발생시
     */
    public static String streamToString(String path, Charset charSet) {
        return streamToString(getStream(path), charSet, DEFAULT_READ_LENGTH);
    }

    /**
     * 리소스 String로 변환
     * 
     * @param is      inputStream 객체
     * @param charSet 인코딩 정보
     * @param size    stream에서 읽을 길이 단위 ( ex. 400 일 경우 inputStream에서 400 바이트씩 읽어옴 )
     * @return stream에서 읽은 문자열
     * @throws IOException              stream에서 읽는 중 예외 발생시
     * @throws IllegalArgumentException 메소드의 파라미터가 유효하지 않을 경우
     */
    public static String streamToString(InputStream is, Charset charSet, int size) {

        if (ObjectUtils.isNull(is)) {
            throw new IllegalArgumentException("inputStream is null.");
        }else if (ObjectUtils.isNull(charSet)) {
            throw new IllegalArgumentException("charSet is null.");
        }else if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: " + size);
        }

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(is, charSet);

            char[] temp = new char[size];
            int readLength = 0;

            StringBuilder strBuilder = new StringBuilder(size);

            while ((readLength = reader.read(temp)) > -1)
                strBuilder.append(temp, 0, readLength);

            return strBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(is, reader);
        }
    }

    /**
     * 리소스 byte로 변환
     * 
     * @param is inputStream 객체
     * @return stream에서 읽은 byte 배열
     * @throws IOException inputStream 처리 중 예외 발생시
     */
    public static byte[] streamToByte(InputStream is) {
        return streamToByte(is, DEFAULT_READ_LENGTH);
    }

    /**
     * 리소스 byte로 변환
     * 
     * @param is   inputStream 객체
     * @param size stream에서 읽을 길이 단위 ( ex. 400 일 경우 inputStream에서 400 바이트씩 읽어옴 )
     * @return stream에서 읽은 byte 배열
     * @throws IOException              stream에서 읽는 중 예외 발생시
     * @throws IllegalArgumentException size 가 음수일 경우 or InputStream이 null 일 경우
     */
    public static byte[] streamToByte(InputStream is, int size) {

        if (ObjectUtils.isNull(is)) {
            throw new IllegalArgumentException("inputStream is null.");
        }

        FastByteArrayOutputStream buffer = null;
        try {
            buffer = new FastByteArrayOutputStream(size);
            int read = 0;
            byte[] temp = new byte[size];
            while ((read = is.read(temp, 0, size)) > -1) {
                buffer.write(temp, 0, read);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(is, buffer);
        }
    }

    /**
     * 리소스 byte로 변환
     * 
     * @param path    리소스 경로
     * @param charSet 파일 인코딩 정보
     * @return stream에서 읽은 byte 배열
     * @throws IOException inputStream 처리 중 예외 발생시
     */
    public static byte[] streamToByte(String path) {
        return streamToByte(getStream(path), DEFAULT_READ_LENGTH);
    }

    /**
     * properties파일을 읽어서 Properties 객체 생성
     * 
     * @param path 경로
     * @return Properties 객체
     * @throws IOException properties 파일 처리 중 예외 발생시
     */
    public static Properties loadProp(String path) {
        if (StringUtil.isNullorEmpty(path)) {
            throw new IllegalArgumentException("path is null.");
        }
        return streamToProp(isXML(path), getStream(path));
    }

    /**
     * Stream을 Properties로 변환
     * 
     * @param isXML properties 파일이 확장자가 .xml 인지 확인
     * @param is    properties 파일 stream
     * @return Properties 객체
     * @throws IllegalArgumentException         InputStream 이 null 일 경우
     * @throws IOException                      properties 파일 로드 중 예외 발생시
     * @throws InvalidPropertiesFormatException xml properties 파일의 포멧이 잘못된 경우
     */
    public static Properties streamToProp(boolean isXML, InputStream is) {

        if (is == null) {
            throw new IllegalArgumentException("inputStream is null.");
        }

        try {
            Properties prop = new Properties();

            if (isXML)
                prop.loadFromXML(is);
            else
                prop.load(is);

            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(is);
        }
    }

    /**
     * {@link Closeable} 구현체들의 close() 메소드 호출.
     * 
     * @param closeable {@link Closeable} 구현체
     */
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables)
            if (ObjectUtils.isNotNull(closeable))
                try {
                    closeable.close();
                } catch (IOException ignore) {
                }

    }

    /**
     * 기존 ByteArrayOutputStream에 write와 toByteArray 의 성능 개선
     * 
     * <li>write 메소드의 synchronized 제거</li>
     * <li>toByteArray 메소드의 synchronized 제거 및 buf 복제 로직 제거</li> <br>
     * 
     * @author kys0213
     * @since 2019. 1. 22.
     */
    private static class FastByteArrayOutputStream extends ByteArrayOutputStream {

        public FastByteArrayOutputStream(int size) {
            super(size);
        }

        @Override
        public void write(byte b[], int off, int len) {

            if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }

            int newcount = count + len;

            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }

            System.arraycopy(b, off, buf, count, len);

            count = newcount;
        }

        @Override
        public byte toByteArray()[] {
            return buf;
        }
    }
}
