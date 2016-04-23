package com.android.erdem.mate;

/**
 * Created by jon on 19.04.2016.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://cs450mate.esy.es/register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String mail, String password, String sex, int age, String image, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("mail", mail);
        params.put("password", password);
        params.put("sex", sex);
        params.put("age", age + "");
        params.put("image", image);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}