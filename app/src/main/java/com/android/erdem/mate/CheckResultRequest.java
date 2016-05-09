package com.android.erdem.mate;

/**
 * Created by jon on 08.05.2016.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CheckResultRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://cs450mate.esy.es/checkresult.php";
    private Map<String, String> params;

    public CheckResultRequest(int quizID, int userID, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("quizid", quizID + "");
        params.put("userid", userID + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}