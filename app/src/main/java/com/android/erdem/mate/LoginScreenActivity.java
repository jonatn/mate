package com.android.erdem.mate;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreenActivity extends AppCompatActivity {

    private TextView username, password, signin, signin_facebook, forgotpassword;
    //add checkbox and forgot your password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        signin = (TextView)findViewById(R.id.loginscreen_signin_text);
        username = (TextView)findViewById(R.id.loginscreen_username_text);
        password = (TextView)findViewById(R.id.loginscreen_password_text);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());
        forgotpassword = (TextView)findViewById(R.id.loginscreen_forgot_password_text);
        signin_facebook=(TextView)findViewById(R.id.loginscreen_signinwithfacebook_text);

        // For DB
        // final EditText etUsername = (EditText) findViewById(R.id.loginscreen_username_text);
        // final EditText etPassword = (EditText) findViewById(R.id.loginscreen_password_text);

        signin_facebook.setOnClickListener(new View.OnClickListener()
                                           {
                                               @Override
                                               public void onClick(View v) {
                                                   Toast.makeText(LoginScreenActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                                               }
                                           }
        );

        signin.setOnClickListener(new View.OnClickListener()
                                           {
                                               @Override
                                               public void onClick(View v) {

                                                   //progress.show();

                                                   final String strUsername = username.getText().toString();
                                                   final String strPassword = password.getText().toString();

                                                   // Response received from the server
                                                   Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                       @Override
                                                       public void onResponse(String response) {
                                                           try {
                                                               JSONObject jsonResponse = new JSONObject(response);
                                                               boolean success = jsonResponse.getBoolean("success");

                                                               if (success) {

                                                                   ProfileInfo.id = jsonResponse.getInt("userid");
                                                                   String name = jsonResponse.getString("username");
                                                                   int age = jsonResponse.getInt("age");
                                                                   ProfileInfo.username = name;
                                                                   ProfileInfo.age = age;
                                                                   ProfileInfo.sex = jsonResponse.getString("sex");

                                                                   Toast.makeText(LoginScreenActivity.this, "Welcome " + name + "!", Toast.LENGTH_LONG).show();

                                                                   // FOR SIMULATING NOTIF. JUST ENABLE COMMAND AND GO TO WAIT SCREEN
                                                                   // ProfileInfo.quizID = 125;
                                                                   Intent intent = new Intent(LoginScreenActivity.this, GPSPermission.class);

                                                                   // intent.putExtra("name", name);
                                                                   // intent.putExtra("age", age);
                                                                   // intent.putExtra("username", strUsername);
                                                                   LoginScreenActivity.this.startActivity(intent);
                                                               } else {
                                                                   AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreenActivity.this);
                                                                   builder.setMessage("Login Failed. Invalid username or password.")
                                                                           .setNegativeButton("Retry", null)
                                                                           .create()
                                                                           .show();
                                                               }

                                                           } catch (JSONException e) {
                                                               AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreenActivity.this);
                                                               builder.setMessage("Server Connection Failed. Have another try or contact developer." + ProfileInfo.id)
                                                                       .setNegativeButton("Okay", null)
                                                                       .create()
                                                                       .show();
                                                               e.printStackTrace();
                                                           }
                                                       }
                                                   };

                                                   LoginRequest loginRequest = new LoginRequest(strUsername, strPassword, responseListener);
                                                   RequestQueue queue = Volley.newRequestQueue(LoginScreenActivity.this);
                                                   queue.add(loginRequest);

                                                   //progress.dismiss();
                                               }
                                           }
        );

    }

}
