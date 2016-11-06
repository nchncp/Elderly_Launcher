package com.launcher.elderlylauncher;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactFind extends Activity {

    private EditText searchUser;
    private Button search;
    private ListView listContact;

    private String[] cList;

    private ContactBook[] listContactBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_find);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        searchUser = (EditText)findViewById(R.id.searchContact);

        search = (Button)findViewById(R.id.btnSearch);

        listContact = (ListView)findViewById(R.id.listContact);
        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
                int numContact = sharedPreferences.getInt("numContact", 0);
                numContact++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> newSet = new HashSet<String>();
                newSet.add(null);
                newSet.add("user:"+listContactBook[position].getmUsername());
                newSet.add("name:"+listContactBook[position].getmName());
                newSet.add("lname:"+listContactBook[position].getmLName());
                newSet.add("phone:"+listContactBook[position].getmPhone());
                newSet.add("email:"+listContactBook[position].getmEmail());
                editor.putStringSet("Contact_"+numContact, newSet);
                editor.putInt("numContact", numContact);
                editor.commit();

                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://dlab.sit.kmutt.ac.th/el_launcher/getAccountbyUsername.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("strUser", searchUser.getText().toString()));

                String resultServer = Util.getHttpPost(url, params);

                try {
                    JSONArray lContact = new JSONArray(resultServer);
                    cList = new String[lContact.length()];
                    listContactBook = new ContactBook[lContact.length()];
                    for(int i=0; i<lContact.length(); i++){
                        cList[i]=lContact.getJSONObject(i).getString("Username")+" ("+lContact.getJSONObject(i).getString("FName")+")";
                        listContactBook[i]=new ContactBook(null, lContact.getJSONObject(i).getString("Username"), lContact.getJSONObject(i).getString("FName"), lContact.getJSONObject(i).getString("LName"), lContact.getJSONObject(i).getString("PhoneNum"), lContact.getJSONObject(i).getString("Email"));
                    }
                    ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(ContactFind.this, R.layout.contacts_view, cList);
                    listContact.setAdapter(cAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
