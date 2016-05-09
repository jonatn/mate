package com.android.erdem.mate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class WaitActivity extends AppCompatActivity {

    Thread thread;
    boolean isActive = true;
    boolean isFinished = false;
    boolean isMatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);


        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String string = bundle.getString("myKey");

                AlertDialog.Builder builder = new AlertDialog.Builder(WaitActivity.this);
                builder.setMessage("in handleMsg().")
                        .setNegativeButton("Okay", null)
                        .create()
                        .show();

                Intent intent = new Intent(WaitActivity.this, MatchedWith.class);

                // use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pi = PendingIntent.getActivity(WaitActivity.this, 0, intent, 0);

                String notific;
                if (isMatched) {
                    notific = "YOU'RE MATCHED!!!";
                } else {
                    notific = "Not Matched... Try your luck another time :)";
                }

                Resources r = getResources();
                Notification notification = new NotificationCompat.Builder(WaitActivity.this)
                        .setTicker("TICKER")
                        .setSmallIcon(android.R.drawable.ic_menu_report_image)
                        .setContentTitle("MATE quiz ended")
                        .setContentText(notific)
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification);

                AlertDialog.Builder builder2 = new AlertDialog.Builder(WaitActivity.this);
                builder2.setMessage(string)
                        .setNegativeButton("Okay", null)
                        .create()
                        .show();
            }
        };

        Runnable runnable = new Runnable() {
            public void run() {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                isMatched = jsonResponse.getBoolean("result");
                                isFinished = jsonResponse.getBoolean("finished");
                                Message msg = handler.obtainMessage();



                                Bundle bundle = new Bundle();
                    /*SimpleDateFormat dateformat =
                            new SimpleDateFormat("HH:mm:ss MM/dd/yyyy",
                                    Locale.US);
                    String dateString =
                            dateformat.format(new Date());*/
                                if(isFinished) {

                                    ProfileInfo.partnerName = jsonResponse.getString("name");
                                    String strImage = jsonResponse.getString("image");

                                    byte[] imageBytes = Base64.decode(strImage, Base64.DEFAULT);
                                    ProfileInfo.partnerImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

                                    bundle.putString("myKey", "JUST A TEST");
                                    msg.setData(bundle);
                                    handler.sendMessage(msg);
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WaitActivity.this);
                                builder.setMessage("Fatal error. Please contact developer.")
                                        .setNegativeButton("Okay", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(WaitActivity.this);
                            builder.setMessage("Server Connection Failed. Have another try or contact developer.")
                                    .setNegativeButton("Okay", null)
                                    .create()
                                    .show();
                            e.printStackTrace();
                        }
                    }
                };

                CheckResultRequest checkResultRequest = new CheckResultRequest(ProfileInfo.quizID, ProfileInfo.id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WaitActivity.this);

                while (isActive) {
                    queue.add(checkResultRequest);
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void onStop() {
        //isActive = false;
        super.onStop();
    }
}
