package com.example.httpapp.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.httpapp.R;
import com.example.httpapp.core.Constants;
import com.example.httpapp.model.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient;
    private Request request;
    private Response response;
    private MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private String json;
    private String jsonString = "{\"email\":\"joaodasilva@mail.com\",\"nome\":\"João da Silva\"}";
    private TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_data = (TextView) findViewById(R.id.tv_data);
        new ExampleOkHttp().execute();
    }

    public void loadUserFromJSON(View v) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonString, User.class);
        Log.d("Gson", "User infos: " + user.getName() + " - " + user.getEmail());
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
    }

    public void parseUserToJSON(View v){
        User user = new User();
        user.setName("João da Silva");
        user.setEmail("joaodasilva@mail.com");
        Gson gson = new Gson();
        String userJSONString = gson.toJson(user);
        Log.d("AppTest", "User JSON String: " + userJSONString);
        Toast.makeText(this, userJSONString.toString(), Toast.LENGTH_LONG).show();
    }

    private class ExampleOkHttp extends AsyncTask<Void, Void, Response> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            URL url = null;
            try {
                url = new URL(Constants.BASE_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            okHttpClient = new OkHttpClient();
            request = new Request.Builder()
                    .url(url)
                    .build();
        }
        @Override
        protected Response doInBackground(Void... params) {
            try {
                response = okHttpClient.newCall(request).execute();
                return response;
            }catch (IOException e){
                return null;
            }
        }
        @Override
        protected void onPostExecute(Response response) {
            if (response.code() == 200){
                try {
                    Log.i("AppTest", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                response.message();
            }
            Toast.makeText(MainActivity.this, json, Toast.LENGTH_LONG).show();
        }

    }

}
