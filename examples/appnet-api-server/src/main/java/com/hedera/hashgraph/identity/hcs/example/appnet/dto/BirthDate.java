package com.hedera.hashgraph.identity.hcs.example.appnet.dto;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.hedera.hashgraph.identity.utils.JsonUtils;

import java.util.LinkedHashMap;

public class BirthDate {
    public static final String[] JSON_PROPERTIES_ORDER = {"day", "month", "year"};
    @Expose
    private final int day;

    @Expose
    private final int month;

    @Expose
    private final int year;

    public BirthDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public JsonElement toNormalizedJsonElement() {
        Gson gson = JsonUtils.getGson();

        // First turn to normal JSON
        JsonObject root = gson.toJsonTree(this).getAsJsonObject();
        // Then put JSON properties in ordered map
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<>();

        for (String property : JSON_PROPERTIES_ORDER) {
            if (root.has(property)) {
                map.put(property, root.get(property));
            }
        }
        // Turn map to JSON
        return gson.toJsonTree(map);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
