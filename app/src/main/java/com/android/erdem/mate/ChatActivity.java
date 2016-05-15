package com.android.erdem.mate;


import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private ListView listView;
    private TextView send;
    private EditText message;



    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView)findViewById(R.id.chat_listView);
        message = (EditText)findViewById(R.id.chatscreen_send_msg_text);
        send = (TextView)findViewById(R.id.chatscreen_send);

        toolbar = (Toolbar) findViewById(R.id.chat_actionbar);
        setSupportActionBar(toolbar);//setting toolbar as actionbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      ChatAdapter ca = new ChatAdapter(this,R.layout.activity_chat);
        listView.setAdapter(ca);

    }

}
