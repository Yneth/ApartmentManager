package ua.abond.lab4.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParseTest {

    @Test
    public void testParseIntObject() throws Exception {
        assertEquals(new Integer(123), Parse.intObject("123", null));
    }

    @Test
    public void testParseIntObjectReturnFallback() throws Exception {
        assertEquals(null, Parse.intObject("89nsd2jkfiu1"));
    }

    @Test
    public void testIntValue() throws Exception {
        assertEquals(123, Parse.intValue("123", 0));
    }

    @Test
    public void testIntValueReturnFallback() throws Exception {
        assertEquals(0, Parse.intValue("j3i298gfi1"));
    }

    @Test
    public void testParseLongObject() throws Exception {
        assertEquals(new Long(123), Parse.longObject("123"));
    }

    @Test
    public void testParseLongObjectReturnFallback() throws Exception {
        assertNull(Parse.longObject("123dskfjvgvuhods"));
    }

    @Test
    public void testParseEnumeration() throws Exception {
        assertEquals(TestEnum.VAL2, Parse.enumeration(TestEnum.class, "VAL2", TestEnum.VAL0));
    }

    @Test
    public void testParseEnumerationReturnFallback() throws Exception {
        assertEquals(TestEnum.VAL0, Parse.enumeration(TestEnum.class, "beer", TestEnum.VAL0));
    }

    @Test
    public void testParseDoubleObject() throws Exception {
        assertEquals(new Double(123), Parse.doubleObject("123"));
    }

    @Test
    public void testParseDoubleObjectReturnFallback() throws Exception {
        assertNull(Parse.doubleObject("1fdsln23"));
    }

    @Test
    public void testParseBigDecimal() throws Exception {
        assertEquals(new BigDecimal("2321.312873721378213"),
                Parse.bigDecimal("2321.312873721378213"));
    }

    @Test
    public void testParseBigDecimalReturnFallback() throws Exception {
        assertNull(Parse.bigDecimal("1fdsln23"));
    }

    @Test
    public void testParseLocalDateTime() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss.SSS");
        assertEquals(now, Parse.localDateTime(now.format(dtf), dtf));
    }

    @Test
    public void testParseLocalDateTimeReturnFallback() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss.SSS");
        assertNull(Parse.localDateTime("dsadjask", dtf));
    }

    private enum TestEnum {
        VAL0, VAL1, VAL2, VAL3
    }
}