package org.lime.resourcepack.clear.utils;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;

public class JsonFormatter {
    public static String format(JsonElement json) {
        return format(json, "    ");
    }
    public static String format(JsonElement json, String indent) {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.setIndent(indent);
            jsonWriter.setLenient(true);
            com.google.gson.internal.Streams.write(json, jsonWriter);
            return stringWriter.toString();
        } catch (IOException var3) {
            throw new AssertionError(var3);
        }
    }
}
