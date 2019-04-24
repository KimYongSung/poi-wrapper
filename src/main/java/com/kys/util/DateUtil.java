package com.kys.util;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * java1.8 java.time.* 패키지 기반의 DateUtil
 * 
 * @author kys0213
 * @since 2018. 3. 9.
 */
public class DateUtil {

    /** yyyyMMddHHmmss Format */
    public static final DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /** yyyyMMddHHmmssSSS Format */
    public static final DateTimeFormatter YYYYMMDDHHMMSS_SSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    /** yyyyMMdd Format */
    public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /** HHmmss Format */
    public static final DateTimeFormatter HHMMSS = DateTimeFormatter.ofPattern("HHmmss");

    public static Long getTimeStampByLong() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }

    /**
     * 현재 시간 TimsStamp 정보
     * 
     * @return
     */
    private static LocalDateTime timeStamp() {
        return new Timestamp(System.currentTimeMillis()).toLocalDateTime();
    }

    /**
     * 특정 시간 문자열을 {@link LocalDateTime} 인스턴스로 변환
     * 
     * @param date
     * @return
     */
    private static LocalDateTime getLocalDateTime(String date) {
        return LocalDateTime.parse(date, YYYYMMDDHHMMSS);
    }

    /**
     * 특정 시간 문자열을 {@link LocalDate} 인스턴스로 변환
     * 
     * @param date
     * @return
     */
    private static LocalDate getLocalDate(String date) {
        if (date.length() > 8)
            date = date.substring(0, 8);
        return LocalDate.parse(date, YYYYMMDD);
    }

    /**
     * 현재 년,월,일
     * 
     * @return yyyyMMdd 형식
     */
    public static String getCurrentDay() {
        return LocalDate.now().format(YYYYMMDD);
    }

    /**
     * 현재 년,월,일
     * 
     * @return 사용자 지정 형식
     */
    public static String getCurrentDay(DateTimeFormatter format) {
        return LocalDate.now().format(format);
    }

    /**
     * 현재 년,월,일
     * 
     * @return 사용자 지정 형식
     */
    public static String getCurrentDay(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 현재 시,분,초
     * 
     * @return HHmmss 형식
     */
    public static String getCurrentTime() {
        return LocalTime.now().format(HHMMSS);
    }

    /**
     * 현재 시,분,초
     * 
     * @return 사용자 지정 형식
     */
    public static String getCurrentTime(DateTimeFormatter format) {
        return LocalTime.now().format(format);
    }

    /**
     * 현재 시,분,초
     * 
     * @return 사용자 지정 형식
     */
    public static String getCurrentTime(String format) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 현재 시간
     * 
     * @return yyyyMMddHHmmss 형식
     */
    public static String getCurrent() {
        return LocalDateTime.now().format(YYYYMMDDHHMMSS);
    }

    /**
     * 현재 시간 리턴
     * 
     * @return 사용자 지정 형식
     */
    public static String getCurrent(DateTimeFormatter format) {
        return LocalDateTime.now().format(format);
    }

    /**
     * 현재 시간 리턴
     * 
     * @return 사용자 지정 형식
     */
    public static String getCurrent(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 현재 TimeStamp
     * 
     * @return yyyyMMddHHmmssSSS 형식
     */
    public static String getTimeStamp() {
        return timeStamp().format(YYYYMMDDHHMMSS_SSS);
    }

    /**
     * 현재 TimeStamp
     * 
     * @param format
     * @return 사용자 지정 형식
     */
    public static String getTimeStamp(String format) {
        return timeStamp().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 현재 TimeStamp
     * 
     * @param format
     * @return 사용자 지정 형식
     */
    public static String getTimeStamp(DateTimeFormatter format) {
        return timeStamp().format(format);
    }

    /**
     * '일자' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @return 24시간 이상 차이가 발생하지 않을 경우 0
     */
    public static long daysDiff(String fromDate, String toDate) {
        return timeDiff(fromDate, toDate, ChronoUnit.DAYS);
    }

    /**
     * '년도' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @return 365일 이상 차이가 발생하지 않을 경우 0
     */
    public static long yearsDiff(String fromDate, String toDate) {
        return timeDiff(fromDate, toDate, ChronoUnit.YEARS);
    }

    /**
     * '년도' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @return 365일 이상 차이가 발생하지 않을 경우 0
     */
    public static long monthDiff(String fromDate, String toDate) {
        return timeDiff(fromDate, toDate, ChronoUnit.MONTHS);
    }

    /**
     * '시간' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @return 60분 이상의 차이가 발생하지 않을 경우 0
     */
    public static long hoursDiff(String fromDate, String toDate) {
        return timeDiff(fromDate, toDate, ChronoUnit.HOURS);
    }

    /**
     * 현재 '시간' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @return 60분 이상의 차이가 발생하지 않을 경우 0
     */
    public static long hoursDiff(String date) {
        return timeDiff(getCurrent(), date, ChronoUnit.HOURS);
    }

    /**
     * '분' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @return 60분 이상의 차이가 발생하지 않을 경우 0
     */
    public static long minutesDiff(String fromDate, String toDate) {
        return timeDiff(fromDate, toDate, ChronoUnit.MINUTES);
    }

    /**
     * 현재 시간 '분' 비교
     * 
     * @param date 'YYYYMMDDHHMMSS' 형식의 날짜
     * @return 60분 이상의 차이가 발생하지 않을 경우 0
     */
    public static long minutesDiff(String date) {
        return timeDiff(getCurrent(), date, ChronoUnit.MINUTES);
    }

    /**
     * '초' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @return from 과 to 의 차이
     */
    public static long secondsDiff(String fromDate, String toDate) {
        return timeDiff(fromDate, toDate, ChronoUnit.SECONDS);
    }

    /**
     * 현재 시간 '초' 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @return from 과 to 의 차이
     */
    public static long secondsDiff(String date) {
        return timeDiff(getCurrent(), date, ChronoUnit.SECONDS);
    }

    /**
     * 시간 비교
     * 
     * @param fromDate 'YYYYMMDDHHMMSS' 형식의 날짜
     * @param toDate   'YYYYMMDDHHMMSS' 형식의 날짜
     * @param unit     {@link ChronoUnit} 비교할 형식 지정
     * @return fromDate와 toDate의 날짜 비교
     */
    public static long timeDiff(String fromDate, String toDate, ChronoUnit unit) {
        return unit.between(getLocalDateTime(fromDate), getLocalDateTime(fromDate));
    }

    /**
     * 해당 날짜가 과거인지 판단
     * 
     * @param date 'YYYYMMDDHHMMSS' 형식의 날짜
     * @return
     */
    public static boolean isPast(String date) {
        return isPast(getLocalDateTime(date), LocalDateTime.now());
    }

    /**
     * 해당 날짜가 미래인지 판단.
     * 
     * @param date 'YYYYMMDDHHMMSS' 형식의 날짜
     * @return
     */
    public static boolean isFuture(String date) {
        return isFuture(getLocalDateTime(date), LocalDateTime.now());
    }

    /**
     * 해당 날짜가 오늘인지 판단.
     * 
     * @param date 'YYYYMMDDHHMMSS' 형식의 날짜
     * @return
     */
    public static boolean isEquals(String date) {
        return isEquals(getLocalDateTime(date), LocalDateTime.now());
    }

    /**
     * 해당 날짜가 과거인지 판단
     * 
     * @param date 'YYYYMMDDHHMMSS' 형식의 날짜
     * @return
     */
    public static boolean isPastOrEquals(String date) {
        LocalDateTime dateTime = getLocalDateTime(date);
        LocalDateTime now = LocalDateTime.now();
        return isPast(dateTime, now) || isEquals(dateTime, now);
    }

    /**
     * 해당 날짜가 미래인지 판단.
     * 
     * @param date 'YYYYMMDDHHMMSS' 형식의 날짜
     * @return
     */
    public static boolean isEqualsOrFuture(String date) {
        LocalDateTime dateTime = getLocalDateTime(date);
        LocalDateTime now = LocalDateTime.now();
        return isFuture(dateTime, now) || isEquals(dateTime, now);
    }

    /**
     * date1이 date2보다 과거인지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isPast(ChronoLocalDateTime<?> date1, ChronoLocalDateTime<?> date2) {
        return date1.isBefore(date2);
    }

    /**
     * date1이 date2보다 미래인지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isFuture(ChronoLocalDateTime<?> date1, ChronoLocalDateTime<?> date2) {
        return date1.isAfter(date2);
    }

    /**
     * date1과 date2이 일치하는지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEquals(ChronoLocalDateTime<?> date1, ChronoLocalDateTime<?> date2) {
        return date1.isEqual(date2);
    }

    /**
     * date1이 date2보다 과거 또는 현재 인지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isPastOrEquals(ChronoLocalDateTime<?> date1, ChronoLocalDateTime<?> date2) {
        return isPast(date1, date2) || isEquals(date1, date2);
    }

    /**
     * date1이 date2보다 일치 또는 미래
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqualsOrFuture(ChronoLocalDateTime<?> date1, ChronoLocalDateTime<?> date2) {
        return isEquals(date1, date2) || isFuture(date1, date2);
    }

    /**
     * date1이 date2보다 과거인지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isPast(ChronoLocalDate date1, ChronoLocalDate date2) {
        return date1.isBefore(date2);
    }

    /**
     * date1이 date2보다 미래인지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isFuture(ChronoLocalDate date1, ChronoLocalDate date2) {
        return date1.isAfter(date2);
    }

    /**
     * date1과 date2이 일치하는지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEquals(ChronoLocalDate date1, ChronoLocalDate date2) {
        return date1.isEqual(date2);
    }

    /**
     * date1이 date2보다 과거 또는 현재 인지 확인
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isPastOrEquals(ChronoLocalDate date1, ChronoLocalDate date2) {
        return isPast(date1, date2) || isEquals(date1, date2);
    }

    /**
     * date1이 date2보다 일치 또는 미래
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqualsOrFuture(ChronoLocalDate date1, ChronoLocalDate date2) {
        return isEquals(date1, date2) || isFuture(date1, date2);
    }

    /**
     * 해당 날짜가 오늘인지 판단.
     * 
     * @param date 'YYYYMMDD' 형식의 날짜
     * @return
     */
    public static boolean isTodayByLocalDate(String date) {
        return getLocalDate(date).isEqual(LocalDate.now());
    }

    /**
     * 현재 시간에서 '년' 더하기
     * 
     * @param year 증가할 숫자
     * @return
     */
    public static String addYear(int year) {
        return addYear(LocalDateTime.now(), year, YYYYMMDDHHMMSS);
    }

    /**
     * '년' 더하기
     * 
     * @param date YYYYMMDDHHMMSS 형식
     * @param year 증가할 숫자
     * @return
     */
    public static String addYear(String date, int year) {
        return addYear(getLocalDateTime(date), year, YYYYMMDDHHMMSS);
    }

    /**
     * '년' 더하기
     * 
     * @param LocalDateTime 날짜
     * @param year          증가할 숫자
     * @param format        날짜 형식
     * @return
     */
    public static String addYear(LocalDateTime date, int year, DateTimeFormatter format) {
        return date.with((temporal) -> temporal.with(MONTH_OF_YEAR, date.getMonthValue())
                   .plus(year * 12, ChronoUnit.MONTHS))
                   .format(format);
    }

    /**
     * 현재시간에서 '월' 더하기
     * 
     * @param month 증가할 숫자
     * @return
     */
    public static String addMonth(int month) {
        return addMonth(LocalDateTime.now(), month, YYYYMMDDHHMMSS);
    }

    /**
     * '월' 더하기
     * 
     * @param date  YYYYMMDDHHMMSS 형식
     * @param month 증가할 숫자
     * @return
     */
    public static String addMonth(String date, int month) {
        return addMonth(getLocalDateTime(date), month, YYYYMMDDHHMMSS);
    }

    /**
     * '월' 더하기
     * 
     * @param date   YYYYMMDDHHMMSS 형식
     * @param month  증가할 숫자
     * @param format 날짜 형식
     * @return
     */
    public static String addMonth(LocalDateTime date, int month, DateTimeFormatter format) {
        return date.with((temporal) -> temporal.with(MONTH_OF_YEAR, date.getMonthValue())
                   .plus(month, ChronoUnit.MONTHS))
                   .format(format);
    }

    /**
     * 현재시간에서 '일' 더하기
     * 
     * @param day 증가할 숫자
     * @return
     */
    public static String addDay(int day) {
        return addDay(LocalDateTime.now(), day, YYYYMMDDHHMMSS);
    }

    /**
     * '일' 더하기
     * 
     * @param date YYYYMMDDHHMMSS 형식
     * @param day  증가할 숫자
     * @return
     */
    public static String addDay(String date, int day) {
        LocalDateTime localDateTime = getLocalDateTime(date);
        return addDay(localDateTime, day, YYYYMMDDHHMMSS);
    }

    /**
     * '일' 더하기
     * 
     * @param date   YYYYMMDDHHMMSS 형식
     * @param day    증가할 숫자
     * @param format 날짜 형식
     * @return
     */
    public static String addDay(LocalDateTime date, int day, DateTimeFormatter format) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, date.getDayOfMonth())
                   .plus(day, ChronoUnit.DAYS))
                   .format(format);
    }

    /**
     * 시간 더하기
     * 
     * @param hour 증가할 숫자
     * @return
     */
    public static String addHour(int hour) {
        return addHour(LocalDateTime.now(), hour, YYYYMMDDHHMMSS);
    }

    /**
     * 시간 더하기
     * 
     * @param date YYYYMMDDHHMMSS 형식
     * @param hour 증가할 숫자
     * @return
     */
    public static String addHour(String date, int hour) {
        LocalDateTime localDateTime = getLocalDateTime(date);
        return addHour(localDateTime, hour, YYYYMMDDHHMMSS);
    }

    /**
     * 시간 더하기
     * 
     * @param date   YYYYMMDDHHMMSS 형식
     * @param hour   증가할 숫자
     * @param format 날짜 형식
     * @return
     */
    public static String addHour(LocalDateTime date, int hour, DateTimeFormatter format) {
        return date.with((temporal) -> temporal.with(HOUR_OF_DAY, date.getHour())
                   .plus(hour, ChronoUnit.HOURS))
                   .format(format);
    }

    /**
     * 분 더하기
     * 
     * @param minute 증가할 숫자
     * @return
     */
    public static String addMinute(int minute) {
        return addMinute(LocalDateTime.now(), minute, YYYYMMDDHHMMSS);
    }

    /**
     * 분 더하기
     * 
     * @param date   YYYYMMDDHHMMSS 형식
     * @param minute 증가할 숫자
     * @return
     */
    public static String addMinute(String date, int minute) {
        LocalDateTime localDateTime = getLocalDateTime(date);
        return addMinute(localDateTime, minute, YYYYMMDDHHMMSS);
    }

    /**
     * 분 더하기
     * 
     * @param date   YYYYMMDDHHMMSS 형식
     * @param minute 증가할 숫자
     * @param format 날짜 형식
     * @return
     */
    public static String addMinute(LocalDateTime date, int minute, DateTimeFormatter format) {
        return date.with((temporal) -> temporal.with(MINUTE_OF_HOUR, date.getMinute())
                   .plus(minute, ChronoUnit.MINUTES))
                   .format(format);
    }

    /**
     * 현재 시간에서 '초' 더하기
     * 
     * @param second 증가할 숫자
     * @return
     */
    public static String addSeconds(int second) {
        return addSeconds(LocalDateTime.now(), second, YYYYMMDDHHMMSS);
    }

    /**
     * '초' 더하기
     * 
     * @param date   YYYYMMDDHHMMSS 형식
     * @param second 증가할 숫자
     * @return
     */
    public static String addSeconds(String date, int second) {
        LocalDateTime localDateTime = getLocalDateTime(date);
        return addSeconds(localDateTime, second, YYYYMMDDHHMMSS);
    }

    /**
     * '초' 더하기
     * 
     * @param date   LocalDateTime
     * @param second 증가할 숫자
     * @param format 날짜 형식
     * @return
     */
    public static String addSeconds(LocalDateTime date, int second, DateTimeFormatter format) {
        return date.with((temporal) -> temporal.with(SECOND_OF_MINUTE, date.getSecond()).plus(second, ChronoUnit.SECONDS))
                   .format(format);
    }

}
