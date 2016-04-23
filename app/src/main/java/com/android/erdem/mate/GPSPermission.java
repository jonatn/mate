package com.android.erdem.mate;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GPSPermission extends AppCompatActivity {

   private TextView allow, skipnow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpspermission);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        allow = (TextView)findViewById(R.id.gpsscreen_allow_text);
        skipnow = (TextView)findViewById(R.id.gpsscreen_skipnow_text);

        allow.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v) {
                                          Intent i = new Intent(GPSPermission.this, ProfilePicture.class);
                                          startActivity(i);
                                      }
                                  }
        );
    }
}
