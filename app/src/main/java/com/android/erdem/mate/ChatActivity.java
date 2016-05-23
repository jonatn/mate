package com.android.erdem.mate;



import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
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

    public boolean side = false; //side false = left bubble

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView)findViewById(R.id.chat_listView);
        message = (EditText)findViewById(R.id.chatscreen_send_msg_text);
        send = (TextView)findViewById(R.id.chatscreen_send);
        toolbar = (Toolbar) findViewById(R.id.chat_actionbar);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        setSupportActionBar(toolbar);//setting toolbar as actionbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      chatArrayAdapter = new ChatArrayAdapter(this,R.layout.chat_left);
        listView.setAdapter(chatArrayAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
                Handler hand = new Handler();
                hand.postDelayed(new Runnable() {
                    public void run() {
                        chatArrayAdapter.add(new ChatMessage(!side,"Haha :D \nWhat a BEAUTIFUL MIND ;)"));
                        // Actions to do after 10 seconds
                    }
                }, 5000);

            }
        });
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });


        /*
        chatArrayAdapter.add(new ChatMessage(side,"Test adasdasfasfa" +
                "aafasfasfasfasfasfasf " +
                "asfasfasfasfasfafafsafafafaf " +
                "asfafasfafafasfasf")); // for test messages
        chatArrayAdapter.add(new ChatMessage(!side,"Test adasdasfasfa" +
                "aafasfasfasfasfasfasf " +
                "asfasfasfasfasfafafsafafafaf " +
                "asfafasfafafasfasf"));
                */
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


