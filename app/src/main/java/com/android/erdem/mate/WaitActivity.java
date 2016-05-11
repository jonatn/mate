package com.android.erdem.mate;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



import static java.lang.Thread.sleep;

public class WaitActivity extends AppCompatActivity {

    Thread thread;
    boolean isActive = true;
    boolean isFinished = false;
    boolean isMatched;

    ImageView loading;
    TextView timer;
    CountDownTimer countDownTimer;

    public void rotateAnimation ()
    {
        RotateAnimation rotateAnimation1 = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation1.setInterpolator(new LinearInterpolator());
        rotateAnimation1.setDuration(16000);
        rotateAnimation1.setRepeatCount(Animation.INFINITE);
        loading.startAnimation(rotateAnimation1);
    }

    public void updateTimer (int secondsLeft)
    {
        int hours = secondsLeft / 3600;
        int minutes = ((secondsLeft - (hours * 3600)) / 60);
        int seconds = secondsLeft - ((hours * 3600) + (minutes * 60));

        String hourString = Integer.toString(hours);
        String minString = Integer.toString(minutes);
        String secString = Integer.toString(seconds);

        if (hours <=9)
        {
            hourString = "0" + hourString;
        }
        if (minutes <=9)
        {
            minString = "0" + minString;
        }
        if (seconds <=9)
        {
            secString = "0" + secString;
        }

        timer.setText(hourString+":"+minString+":"+secString);
    }

    public void controlTimer(View view)
    {
        countDownTimer = new CountDownTimer(86400000,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                updateTimer((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish()
            {
                //end of countdown
            }
        };
        countDownTimer.start();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        loading = (ImageView) findViewById(R.id.waitactivity_loading);
        timer = (TextView) findViewById(R.id.waitactivity_timer);
        View v = timer;
        controlTimer(v);
       //loading.setDrawingCacheEnabled(true);
        rotateAnimation();




        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String string = bundle.getString("myKey");

                /*AlertDialog.Builder builder = new AlertDialog.Builder(WaitActivity.this);
                builder.setMessage("in handleMsg().")
                        .setNegativeButton("Okay", null)
                        .create()
                        .show();*/

                Intent intent;



                String notific;
                if (isMatched) {
                    notific = "YOU'RE MATCHED!!!";
                    intent = new Intent(WaitActivity.this, MatchedWith.class);
                } else {
                    notific = "Not Matched... Try your luck another time :)";
                    intent = new Intent(WaitActivity.this, MatchSelectActivity.class);
                }

                // use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pi = PendingIntent.getActivity(WaitActivity.this, 0, intent, 0);

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

                /*AlertDialog.Builder builder2 = new AlertDialog.Builder(WaitActivity.this);
                builder2.setMessage(string)
                        .setNegativeButton("Okay", null)
                        .create()
                        .show();*/
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
                                    if(isMatched) {
                                        ProfileInfo.partnerName = jsonResponse.getString("name");
                                        String strImage = jsonResponse.getString("image");

                                        byte[] imageBytes = Base64.decode(strImage, Base64.DEFAULT);
                                        ProfileInfo.partnerImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                    }
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
