package com.kys.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void 날짜비교테스트() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = LocalDateTime.of(2019, 01, 15, 17, 50, 44);
        LocalDateTime future = LocalDateTime.of(2019, 05, 15, 17, 50, 44);

        assertThat(DateUtil.isPast(now, past), equalTo(false));
        assertThat(DateUtil.isPast(now, future), equalTo(true));
        assertThat(DateUtil.isPastOrEquals(now, past), equalTo(false));
        assertThat(DateUtil.isPastOrEquals(now, future), equalTo(true));
        assertThat(DateUtil.isPastOrEquals(now, now), equalTo(true));

        assertThat(DateUtil.isFuture(now, past), equalTo(true));
        assertThat(DateUtil.isFuture(now, future), equalTo(false));
        assertThat(DateUtil.isEqualsOrFuture(now, past), equalTo(true));
        assertThat(DateUtil.isEqualsOrFuture(now, future), equalTo(false));
        assertThat(DateUtil.isEqualsOrFuture(now, now), equalTo(true));

        assertThat(DateUtil.isEquals(now, now), equalTo(true));
        assertThat(DateUtil.isEquals(now, future), equalTo(false));
        assertThat(DateUtil.isEquals(now, past), equalTo(false));
    }

    @Test
    public void 날짜비교테스트2() throws Exception {
        LocalDate now = LocalDate.now();
        LocalDate past = LocalDate.of(2019, 01, 15);
        LocalDate future = LocalDate.of(2019, 05, 15);

        assertThat(DateUtil.isPast(now, past), equalTo(false));
        assertThat(DateUtil.isPast(now, future), equalTo(true));
        assertThat(DateUtil.isPastOrEquals(now, past), equalTo(false));
        assertThat(DateUtil.isPastOrEquals(now, future), equalTo(true));
        assertThat(DateUtil.isPastOrEquals(now, now), equalTo(true));

        assertThat(DateUtil.isFuture(now, past), equalTo(true));
        assertThat(DateUtil.isFuture(now, future), equalTo(false));
        assertThat(DateUtil.isEqualsOrFuture(now, past), equalTo(true));
        assertThat(DateUtil.isEqualsOrFuture(now, future), equalTo(false));
        assertThat(DateUtil.isEqualsOrFuture(now, now), equalTo(true));

        assertThat(DateUtil.isEquals(now, now), equalTo(true));
        assertThat(DateUtil.isEquals(now, future), equalTo(false));
        assertThat(DateUtil.isEquals(now, past), equalTo(false));
    }

    @Test
    public void 날짜비교테스트3() throws Exception {
        String now = LocalDateTime.now().format(DateUtil.YYYYMMDDHHMMSS);

        assertThat(DateUtil.isPast(now), equalTo(true));
        assertThat(DateUtil.isPastOrEquals(now), equalTo(true));
        assertThat(DateUtil.isPastOrEquals(now), equalTo(true));
        assertThat(DateUtil.isPastOrEquals(now), equalTo(true));

        assertThat(DateUtil.isFuture(now), equalTo(false));
        assertThat(DateUtil.isEqualsOrFuture(now), equalTo(false));
        assertThat(DateUtil.isEqualsOrFuture(now), equalTo(false));
        assertThat(DateUtil.isEqualsOrFuture(now), equalTo(false));
    }
}
