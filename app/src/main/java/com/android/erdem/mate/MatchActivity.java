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
    private ImageView image1, image2, image3, image4;

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
        match = (TextView)findViewById(R.id.matchactivity_match);
        image1 = (ImageView)findViewById(R.id.matchactivity_profile_image1);
        image2 = (ImageView)findViewById(R.id.matchactivity_profile_image2);
        image3 = (ImageView)findViewById(R.id.matchactivity_profile_image3);
        image4 = (ImageView)findViewById(R.id.matchactivity_profile_image4);

        //need to add images from server also instead of empty images to here

        match.setOnClickListener(new View.OnClickListener()
                                 {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i = new Intent(MatchActivity.this, CardgameActivity.class);
                                         startActivity(i);
                                     }
                                 }
        );
    }


}
