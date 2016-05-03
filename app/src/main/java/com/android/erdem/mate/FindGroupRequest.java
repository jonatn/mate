package com.android.erdem.mate;

/**
 * Created by jon on 02.05.2016.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindGroupRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://cs450mate.esy.es/findquiz.php";
    private Map<String, String> params;

    public FindGroupRequest(int userID, String userSex, String findSex, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("userid", userID + "");
        params.put("usersex", userSex);
        params.put("findsex", findSex);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}