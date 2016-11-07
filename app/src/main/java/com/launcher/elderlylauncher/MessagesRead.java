package com.launcher.elderlylauncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nicha on 9/16/16.
 */
public class MessagesRead extends Activity {
    private ListView jsonListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_read);
    }

    public interface APIService {
        //      @GET("stayintouch/WebApplication/examples/web/json/readMessage.php?accountid={accountid}")
//      Call<List<MessagesModel>> getMessage(@Path("accountid") String accountid);
        @GET("el_launcher/readMessages2.php")
        Call<List<MessagesModel>> getMessage(@Query("reciever") String username);
    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonListview = (ListView) findViewById(R.id.listReadMessages);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dlab.sit.kmutt.ac.th/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        //final String AccountID = sharedPreferences.getString("AccountID", "");
        final String Username = sharedPreferences.getString("Username", "");

        APIService service = retrofit.create(APIService.class);
        Call<List<MessagesModel>> call = service.getMessage(Username);
        call.enqueue(new Callback<List<MessagesModel>>() {
            @Override
            public void onResponse(Call<List<MessagesModel>> call, Response<List<MessagesModel>> response) {
                final ArrayList<MessagesModel> exData = new ArrayList<MessagesModel>();
                for (MessagesModel obj : response.body()) {
                    exData.add(new MessagesModel(obj.getTopic(), obj.getMessage(), obj.getTimeSend(), obj.getDateSend()));
                }

                MessagesAdapter myAdapter = new MessagesAdapter(MessagesRead.this, exData);
                jsonListview.setAdapter(myAdapter);

                jsonListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(MessagesUnread.this,exData.get(position).getTopic(),Toast.LENGTH_SHORT).show();

                        // custom dialog
                        final Dialog dialog = new Dialog(MessagesRead.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message_view);

                        // set the custom dialog components - text, image and button
                        TextView topic = (TextView)dialog.findViewById(R.id.viewTopic);
                        topic.setText(exData.get(position).getTopic());

                        TextView message = (TextView)dialog.findViewById(R.id.viewMessage);
                        message.setText(exData.get(position).getMessage());

                        Button okay = (Button) dialog.findViewById(R.id.btnOkay);
                        // if button is clicked, close the custom dialog
                        okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        Button close = (Button) dialog.findViewById(R.id.btnCancel);
                        // if button is clicked, close the custom dialog
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<MessagesModel>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}