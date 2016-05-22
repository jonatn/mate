package com.android.erdem.mate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by erdem on 22.05.2016.
 */
public class ChatMessage {
    public boolean left;
    public String message;
    public String time;

    public ChatMessage(boolean left, String message)
    {
        super();
        this.left = left;
        this.message = message;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); //https://developer.android.com/reference/java/text/SimpleDateFormat.html for other time options
        String time = sdf.format(new Date());
        this.time = time;
    }
}
