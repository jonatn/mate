package com.android.erdem.mate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<String> {
    public boolean messagetype; //if messagetype is 0 than consider as that user is sending message,
    // else it is the other user sending message(the message comes from server)
    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ChatAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (messagetype) ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int type = getItemViewType(position);
        if (v == null) {
// Inflate the layout according to the view type
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (type == 0) {
// Inflate the layout with image
                v = inflater.inflate(R.layout.chat_left, parent, false);
            }
            else {
                v = inflater.inflate(R.layout.chat_right, parent, false);
            }
        }
//

        TextView message = (TextView) v.findViewById(R.id.chat_msg);
        TextView time = (TextView) v.findViewById(R.id.chat_msg_time);

       // Contact c = contactList.get(position);

        //add the code related to if chat is being sent by that device or other one.


        return v;
    }
}
