package com.android.erdem.mate;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class AnswererQuestionerActivity extends AppCompatActivity {

    private TextView text1, text2, play;
    private ImageView image;



    private void Answerer()
    {
            text1.setText("You are an ");
            text2.setText("answerer !");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answerer_questioner);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        text1 = (TextView) findViewById(R.id.answerer_questioner_text1);
        text2 = (TextView) findViewById(R.id.answerer_questioner_text2);
        play = (TextView) findViewById(R.id.answerer_questioner_play);
        image = (ImageView) findViewById(R.id.answerer_questioner_avatar);

        //if that person is answerer than call Answerer function, else no need to do anything.
        //also set image according to that person's profile image.




        if(!ProfileInfo.isQuestioner)
        {
            Answerer();
        }




        /*
                if (ProfileInfo.isQuestioner)
            message = "Congratulations! You're questioner";
        else
            message = "You're answerer";
         */


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnswererQuestionerActivity.this, CardgameActivity.class);
                startActivity(i);
            }
        });
    }
}
