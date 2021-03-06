package com.launcher.elderlylauncher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.launcher.elderlylauncher.R.styleable.View;

/**
 * Created by nicha on 9/25/16.
 */

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        // txtUsername & txtPassword
        final EditText txtUser = (EditText) findViewById(R.id.txtUsername);
        final EditText txtPass = (EditText) findViewById(R.id.txtPassword);

        // btnLogin
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);

        // Perform action on click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String url = "http://dlab.sit.kmutt.ac.th/el_launcher/checkAccount.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("strUser", txtUser.getText().toString()));
                params.add(new BasicNameValuePair("strPass", txtPass.getText().toString()));

                String resultServer = Util.getHttpPost(url, params);

                /*** Default Value ***/
                String strStatusID = "0";
                String strAccountID = "0";
                String strError = "Unknow Status!";

                String strUsername = "";
                String strFName = "";
                String strLName = "";

                JSONObject c;
                try {
                    c = new JSONObject(resultServer);
                    strStatusID = c.getString("StatusID");
                    strAccountID = c.getString("AccountID");
                    strError = c.getString("Error");

                    strUsername = c.getString("Username");
                    strFName = c.getString("FName");
                    strLName = c.getString("LName");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Prepare Login
                if (strStatusID.equals("0")) {
                    // Dialog
                    ad.setTitle("Error! ");
                    ad.setIcon(android.R.drawable.btn_star_big_on);
                    ad.setPositiveButton("Close", null);
                    ad.setMessage(strError);
                    ad.show();
                    txtUser.setText("");
                    txtPass.setText("");
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("AccountID", strAccountID);
                    editor.putString("Username",strUsername);
                    editor.putString("FName", strFName);
                    editor.putString("LName", strLName);
                    editor.commit();

                    final String strToken = FirebaseInstanceId.getInstance().getToken();

                    url = "http://dlab.sit.kmutt.ac.th/el_launcher/token.php";
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("strID", strAccountID));
                    params.add(new BasicNameValuePair("strIDToken", strToken));

                    resultServer = Util.getHttpPost(url, params);
                    Log.d("555", resultServer);

                    Toast.makeText(LoginActivity.this, "Login OK", Toast.LENGTH_SHORT).show();
                    
                    Intent newActivity = new Intent(LoginActivity.this, HomeActivity.class);
                    newActivity.putExtra("AccountID", strAccountID);
                    newActivity.putExtra("Username", strUsername);
                    startActivity(newActivity);
                }

            }

        });
    }

    public void showRegister(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}