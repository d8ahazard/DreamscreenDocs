package com.amazonaws.util.json;

import com.amazonaws.AmazonClientException;
import com.amazonaws.util.BinaryUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Date;

final class JacksonFactory implements AwsJsonFactory {
    private final JsonFactory factory = new JsonFactory();

    /* renamed from: com.amazonaws.util.json.JacksonFactory$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$core$JsonToken = new int[JsonToken.values().length];

        static {
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.START_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.END_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.START_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.END_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.FIELD_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_TRUE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_FALSE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_INT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_FLOAT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NULL.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_STRING.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    private static final class JacksonReader implements AwsJsonReader {
        private JsonToken nextToken = null;
        private JsonParser reader;

        public JacksonReader(JsonFactory factory, Reader in) {
            try {
                this.reader = factory.createJsonParser(in);
            } catch (IOException e) {
                throw new AmazonClientException("Failed to create JSON reader", e);
            }
        }

        public void beginArray() throws IOException {
            nextToken();
            expect(JsonToken.START_ARRAY);
            clearToken();
        }

        public void endArray() throws IOException {
            nextToken();
            expect(JsonToken.END_ARRAY);
            clearToken();
        }

        public void beginObject() throws IOException {
            nextToken();
            expect(JsonToken.START_OBJECT);
            clearToken();
        }

        public void endObject() throws IOException {
            nextToken();
            expect(JsonToken.END_OBJECT);
            clearToken();
        }

        public boolean isContainer() throws IOException {
            nextToken();
            return JsonToken.START_ARRAY == this.nextToken || JsonToken.START_OBJECT == this.nextToken;
        }

        public boolean hasNext() throws IOException {
            nextToken();
            return (JsonToken.END_OBJECT == this.nextToken || JsonToken.END_ARRAY == this.nextToken) ? false : true;
        }

        public String nextName() throws IOException {
            nextToken();
            expect(JsonToken.FIELD_NAME);
            clearToken();
            return this.reader.getText();
        }

        public String nextString() throws IOException {
            nextToken();
            String s = JsonToken.VALUE_NULL == this.nextToken ? null : this.reader.getText();
            clearToken();
            return s;
        }

        public AwsJsonToken peek() throws IOException {
            nextToken();
            return JacksonFactory.convert(this.nextToken);
        }

        public void skipValue() throws IOException {
            nextToken();
            this.reader.skipChildren();
            clearToken();
        }

        public void close() throws IOException {
            this.reader.close();
        }

        private void nextToken() throws IOException {
            if (this.nextToken == null) {
                this.nextToken = this.reader.nextToken();
            }
        }

        private void clearToken() throws IOException {
            this.nextToken = null;
        }

        private void expect(JsonToken token) throws IOException {
            if (this.nextToken != token) {
                throw new IOException("Expected " + token + " but was " + token);
            }
        }
    }

    private static final class JacksonWriter implements AwsJsonWriter {
        private static final int NEGATIVE_THREE = -3;
        private JsonGenerator writer;

        public JacksonWriter(JsonFactory factory, Writer out) {
            try {
                this.writer = factory.createGenerator(out);
            } catch (IOException e) {
                throw new AmazonClientException("Failed to create json writer", e);
            }
        }

        public AwsJsonWriter beginArray() throws IOException {
            this.writer.writeStartArray();
            return this;
        }

        public AwsJsonWriter endArray() throws IOException {
            this.writer.writeEndArray();
            return this;
        }

        public AwsJsonWriter beginObject() throws IOException {
            this.writer.writeStartObject();
            return this;
        }

        public AwsJsonWriter endObject() throws IOException {
            this.writer.writeEndObject();
            return this;
        }

        public AwsJsonWriter name(String name) throws IOException {
            this.writer.writeFieldName(name);
            return this;
        }

        public AwsJsonWriter value(String value) throws IOException {
            this.writer.writeString(value);
            return this;
        }

        public AwsJsonWriter value(boolean value) throws IOException {
            this.writer.writeBoolean(value);
            return this;
        }

        public AwsJsonWriter value(double value) throws IOException {
            this.writer.writeNumber(value);
            return this;
        }

        public AwsJsonWriter value(long value) throws IOException {
            this.writer.writeNumber(value);
            return this;
        }

        public AwsJsonWriter value(Number value) throws IOException {
            this.writer.writeNumber(value.toString());
            return this;
        }

        public AwsJsonWriter value(Date value) throws IOException {
            this.writer.writeNumber(BigDecimal.valueOf(value.getTime()).scaleByPowerOfTen(-3).toPlainString());
            return this;
        }

        public AwsJsonWriter value(ByteBuffer value) throws IOException {
            value.mark();
            byte[] bytes = new byte[value.remaining()];
            value.get(bytes, 0, bytes.length);
            value.reset();
            this.writer.writeString(BinaryUtils.toBase64(bytes));
            return this;
        }

        public AwsJsonWriter value() throws IOException {
            this.writer.writeNull();
            return this;
        }

        public void flush() throws IOException {
            this.writer.flush();
        }

        public void close() throws IOException {
            this.writer.close();
        }
    }

    JacksonFactory() {
    }

    public AwsJsonReader getJsonReader(Reader in) {
        return new JacksonReader(this.factory, in);
    }

    public AwsJsonWriter getJsonWriter(Writer out) {
        return new JacksonWriter(this.factory, out);
    }

    /* access modifiers changed from: private */
    public static AwsJsonToken convert(JsonToken token) {
        if (token == null) {
            return null;
        }
        switch (AnonymousClass1.$SwitchMap$com$fasterxml$jackson$core$JsonToken[token.ordinal()]) {
            case 1:
                return AwsJsonToken.BEGIN_ARRAY;
            case 2:
                return AwsJsonToken.END_ARRAY;
            case 3:
                return AwsJsonToken.BEGIN_OBJECT;
            case 4:
                return AwsJsonToken.END_OBJECT;
            case 5:
                return AwsJsonToken.FIELD_NAME;
            case 6:
            case 7:
                return AwsJsonToken.VALUE_BOOLEAN;
            case 8:
            case 9:
                return AwsJsonToken.VALUE_NUMBER;
            case 10:
                return AwsJsonToken.VALUE_NULL;
            case 11:
                return AwsJsonToken.VALUE_STRING;
            default:
                return AwsJsonToken.UNKNOWN;
        }
    }
}
