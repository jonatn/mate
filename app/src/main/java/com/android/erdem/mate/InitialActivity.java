    package com.android.erdem.mate;


    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.TextView;
    import android.widget.Toast;

    public class InitialActivity extends AppCompatActivity {

        private TextView signin, signup;
        private Toast toast;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_initial);



            signin = (TextView) findViewById(R.id.initial_sign_in_text);
            signup = (TextView) findViewById(R.id.initial_sign_up_text);


            signup.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v) {
                                              Intent i = new Intent(InitialActivity.this, RegisterActivity.class);
                                              startActivity(i);
                                          }
                                      }
            );

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(InitialActivity.this, LoginScreenActivity.class);
                    startActivity(i);
                }

            });
        }
    }
