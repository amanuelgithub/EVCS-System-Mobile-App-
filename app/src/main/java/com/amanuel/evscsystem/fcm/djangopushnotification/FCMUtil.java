package com.amanuel.evscsystem.fcm.djangopushnotification;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FCMUtil {

    private final static String app_server_url = "http://192.168.137.1:8000/HelloWorldApp/insert/?fcm_token="; //change it to your server address

    public static void sendRegistrationTokenToServer(String token){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, app_server_url + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //@todo: do something when fcm token is send
                        Log.d("MyFCMService", "New FCM Token: " + token);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //@todo: show error response
//                Toast.makeText(PushNotifyActivity.this, "Error.." + error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fcm_token", token);
                return params;
            }

        };
    }
}
