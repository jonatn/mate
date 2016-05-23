package com.android.erdem.mate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchedWith extends AppCompatActivity {

    TextView startchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched_with);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        ImageView profilepicture =(ImageView)findViewById(R.id.imageView3);
        //profilepicture.setImageBitmap(ProfileInfo.partnerImage);

        startchat = (TextView) findViewById(R.id.matchedwith_start_chat);
        TextView textView = (TextView)findViewById(R.id.textView5);
        //textView.setText("You got matched with " + ProfileInfo.partnerName + "!");

        startchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchedWith.this, ChatActivity.class);
                startActivity(intent);
            }
        });

    }
}
