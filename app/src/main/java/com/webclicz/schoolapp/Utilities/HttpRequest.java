package com.webclicz.schoolapp.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.webclicz.schoolapp.VolleyCallback;

import org.json.JSONObject;

/**
 * Created by Linesh on 9/26/2017.
 */

public class HttpRequest {
    ProgressDialog pd;
    public void doPost(final Context con, String apiUrl, JSONObject input, final VolleyCallback volleyCallback){
        RequestQueue queue = Volley.newRequestQueue(con);
        pd = new ProgressDialog(con);
        pd.setMessage("Loading..");
        pd.show();
        Log.e("count", "count");
        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.POST, apiUrl, input,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObj) {
                    pd.dismiss();
                    if (jsonObj != null) {
                        volleyCallback.onSuccess(jsonObj);
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("err", error.toString());
                    pd.dismiss();
                }
        });
        queue.add(request);
    }

    public void doGet(final Context con, String apiUrl, final VolleyCallback volleyCallback){
        RequestQueue queue = Volley.newRequestQueue(con);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, apiUrl,  null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObj) {

                        if (jsonObj != null) {
                            volleyCallback.onSuccess(jsonObj);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("err", error.toString());
                        Toast.makeText(con, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(request);
    }

}
