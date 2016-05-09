package com.android.erdem.mate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by jon on 08.05.2016.
 */
public class AndroidThreadNotification extends Activity {

    BackgroundThread backgroundThread;
    Handler backgroundHandler;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundThread = new BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.con=AndroidThreadNotification.this;
        backgroundThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        boolean retry = true;
        backgroundThread.setRunning(false);

        while (retry) {
            try {
                backgroundThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public class BackgroundThread extends Thread {
        boolean running = false;
        final static String ACTION = "NotifyServiceAction";
        NotificationManager notificationManager;
        Notification myNotification;
        private static final int MY_NOTIFICATION_ID = 1;

        public Activity con;
        void setRunning(boolean b) {
            running = b;
        }

        @Override
        public synchronized void start() {
            // TODO Auto-generated method stub
            super.start();
            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        @Override
        public void run() {
            Looper.prepare();
            while (running) {
                try {
                    sleep(10000); //send notification in every 10sec.
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // prepare intent which is triggered if the
                // notification is selected

                Intent intent = new Intent(getBaseContext(), LoginRequest.class);
                // use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), (int) System.currentTimeMillis(), intent, 0);

                // build notification
                // the addAction re-use the same intent to keep the example short
                AlertDialog.Builder builder2 = new AlertDialog.Builder(con);
                builder2.setMessage("Fatal error. Please contact developer.")
                        .setNegativeButton("Okay", null)
                        .create()
                        .show();
            }
        }
    }
}
