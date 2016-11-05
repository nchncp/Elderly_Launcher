package com.launcher.elderlylauncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by nicha on 9/13/16.
 */
public class ContactsActivity extends Activity {

    private ListView listView;
    private Button findContact;

    final List<ContactBook> listPhoneBook = new ArrayList<ContactBook>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshContact();
    }

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = (ListView)findViewById(R.id.listView1);

        findContact = (Button)findViewById(R.id.btnFindContact);

        refreshContact();

        //Find People
        findContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsActivity.this, ContactFind.class);
                startActivityForResult(i,9999);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsActivity.this, MessagesSend.class);
                intent.putExtra("Reciever", listPhoneBook.get(position).getmUsername());
                startActivityForResult(intent, 9998);
            }
        });
    }

    private void refreshContact() {
        //Add Contact
//        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar0), "Daugther", "Varistha", "Thato", "098-765-4321", "daugther@gmail.com"));
//        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar1), "Son", "Anyakrit", "Thato", "098-765-4321", "son@gmail.com"));
//        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar2), "GrandDaughter", "Pahfun", "Thato", "098-765-4321", "grand_daugther@gmail.com"));
//        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar3), "GrandSon", "Boonyarit", "Thato", "098-765-4321", "grand_son@gmail.com"));

        SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        final String AccountID = sharedPreferences.getString("AccountID", "");

        int count = sharedPreferences.getInt("numContact", 0);
        for(int i=1; i<=count; i++){
            Set<String> temp = sharedPreferences.getStringSet("Contact_"+i, null);
            Log.d("temp", temp.toString());

            String user = null;
            String name = null;
            String lname = null;
            String phone = null;
            String email = null;
            for(String s : temp){
                if(s==null){
                    continue;
                }
                String[] val = s.split(":");
                if(val[0].equals("user")){
                    user = val[1];
                }else if(val[0].equals("name")){
                    name = val[1];
                }else if(val[0].equals("lname")){
                    lname = val[1];
                }else if(val[0].equals("phone")){
                    phone = val[1];
                }else if(val[0].equals("email")){
                    email = val[1];
                }
            }

            listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar0), user, name, lname, phone, email));
        }

        ContactBookAdapter adapter = new ContactBookAdapter(this, listPhoneBook);
        listView.setAdapter(adapter);
    }

}
