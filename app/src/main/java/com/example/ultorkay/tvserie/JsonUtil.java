package com.example.ultorkay.tvserie;

import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtil {
    public static JsonArray GetJsonArrayFromUrl(String url) {
        JsonArray rootarray = null;
        try{
            URL n_url = new URL(url);
            System.out.println(url);
            HttpURLConnection request = (HttpURLConnection) n_url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            rootarray = root.getAsJsonArray();
        } catch(JsonIOException | JsonSyntaxException | IOException ex){
            ex.printStackTrace();
        }
        return rootarray;
    }

    public static JsonObject GetJsonObjectFromUrl(String url) {
        JsonObject rootobj = null;
        try{
            URL n_url = new URL(url);
            HttpURLConnection request = (HttpURLConnection) n_url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            rootobj = root.getAsJsonObject();
        } catch(JsonIOException | JsonSyntaxException | IOException ex){
            ex.printStackTrace();
        }
        return rootobj;
    }
}