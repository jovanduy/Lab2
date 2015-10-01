package com.mobileproto.jovanduy.photofeed;


import android.content.Context;
import android.telecom.Call;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jordan on 9/24/15.
 */
public class HttpHandler {

    public RequestQueue queue;

    public HttpHandler(Context context) { queue = Volley.newRequestQueue(context); }

    /**
     * sends to GET request to
     * @param searchQuery String representing what is being searched
     * @param callback callback
     */
    public void searchImages(String searchQuery, final Callback callback) {
        String query = searchQuery.replaceAll(" ", "+");
        String url = "https://www.googleapis.com/customsearch/v1";
        String key = "AIzaSyDiS0H5_uYE54YwVqBKvsz2yPbDcWNQsxw";
        String cx = "003519602263697178317:rf_putw_noi";
        url += "?key=" + key;
        url += "&cx=" + cx;
        url += "&q=" + query;
        url += "&searchType=image";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<String> links = new ArrayList<>();
                        try {
                            JSONArray images = response.getJSONArray("items");
                            for (int i=0; i<images.length(); i++) {
                                JSONObject image = (JSONObject) images.get(i);
                                String link = image.getString("link");
                                Log.d("Link", link);
                                links.add(link);
                            }
                        } catch (Exception e) {
                            callback.callback(false, null);
                            Log.e("Error!", e.getMessage());
                        }
                        callback.callback(true, links);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error!", error.getMessage());
                        callback.callback(false, null);
                    }
                }
        );
        queue.add(request);
    }

}
