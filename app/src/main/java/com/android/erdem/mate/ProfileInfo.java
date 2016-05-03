package com.android.erdem.mate;

import android.graphics.Bitmap;

import org.json.JSONArray;

/**
 * Created by jon on 02.05.2016.
 */
public class ProfileInfo {
    public static int id;
    public static String username;
    public static String mail;
    public static String sex;
    public static int age;
    public static Bitmap image;

    public static int quizID;
    public static boolean isQuestioner;

    public static JSONArray questions, answers1, answers2, answers3, answers4;

    public static int questionnr;
}
