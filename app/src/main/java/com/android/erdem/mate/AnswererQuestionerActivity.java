package com.android.erdem.mate;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        text1 = (TextView) findViewById(R.id.answerer_questioner_text1);
        text2 = (TextView) findViewById(R.id.answerer_questioner_text2);
        play = (TextView) findViewById(R.id.answerer_questioner_play);
        image = (ImageView) findViewById(R.id.answerer_questioner_avatar);

        //if that person is answerer than call Answerer function, else no need to do anything.
        //also set image according to that person's profile image.

        setContentView(R.layout.activity_answerer_questioner);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

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

    }
}
