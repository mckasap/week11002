package org.kasapbasi.week11002;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    String JsonString = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"\n" +
            "}";
    final MediaType MT = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        ascCall myCall = new ascCall();

        myCall.execute(JsonString);
    }

    public class ascCall extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPostExecute(final String json) {
            super.onPostExecute(json);
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(json);
                }
            });
        }

        @Override
        protected String doInBackground(String... strings) {

            RequestBody body = RequestBody.create(MT, strings[0]);
            Request req = new Request.Builder()
                    .url("https://reqres.in/api/users")
                    .post(body)
                    .build();
            try {

                Response res = client.newCall(req).execute();
                return res.body().string();

            } catch (IOException ex) {
                return ex.getMessage();

            }
        }
    }

}
