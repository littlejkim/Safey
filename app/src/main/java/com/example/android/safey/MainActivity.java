package com.example.android.safey;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start Login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
