package com.android.erdem.mate;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ProfilePicture extends AppCompatActivity implements View.OnClickListener {

    private TextView selectfromlibrary, next;
    private ImageView profilepicture;
    private static int RESULT_LOAD_IMAGE = 1;


   // @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);

        selectfromlibrary = (TextView)findViewById(R.id.profilepicture_select_from_library);
        next = (TextView)findViewById(R.id.profilepicture_next);
        profilepicture =(ImageView)findViewById(R.id.profilepicture_picture);

        selectfromlibrary.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profilepicture_select_from_library:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.profilepicture_next:

                // Get Data from previous Window
                Intent intent = getIntent();
                String strUsername = intent.getStringExtra("username");
                String strMail = intent.getStringExtra("mail");
                String strPassword = intent.getStringExtra("password");
                String strSex = intent.getStringExtra("sex");
                int iAge = intent.getIntExtra("age", -1);

                // encode image as string
                Bitmap image = ((BitmapDrawable) profilepicture.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                int length = encodedImage.length();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePicture.this);
                builder.setMessage("Number of chars in encoded-Image (Width: "+ image.getWidth() + ", Height: " + image.getHeight() + ") string is " + length)
                        .setNegativeButton("Retry", null)
                        .create().show();

                // Prepare Responce Process
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePicture.this);
                                builder.setMessage("Registration successful")
                                        .setNegativeButton("END DEMO", null)
                                        .create().show();
                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePicture.this);
                                builder.setMessage("Registration failed")
                                        .setNegativeButton("Retry", null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePicture.this);
                            builder.setMessage("Fatal error")
                                    .setNegativeButton("Retry", null)
                                    .create().show();
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(strUsername,strMail,strPassword, strSex, iAge, encodedImage,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProfilePicture.this);
                queue.add(registerRequest);

                // INCL. RESPONSE LISTENER
                /*Response.Listener<Bitmap> responseListener = new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePicture.this);
                                builder.setMessage("Image Upload successful!")
                                        .setNegativeButton("OK", null)
                                        .create().show();
                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePicture.this);
                                builder.setMessage("Image Upload failed")
                                        .setNegativeButton("Retry", null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePicture.this);
                            builder.setMessage("Fatal error")
                                    .setNegativeButton("Retry", null)
                                    .create().show();
                            e.printStackTrace();
                        }

                    }
                };
                // USE RESPONSE LISTENER PLUS IMAGEREQUEST TO UPLOAD IMAGE
                ImageRequest imageRequest = new ImageRequest("http://cs450mate.esy.es/savepicture.php", );*/
                break;
        }
    }

    private class UploadImage extends AsyncTask<Void, Void, Void>{

        Bitmap image;
        String name;

        public UploadImage(Bitmap image, String name) {
            this.image = image;
            this.name = name;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            //ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();

            profilepicture.setImageDrawable(null);
            Crop.pickImage(this);
            //Crop.of(selectedImage, selectedImage).asSquare().start(this);
            /*String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
           // ImageView imageView = (ImageView) findViewById(R.id.imgView);
            profilepicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/
        }
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().withMaxSize(512, 512).start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            profilepicture.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_select) {
            resultView.setImageDrawable(null);
            Crop.pickImage(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
