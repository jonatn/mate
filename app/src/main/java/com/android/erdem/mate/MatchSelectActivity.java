package com.android.erdem.mate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MatchSelectActivity extends AppCompatActivity  implements View.OnClickListener {

    private TextView female, male, unspecified;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_select);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        female = (TextView)findViewById(R.id.matchselect_female);
        male = (TextView)findViewById(R.id.matchselect_male);
        unspecified = (TextView)findViewById(R.id.matchselect_unspecified);

        female.setOnClickListener(this);
        male.setOnClickListener(this);
        unspecified.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

                                         Intent i = new Intent(MatchSelectActivity.this, MatchActivity.class);
                                         startActivity(i);
    }
}