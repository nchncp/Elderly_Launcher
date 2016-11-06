package com.launcher.elderlylauncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nicha on 9/27/16.
 */

public class GalleryPhoto extends Activity {
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_photo);
    }

    public interface APIService {
        @GET("el_launcher/getPhotoPath.php")
        Call<List<GalleryPhotoModel>> getMessage(@Query("reciever") String username);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGridView = (GridView)findViewById(R.id.photoView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dlab.sit.kmutt.ac.th/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        final String reciever = sharedPreferences.getString("Username", "");

        APIService photoService = retrofit.create(APIService.class);
        Call<List<GalleryPhotoModel>> call = photoService.getMessage(reciever);
        call.enqueue(new Callback<List<GalleryPhotoModel>>() {
            @Override
            public void onResponse(Call<List<GalleryPhotoModel>> call, Response<List<GalleryPhotoModel>> response) {
                final ArrayList<GalleryPhotoModel> exPhoto = new ArrayList<GalleryPhotoModel>();
                for(GalleryPhotoModel obj : response.body()){
                    exPhoto.add(new GalleryPhotoModel(obj.getPhotoContentID(), obj.getPhotoTopic(), obj.getPhotoMessage(), obj.getPhotoPicturePath(), obj.getPhotoDateSend(), obj.getPhotoTimeSend()));
                }

                for(int position=0; position<exPhoto.size(); position++){
                    if(exPhoto.get(position).getPhotoPicturePath().equals("")){
                        exPhoto.remove(position);
                    }
                }

                GalleryPhotoAdapter myAdapter = new GalleryPhotoAdapter(GalleryPhoto.this, exPhoto);
                mGridView.setAdapter(myAdapter);

                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // custom dialog
                        final Dialog dialog = new Dialog(GalleryPhoto.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.gallery_photo_view);

                        // set the custom dialog components - text, image and button
                        ImageView image = (ImageView) dialog.findViewById(R.id.image);
                        Picasso.with(GalleryPhoto.this).load("http://dlab.sit.kmutt.ac.th/stayintouch/WebApplication/examples/web/upload_file/"+exPhoto.get(position).getPhotoPicturePath()).into(image);

                        Button close = (Button) dialog.findViewById(R.id.btnClose);
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
            public void onFailure(Call<List<GalleryPhotoModel>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


}