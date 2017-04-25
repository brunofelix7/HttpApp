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
import com.example.httpapp.model.Root;
import com.example.httpapp.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();

        User user = new User("bK", "12345");
        user.save();
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

    private void getData(){
        okHttpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(Constants.BASE_URL)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.code() == 200){
                try {
                    json = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                response.message();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Gson gson = new Gson();
                    //Type listType = new TypeToken<ArrayList<Root>>(){}.getType();
                    //List<Root> users = gson.fromJson(json, new TypeToken<List<Root>>(){}.getType());
                    Log.d("AppTest", json);
                    //Toast.makeText(MainActivity.this, json, Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*private class ExampleOkHttp extends AsyncTask<Void, Void, Response> {

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Response doInBackground(Void... params) {

        }
        @Override
        protected void onPostExecute(Response response) {

        }

    }*/

}
