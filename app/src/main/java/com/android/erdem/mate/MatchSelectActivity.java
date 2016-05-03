package com.android.erdem.mate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
        // ##### I QUIZ #####
        switch (v.getId()) {
            case R.id.matchselect_female:
                findGroup(ProfileInfo.sex, "female");
                break;
            case R.id.matchselect_male:
                findGroup(ProfileInfo.sex, "male");
                break;
            case R.id.matchselect_unspecified:
                findGroup("undefined","undefined");
                break;
        }

        //Intent i = new Intent(MatchSelectActivity.this, CardgameActivity.class);
        //startActivity(i);
    }

    // Input: (m)ale, (f)emale, (u)ndefined
    private void findGroup(String userSex, String findSex) {

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        ProfileInfo.quizID = jsonResponse.getInt("quizid");
                        ProfileInfo.isQuestioner = jsonResponse.getBoolean("isquestioner");
                        String str = jsonResponse.getJSONArray("answers4").getString(3);

                        AlertDialog.Builder builder = new AlertDialog.Builder(MatchSelectActivity.this);
                        String message;
                        if(ProfileInfo.isQuestioner)
                            message = "Congratulations! You're questioner" + str;
                        else
                            message = "You're answerer";

                        builder.setMessage(message)
                                .setNegativeButton("Continue", null)
                                .create()
                                .show();

                        Intent intent = new Intent(MatchSelectActivity.this, CardgameActivity.class);

                        MatchSelectActivity.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MatchSelectActivity.this);
                        builder.setMessage("Probably Syntax error in PHP file. Please contact developer.")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MatchSelectActivity.this);
                    builder.setMessage("Server Connection Failed. Have another try or contact developer.")
                            .setNegativeButton("Okay", null)
                            .create()
                            .show();
                    e.printStackTrace();
                }
            }
        };

        FindGroupRequest findGroupRequest = new FindGroupRequest(ProfileInfo.id, userSex, findSex, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MatchSelectActivity.this);
        queue.add(findGroupRequest);
    }
    private void startMatching(String constellation){

    }
}
