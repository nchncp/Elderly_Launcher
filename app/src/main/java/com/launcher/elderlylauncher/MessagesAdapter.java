package com.launcher.elderlylauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nicha on 10/27/16.
 */

public class MessagesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<MessagesModel> mArrayListMessage;

    public MessagesAdapter(Context context, ArrayList<MessagesModel> list) {
        mContext = context;
        mArrayListMessage = list;
    }

    @Override
    public int getCount() {
        return mArrayListMessage.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mArrayListMessage.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        MessagesModel entry = mArrayListMessage.get(arg0);

        if(arg1 == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            arg1 = inflater.inflate(R.layout.messages_item, null);
        }

        TextView topic = (TextView)arg1.findViewById(R.id.topic);
        topic.setText(entry.getTopic());

        TextView message = (TextView)arg1.findViewById(R.id.message);
        message.setText(entry.getMessage());

        TextView timeSend = (TextView)arg1.findViewById(R.id.timeSend);
        timeSend.setText(entry.getTimeSend());

        TextView dateSend = (TextView)arg1.findViewById(R.id.dateSend);
        dateSend.setText(entry.getDateSend());

        return arg1;
    }
}
