package com.launcher.elderlylauncher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactFind extends Activity {

    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_find);

        search = (Button)findViewById(R.id.btnSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
