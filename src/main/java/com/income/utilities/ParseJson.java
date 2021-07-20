package com.income.utilities;

import io.restassured.path.json.JsonPath;

public class ParseJson {
    public static JsonPath fromRawToJson(String response) {
        JsonPath jsn = new JsonPath(response);
        return jsn;
    }
}
