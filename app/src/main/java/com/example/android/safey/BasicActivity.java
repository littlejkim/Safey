package com.example.android.safey;

/**
 * Created by littlejkim on 06/04/2018.
 */

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class BasicActivity extends AppCompatActivity {

    String serverAddress = "http://ec2-18-188-77-130.us-east-2.compute.amazonaws.com/data.php";
    Button upload;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getStringExtra("email");

        setContentView(R.layout.activity_main);
        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });
        // Create c~
        final RecyclerView rv = (RecyclerView) findViewById(R.id.mRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        DownloadReportData d = new DownloadReportData(BasicActivity.this, serverAddress, rv);
        d.execute();


    }

    @Override
    public void onBackPressed() {


    }

}