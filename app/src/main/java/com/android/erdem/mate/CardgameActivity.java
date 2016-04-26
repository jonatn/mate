package com.android.erdem.mate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CardgameActivity extends AppCompatActivity {

    private TextView question, answer1, answer2, answer3, answer4, quiznumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardgame);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        question = (TextView)findViewById(R.id.cardgame_question);
        answer1 = (TextView)findViewById(R.id.cardgame_answer_1);
        answer2 = (TextView)findViewById(R.id.cardgame_answer_2);
        answer3 = (TextView)findViewById(R.id.cardgame_answer_3);
        answer4 = (TextView)findViewById(R.id.cardgame_answer_4);



    }
}
