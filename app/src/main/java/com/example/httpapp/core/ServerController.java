package com.example.httpapp.core;

import com.google.gson.Gson;

public class ServerController {

    private static ServerController INSTANCE = null;

    private ServerController(){}

    public static ServerController getInstance(){
        if (INSTANCE == null)
            INSTANCE = new ServerController();

        return INSTANCE;
    }

    public static String loadAuthFromJSON(String json){
        Gson gson = new Gson();
        ServerResponse ar = gson.fromJson(json, ServerResponse.class);
        return ar.toString();
    }

}
