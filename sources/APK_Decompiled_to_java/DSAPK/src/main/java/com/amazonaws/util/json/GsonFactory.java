package com.amazonaws.util.json;

import com.amazonaws.util.BinaryUtils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Date;

final class GsonFactory implements AwsJsonFactory {

    private static final class GsonReader implements AwsJsonReader {
        private final JsonReader reader;

        public GsonReader(Reader in) {
            this.reader = new JsonReader(in);
        }

        public void beginArray() throws IOException {
            this.reader.beginArray();
        }

        public void endArray() throws IOException {
            this.reader.endArray();
        }

        public void beginObject() throws IOException {
            this.reader.beginObject();
        }

        public void endObject() throws IOException {
            this.reader.endObject();
        }

        public boolean isContainer() throws IOException {
            JsonToken token = this.reader.peek();
            return JsonToken.BEGIN_ARRAY.equals(token) || JsonToken.BEGIN_OBJECT.equals(token);
        }

        public boolean hasNext() throws IOException {
            return this.reader.hasNext();
        }

        public String nextName() throws IOException {
            return this.reader.nextName();
        }

        public String nextString() throws IOException {
            JsonToken token = this.reader.peek();
            if (JsonToken.NULL.equals(token)) {
                this.reader.nextNull();
                return null;
            } else if (JsonToken.BOOLEAN.equals(token)) {
                return this.reader.nextBoolean() ? "true" : "false";
            } else {
                return this.reader.nextString();
            }
        }

        public void skipValue() throws IOException {
            this.reader.skipValue();
        }

        public AwsJsonToken peek() throws IOException {
            try {
                return GsonFactory.convert(this.reader.peek());
            } catch (EOFException e) {
                return null;
            }
        }

        public void close() throws IOException {
            this.reader.close();
        }
    }

    private static final class GsonWriter implements AwsJsonWriter {
        private static final int NEGATIVE_THREE = -3;
        private final JsonWriter writer;

        public GsonWriter(Writer out) {
            this.writer = new JsonWriter(out);
        }

        public AwsJsonWriter beginArray() throws IOException {
            this.writer.beginArray();
            return this;
        }

        public AwsJsonWriter endArray() throws IOException {
            this.writer.endArray();
            return this;
        }

        public AwsJsonWriter beginObject() throws IOException {
            this.writer.beginObject();
            return this;
        }

        public AwsJsonWriter endObject() throws IOException {
            this.writer.endObject();
            return this;
        }

        public AwsJsonWriter name(String name) throws IOException {
            this.writer.name(name);
            return this;
        }

        public AwsJsonWriter value(String value) throws IOException {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(boolean value) throws IOException {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(double value) throws IOException {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(long value) throws IOException {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(Number value) throws IOException {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(Date value) throws IOException {
            this.writer.value((Number) BigDecimal.valueOf(value.getTime()).scaleByPowerOfTen(-3));
            return this;
        }

        public AwsJsonWriter value(ByteBuffer value) throws IOException {
            value.mark();
            byte[] bytes = new byte[value.remaining()];
            value.get(bytes, 0, bytes.length);
            value.reset();
            this.writer.value(BinaryUtils.toBase64(bytes));
            return this;
        }

        public AwsJsonWriter value() throws IOException {
            this.writer.nullValue();
            return this;
        }

        public void flush() throws IOException {
            this.writer.flush();
        }

        public void close() throws IOException {
            this.writer.close();
        }
    }

    GsonFactory() {
    }

    public AwsJsonReader getJsonReader(Reader in) {
        return new GsonReader(in);
    }

    public AwsJsonWriter getJsonWriter(Writer out) {
        return new GsonWriter(out);
    }

    /* access modifiers changed from: private */
    public static AwsJsonToken convert(JsonToken token) {
        if (token == null) {
            return null;
        }
        switch (token) {
            case BEGIN_ARRAY:
                return AwsJsonToken.BEGIN_ARRAY;
            case END_ARRAY:
                return AwsJsonToken.END_ARRAY;
            case BEGIN_OBJECT:
                return AwsJsonToken.BEGIN_OBJECT;
            case END_OBJECT:
                return AwsJsonToken.END_OBJECT;
            case NAME:
                return AwsJsonToken.FIELD_NAME;
            case BOOLEAN:
                return AwsJsonToken.VALUE_BOOLEAN;
            case NUMBER:
                return AwsJsonToken.VALUE_NUMBER;
            case NULL:
                return AwsJsonToken.VALUE_NULL;
            case STRING:
                return AwsJsonToken.VALUE_STRING;
            case END_DOCUMENT:
                return null;
            default:
                return AwsJsonToken.UNKNOWN;
        }
    }
}
