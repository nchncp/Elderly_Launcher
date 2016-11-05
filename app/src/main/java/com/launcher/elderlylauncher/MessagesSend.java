package com.launcher.elderlylauncher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicha on 10/27/16.
 */
public class MessagesSend extends Activity {

    EditText Topic, Message, RecieveUser;
    Button btnSend;
    RequestQueue requestQueue;
    String insertUrl = "http://dlab.sit.kmutt.ac.th/el_launcher/sendMessage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_send);

        Topic = (EditText)findViewById(R.id.messageTitle);
        Message = (EditText)findViewById(R.id.messageField);
        RecieveUser = (EditText)findViewById(R.id.to);
        btnSend = (Button)findViewById(R.id.btnSend);

        SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        final String strAccountID = sharedPreferences.getString("AccountID", "");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String,String> parameters = new HashMap<String, String>();
                        parameters.put("Topic", Topic.getText().toString());
                        parameters.put("Message", Message.getText().toString());
                        parameters.put("RecieveUser", RecieveUser.getText().toString());
                        parameters.put("AccountID", strAccountID);
                        parameters.put("DeliveryType", "1");

                        return parameters;
                    }
                };

                requestQueue.add(request);

                finish();
            }
        });

    }

    public void backMessages(View v) {
        finish();
    }

}
