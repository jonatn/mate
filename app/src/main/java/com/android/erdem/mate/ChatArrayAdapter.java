package com.android.erdem.mate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private TextView time;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private Context context;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ChatMessage chatMessageObj = getItem(position);
// Inflate the layout according to the view type
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (chatMessageObj.left) {
                v = inflater.inflate(R.layout.chat_right, parent, false);
            }
            else {
                v = inflater.inflate(R.layout.chat_left, parent, false);
            }
//

        TextView chatText = (TextView) v.findViewById(R.id.chat_msg);
        TextView time = (TextView) v.findViewById(R.id.chat_time);
       chatText.setText(chatMessageObj.message);
        time.setText(chatMessageObj.time);
        return v;
    }
}
