package com.example.ultorkay.tvserie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.os.AsyncTask;
import android.util.Base64;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;

import com.google.gson.JsonArray;

import java.util.HashMap;

import static com.example.ultorkay.tvserie.JsonUtil.GetJsonArrayFromUrl;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "MainActivity";
    HashMap<Integer, String> posXkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posXkey = new HashMap<Integer, String>();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        new MyTask().execute("http://192.168.15.6:4567/tvseries");

    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                openTVSerie(posXkey.get(position));
            }
        });
    }

    public void openTVSerie(String id){
        Intent intent = new Intent(MainActivity.this, TVSerieActivity.class);
        Bundle b = new Bundle();
        b.putString("key", id); //Your id
        intent.putExtras(b);
        startActivity(intent);
    }

    class MyTask extends AsyncTask<String, Integer, JsonArray> {
        @Override
        protected JsonArray doInBackground(String... params) {
            String url = params[0];
            Log.i(LOG_TAG, url);
            JsonArray arr = GetJsonArrayFromUrl(url);

            return arr;
        }
        @Override
        protected void onPostExecute(JsonArray result) {
            for (int i = 0; i < result.size(); i++) {
                String id = result.get(i).getAsJsonObject().get("id").toString().replace("\"", "");
                String name = result.get(i).getAsJsonObject().get("name").toString().replace("\"", "");
                String encodedImage = result.get(i).getAsJsonObject().get("image").toString().replace("\"", "");
                String genres = result.get(i).getAsJsonObject().get("genres").toString().replace("\"", "");

                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                DataObject obj = new DataObject(name, genres, decodedByte);
                ((MyRecyclerViewAdapter) mAdapter).addItem(obj, i);
                posXkey.put(i, id);
            }
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }
}