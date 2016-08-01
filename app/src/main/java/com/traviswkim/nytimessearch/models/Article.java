package com.traviswkim.nytimessearch.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by traviswkim on 7/24/16.
 */
@Parcel
public class Article implements Serializable {

    String webUrl;
    String headline;
    String thumbnail;
    String snippet;
    List<Article> articles;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() { return thumbnail; }

    public String getSnippet() {
        return snippet;
    }

    public Article(){
        articles = new ArrayList<Article>();
    }

    public Article(JSONObject jsonObject){
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");
            this.snippet = jsonObject.getString("snippet").toString();

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            }else{
                this.thumbnail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Article> fromJsonArray(JSONArray array){
        ArrayList<Article> results = new ArrayList<>();
        for(int i=0; i<array.length(); i++){
            try {
                results.add(new Article(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
    public static ArrayList<Article> parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        ArrayList articleResponse = gson.fromJson(response, ArrayList.class);
        return articleResponse;
    }
}
