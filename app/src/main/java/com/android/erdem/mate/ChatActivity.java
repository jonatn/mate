package com.android.erdem.mate;



import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



public class ChatActivity extends AppCompatActivity {

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private TextView send;
    private EditText message;
    private TextView time;
    private Toolbar toolbar;

    public boolean side = false;

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

      chatArrayAdapter = new ChatArrayAdapter(this,R.layout.chat_left);
        listView.setAdapter(chatArrayAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        chatArrayAdapter.add(new ChatMessage(side,"Test")); // for test messages
        chatArrayAdapter.add(new ChatMessage(!side,"Test"));
    }
    private boolean sendChatMessage() {
        chatArrayAdapter.add(new ChatMessage(side, message.getText().toString()));
        message.setText("");
        //time.setText(DateUtils.formatDateTime(ChatActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME)); //this will change time at all messages
        //(also will crash app), don't use!!!

        //side = !side; //use for testing visuals
        return true;
    }

    }


