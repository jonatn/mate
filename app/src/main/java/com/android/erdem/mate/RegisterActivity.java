package com.android.erdem.mate;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private TextView username, emailaddress, password, sex, age, signup, signup_facebook;
   // private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);




        username = (TextView)findViewById(R.id.registerscreen_username_text);
        emailaddress = (TextView)findViewById(R.id.registerscreen_email_text);
        password = (TextView)findViewById(R.id.registerscreen_password_text);
        sex = (TextView)findViewById(R.id.registerscreen_sex_text);
        age = (TextView)findViewById(R.id.registerscreen_age_text);
        signup =(TextView)findViewById(R.id.registerscreen_signup_text);
        signup_facebook =(TextView)findViewById(R.id.registerscreen_signupwithfacebook_text);


        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        // DATA-Binding for Data Base
        //final EditText etUsername = (EditText) findViewById(R.id.registerscreen_username_text);
        //final EditText etMail = (EditText) findViewById(R.id.registerscreen_email_text);
        //final EditText etPassword = (EditText) findViewById(R.id.registerscreen_password_text);
        //final EditText etSex = (EditText) findViewById(R.id.registerscreen_sex_text);
        //final EditText etAge = (EditText) findViewById(R.id.registerscreen_age_text);
        //final TextView tvRegister = (TextView) findViewById(R.id.registerscreen_signup_text);
        //final TextView btRegister = (TextView) findViewById(R.id.registerscreen_signup_text);


        signup_facebook.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v) {
                                         Toast.makeText(RegisterActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                                      }
                                  }
        );

        signup.setOnClickListener(new View.OnClickListener()
                                           {
                                               @Override
                                               public void onClick(View v) {
                                                   final String strUsername = username.getText().toString();
                                                   final String strMail = emailaddress.getText().toString();
                                                   final String strPassword = password.getText().toString();
                                                   final String strSex = sex.getText().toString().toLowerCase();
                                                   final int iAge = Integer.parseInt(age.getText().toString());

                                                   Intent intent = new Intent(RegisterActivity.this, ProfilePicture.class);
                                                   intent.putExtra("username", strUsername);
                                                   intent.putExtra("mail", strMail);
                                                   intent.putExtra("password", strPassword);
                                                   intent.putExtra("sex", strSex);
                                                   intent.putExtra("age", iAge);

                                                   startActivity(intent);



                                               }
                                           }
        );


    }
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(RegisterActivity.this, "Test Message!", Toast.LENGTH_SHORT).show();
    } */
}
