package com.android.erdem.mate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchActivity extends AppCompatActivity {

    private TextView peopleamount, match;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        peopleamount = (TextView)findViewById(R.id.matchactivity_people_amount_text);
        match = (TextView)findViewById(R.id.matchactivity_match_text);
        image1 = (ImageView)findViewById(R.id.matchactivity_avatar_image1);
        image2 = (ImageView)findViewById(R.id.matchactivity_avatar_image2);
        image3 = (ImageView)findViewById(R.id.matchactivity_avatar_image3);
        image4 = (ImageView)findViewById(R.id.matchactivity_avatar_image4);
        image5 = (ImageView)findViewById(R.id.matchactivity_avatar_image5);
        image6 = (ImageView)findViewById(R.id.matchactivity_avatar_image6);
        image7 = (ImageView)findViewById(R.id.matchactivity_avatar_image7);
        image8 = (ImageView)findViewById(R.id.matchactivity_avatar_image8);
        image9 = (ImageView)findViewById(R.id.matchactivity_avatar_image9);
        image10 = (ImageView)findViewById(R.id.matchactivity_avatar_image10);
        image11 = (ImageView)findViewById(R.id.matchactivity_avatar_image11);
        image12 = (ImageView)findViewById(R.id.matchactivity_avatar_image12);

        //need to add images from server also instead of empty images to here

        match.setOnClickListener(new View.OnClickListener()
                                 {
                                     @Override
                                     public void onClick(View v) {

                                         Intent i = new Intent(MatchActivity.this, MatchSelectActivity.class);
                                         startActivity(i);
                                     }
                                 }
        );
    }


}
