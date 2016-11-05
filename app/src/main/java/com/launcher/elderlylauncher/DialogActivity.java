package com.launcher.elderlylauncher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends Activity {

    private Button reply, okay, cancel;
    private String strMessageID, strMessageTitle, strMessageBody;
    private TextView strTopic, strMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        strTopic = (TextView)findViewById(R.id.noti_title);
        strMessage = (TextView)findViewById(R.id.noti_detail);

        reply = (Button)findViewById(R.id.btnPeply);
        okay = (Button)findViewById(R.id.btnOkay);
        cancel = (Button)findViewById(R.id.btnCancel);

        strMessageID = getIntent().getStringExtra("messageID");
        strMessageTitle = getIntent().getStringExtra("messageTitle");
        strMessageBody = getIntent().getStringExtra("messageBody");

        strTopic.setText(strMessageTitle);
        strMessage.setText(strMessageBody);

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://dlab.sit.kmutt.ac.th/el_launcher/UpdateFlag.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("MessageID", strMessageID));
                params.add(new BasicNameValuePair("Flag", "1"));

                String resultServer = Util.getHttpPost(url, params);

                Intent sendMessage = new Intent(DialogActivity.this, MessagesSend.class);
                startActivity(sendMessage);
            }
        });

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://dlab.sit.kmutt.ac.th/el_launcher/UpdateFlag.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("MessageID", strMessageID));
                params.add(new BasicNameValuePair("Flag", "1"));

                String resultServer = Util.getHttpPost(url, params);

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://dlab.sit.kmutt.ac.th/el_launcher/UpdateFlag.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("MessageID", strMessageID));
                params.add(new BasicNameValuePair("Flag", "2"));

                String resultServer = Util.getHttpPost(url, params);

                finish();
            }
        });

    }


}
