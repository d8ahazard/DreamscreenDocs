package com.amazonaws.util.json;

import com.amazonaws.util.DateUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateDeserializer implements JsonDeserializer<Date>, JsonSerializer<Date> {
    private final List<String> dateFormats;
    private final SimpleDateFormat mIso8601DateFormat = new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN);
    private SimpleDateFormat mSimpleDateFormat;

    public DateDeserializer(String[] dateFormats2) {
        this.dateFormats = Arrays.asList(dateFormats2);
    }

    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext context) {
        String dateString = element.getAsString();
        for (String df : this.dateFormats) {
            try {
                Date date = new Date();
                try {
                    this.mSimpleDateFormat = new SimpleDateFormat(df);
                    date.setTime(this.mSimpleDateFormat.parse(dateString).getTime());
                    Date date2 = date;
                    return date;
                } catch (ParseException e) {
                    Date date3 = date;
                }
            } catch (ParseException e2) {
            }
        }
        try {
            return DateFormat.getDateInstance(2).parse(dateString);
        } catch (ParseException e3) {
            throw new JsonParseException(e3.getMessage(), e3);
        }
    }

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        JsonPrimitive jsonPrimitive;
        synchronized (this.mIso8601DateFormat) {
            jsonPrimitive = new JsonPrimitive(this.mIso8601DateFormat.format(src));
        }
        return jsonPrimitive;
    }
}
