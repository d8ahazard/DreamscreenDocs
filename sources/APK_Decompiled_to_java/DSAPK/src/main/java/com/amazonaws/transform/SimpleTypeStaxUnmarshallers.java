package com.amazonaws.transform;

import com.amazonaws.util.Base64;
import com.amazonaws.util.DateUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleTypeStaxUnmarshallers {
    /* access modifiers changed from: private */
    public static Log log = LogFactory.getLog(SimpleTypeStaxUnmarshallers.class);

    public static class BigDecimalStaxUnmarshaller implements Unmarshaller<BigDecimal, StaxUnmarshallerContext> {
        private static BigDecimalStaxUnmarshaller instance;

        public BigDecimal unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String s = unmarshallerContext.readText();
            if (s == null) {
                return null;
            }
            return new BigDecimal(s);
        }

        public static BigDecimalStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new BigDecimalStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class BigIntegerStaxUnmarshaller implements Unmarshaller<BigInteger, StaxUnmarshallerContext> {
        private static BigIntegerStaxUnmarshaller instance;

        public BigInteger unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String s = unmarshallerContext.readText();
            if (s == null) {
                return null;
            }
            return new BigInteger(s);
        }

        public static BigIntegerStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new BigIntegerStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class BooleanStaxUnmarshaller implements Unmarshaller<Boolean, StaxUnmarshallerContext> {
        private static BooleanStaxUnmarshaller instance;

        public Boolean unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String booleanString = unmarshallerContext.readText();
            if (booleanString == null) {
                return null;
            }
            return Boolean.valueOf(Boolean.parseBoolean(booleanString));
        }

        public static BooleanStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new BooleanStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class ByteBufferStaxUnmarshaller implements Unmarshaller<ByteBuffer, StaxUnmarshallerContext> {
        private static ByteBufferStaxUnmarshaller instance;

        public ByteBuffer unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            return ByteBuffer.wrap(Base64.decode(unmarshallerContext.readText()));
        }

        public static ByteBufferStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new ByteBufferStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class ByteStaxUnmarshaller implements Unmarshaller<Byte, StaxUnmarshallerContext> {
        private static ByteStaxUnmarshaller instance;

        public Byte unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String byteString = unmarshallerContext.readText();
            if (byteString == null) {
                return null;
            }
            return Byte.valueOf(byteString);
        }

        public static ByteStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new ByteStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class DateStaxUnmarshaller implements Unmarshaller<Date, StaxUnmarshallerContext> {
        private static DateStaxUnmarshaller instance;

        public Date unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            Date date = null;
            String dateString = unmarshallerContext.readText();
            if (dateString == null) {
                return date;
            }
            try {
                return DateUtils.parseISO8601Date(dateString);
            } catch (Exception e) {
                SimpleTypeStaxUnmarshallers.log.warn("Unable to parse date '" + dateString + "':  " + e.getMessage(), e);
                return date;
            }
        }

        public static DateStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new DateStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class DoubleStaxUnmarshaller implements Unmarshaller<Double, StaxUnmarshallerContext> {
        private static DoubleStaxUnmarshaller instance;

        public Double unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String doubleString = unmarshallerContext.readText();
            if (doubleString == null) {
                return null;
            }
            return Double.valueOf(Double.parseDouble(doubleString));
        }

        public static DoubleStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new DoubleStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class FloatStaxUnmarshaller implements Unmarshaller<Float, StaxUnmarshallerContext> {
        private static FloatStaxUnmarshaller instance;

        public Float unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String floatString = unmarshallerContext.readText();
            if (floatString == null) {
                return null;
            }
            return Float.valueOf(floatString);
        }

        public static FloatStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new FloatStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class IntegerStaxUnmarshaller implements Unmarshaller<Integer, StaxUnmarshallerContext> {
        private static IntegerStaxUnmarshaller instance;

        public Integer unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String intString = unmarshallerContext.readText();
            if (intString == null) {
                return null;
            }
            return Integer.valueOf(Integer.parseInt(intString));
        }

        public static IntegerStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new IntegerStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class LongStaxUnmarshaller implements Unmarshaller<Long, StaxUnmarshallerContext> {
        private static LongStaxUnmarshaller instance;

        public Long unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            String longString = unmarshallerContext.readText();
            if (longString == null) {
                return null;
            }
            return Long.valueOf(Long.parseLong(longString));
        }

        public static LongStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new LongStaxUnmarshaller();
            }
            return instance;
        }
    }

    public static class StringStaxUnmarshaller implements Unmarshaller<String, StaxUnmarshallerContext> {
        private static StringStaxUnmarshaller instance;

        public String unmarshall(StaxUnmarshallerContext unmarshallerContext) throws Exception {
            return unmarshallerContext.readText();
        }

        public static StringStaxUnmarshaller getInstance() {
            if (instance == null) {
                instance = new StringStaxUnmarshaller();
            }
            return instance;
        }
    }
}
