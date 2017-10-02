package com.example.ultorkay.tvserie;

import android.os.AsyncTask;

import com.google.gson.JsonArray;


import static com.example.ultorkay.tvserie.JsonUtil.GetJsonArrayFromUrl;


public class TVSeriesList extends AsyncTask<String, Integer, JsonArray> {
    protected JsonArray doInBackground(String... strings) {
        JsonArray arr = GetJsonArrayFromUrl(strings[0]);
        return arr;
    }

    protected void onProgressUpdate(Integer... progress) {
        System.out.println(progress[0]);
    }

    protected void onPostExecute(JsonArray result) {
        System.out.println("Downloaded " + result.toString().length() + " bytes");
    }
}
