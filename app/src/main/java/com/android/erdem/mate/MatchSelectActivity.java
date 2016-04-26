package com.android.erdem.mate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MatchSelectActivity extends AppCompatActivity {

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
    }


}
