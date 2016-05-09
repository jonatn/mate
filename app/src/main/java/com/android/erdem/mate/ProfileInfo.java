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

    public static JSONArray questionsID, answers1ID, answers2ID, answers3ID, answers4ID;
    public static JSONArray questions, answers1, answers2, answers3, answers4;

    public static JSONArray selectedAnswersID;

    public static int questionnr;

    public static String partnerName;
    public static Bitmap partnerImage;
}
