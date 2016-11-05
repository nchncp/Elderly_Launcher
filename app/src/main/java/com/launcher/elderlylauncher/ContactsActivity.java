package com.launcher.elderlylauncher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicha on 9/13/16.
 */
public class ContactsActivity extends Activity {

    private ListView listView;
    private Button findContact;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_contacts);

        SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        final String AccountID = sharedPreferences.getString("AccountID", "");

        listView = (ListView)findViewById(R.id.listView1);

        findContact = (Button)findViewById(R.id.btnFindContact);

        //Add Contact
        List<ContactBook> listPhoneBook = new ArrayList<ContactBook>();
        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar0), "Daugther", "Varistha", "Thato", "098-765-4321", "daugther@gmail.com"));
        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar1), "Son", "Anyakrit", "Thato", "098-765-4321", "son@gmail.com"));
        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar2), "GrandDaughter", "Pahfun", "Thato", "098-765-4321", "grand_daugther@gmail.com"));
        listPhoneBook.add(new ContactBook(BitmapFactory.decodeResource(getResources(), R.drawable.c_avatar3), "GrandSon", "Boonyarit", "Thato", "098-765-4321", "grand_son@gmail.com"));

        ContactBookAdapter adapter = new ContactBookAdapter(this, listPhoneBook);
        listView.setAdapter(adapter);

        //Find People
        findContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsActivity.this, ContactFind.class);
                startActivity(i);
            }
        });

    }

}
