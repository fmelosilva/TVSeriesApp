package com.example.ultorkay.tvserie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import static com.example.ultorkay.tvserie.JsonUtil.GetJsonObjectFromUrl;

public class TVSerieActivity extends AppCompatActivity {
    private static String LOG_TAG = "TVSerieActivity";
    TextView label;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tvserie);
        label  = (TextView)findViewById(R.id.tvserie_textView);
        image = (ImageView) findViewById(R.id.tvserie_imageView);

        Bundle b = getIntent().getExtras();
        String id = "";
        if(b != null)
            id = b.getString("key");
        super.onCreate(savedInstanceState);
        new TVSerieList().execute("http://192.168.15.6:4567/tvseries/" + id);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    class TVSerieList extends AsyncTask<String, Integer, JsonObject> {
        @Override
        protected JsonObject doInBackground(String... params) {
            return  GetJsonObjectFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(JsonObject result) {
            label.setText(
                    "name: " + result.get("name").toString().replace("\"", "") + '\n' +
                    "genres: " + result.get("genres").toString().replace("\"", "") + '\n' +
                    "status: " + result.get("status").toString().replace("\"", "") + '\n' +
                    "language: " + result.get("language").toString().replace("\"", "") + '\n' +
                    "summary: " + result.get("summary").toString().replace("\"", ""));

            String encodedImage = result.get("image").toString().replace("\"", "");
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image.setImageBitmap(decodedByte);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Integer... values) {}
    }
}