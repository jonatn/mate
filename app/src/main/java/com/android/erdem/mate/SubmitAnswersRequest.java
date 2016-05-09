package com.android.erdem.mate;

/**
 * Created by jon on 02.05.2016.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class SubmitAnswersRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://cs450mate.esy.es/submitanswers.php";
    private Map<String, String> params;

    public SubmitAnswersRequest(int quizID, int userID, JSONArray questionsID, JSONArray answersID, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("quizid", quizID + "");
        params.put("userid", userID + "");
        params.put("questionsid", questionsID.toString());
        params.put("answersid", answersID.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}