package com.example.android.safey;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UploadActivity extends AppCompatActivity {

    String serverAddress = "http://ec2-18-188-77-130.us-east-2.compute.amazonaws.com/upload_report.php";

    ToggleButton tb;
    Button b1;
    String type = "Announcement";
    EditText t1;

    int results = 0;
    HashMap<String, String> hashMap = new HashMap<>();

    //Report info
    String comment;
    String longitude;
    String latitude;
    String date;
    String finalResult;
    String email;

    UserDataParsing httpParse = new UserDataParsing();
    private FusedLocationProviderClient mFusedLocationClient;
    Date c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getStringExtra("email");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_upload);

        // Button for type of report
        tb = (ToggleButton) findViewById(R.id.toggleButton);
        tb.toggle();
        b1 = (Button) findViewById(R.id.submit_report);
        t1 = (EditText) findViewById(R.id.comment);

        // upload button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on = ((ToggleButton) v).isChecked();

                if (on) {
                    type = "Announcement";
                    tb.setTextOn(type);
                } else {
                    type = "Emergency";
                    tb.setTextOff(type);
                }
            }
        });
    }

    public void upload() {
        //Get Date of Report
        c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        // checks user permission for app
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Get location
                        if (location != null) {
                            // save location
                            latitude = String.valueOf(location.getLatitude());
                            longitude = String.valueOf(location.getLongitude());
                            Log.d("Location", latitude + longitude);
                        }
                    }
                });
        final ProgressDialog progressDialog = new ProgressDialog(UploadActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submitting Report");
        progressDialog.show();

        // save comment and date
        comment = t1.getText().toString();
        date = df.format(c);


        class UploadInnerClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(UploadActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                if(httpResponseMsg.equalsIgnoreCase("Success")){
                    results = 1;
                    Intent intent = new Intent(UploadActivity.this, BasicActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    progressDialog.dismiss();
                    finish();
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                // put data into hashmap to send to server
                hashMap.put("comment", comment);
                hashMap.put("longitude", longitude);
                hashMap.put("latitude", latitude);
                hashMap.put("date", date);
                hashMap.put("email", email);
                finalResult = httpParse.postRequest(hashMap, serverAddress);
                return finalResult;
            }
        }
        // Wait 1 second till upload (server response time)
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                UploadInnerClass uploadReport = new UploadInnerClass();
                uploadReport.execute();
            }
        }, 1000);

    }

    // do nothing on back pressed
    @Override
    public void onBackPressed() {

    }

}
