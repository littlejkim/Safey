package com.example.android.safey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by littlejkim on 05/04/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    // Address for PHP file for User login
    String serverAddress = "http://ec2-18-188-77-130.us-east-2.compute.amazonaws.com/UserLogin.php";
    private static final int REQUEST_SIGNUP = 0;

    // Fields for email, password and signup button
    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    String email;
    String password;

    HashMap<String,String> hashMap = new HashMap<>(); // Hashmap for authentication
    UserDataParsing httpParse = new UserDataParsing();
    String finalResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assign view to model
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);


        // OnClickListeners for two buttons (login and signup)
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    // login method
    public void login() {
        // Email and password must match standards (@, number of characters)
        if (!validation()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        // Attempting to log in
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in..");
        progressDialog.show();

        email = emailText.getText().toString();
        password = passwordText.getText().toString();


        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Correct info")){ // Correct info is output from PHP file (echo)
                    Intent intent = new Intent(LoginActivity.this, BasicActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                else {
                    onLoginFailed();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                // Put user info in hashmap and send to server using httpparse class
                hashMap.put("email", email);
                hashMap.put("password", password);
                finalResult = httpParse.postRequest(hashMap, serverAddress);
                return finalResult;
            }
        }
        UserLoginClass login = new UserLoginClass();
        login.execute();
    }

    // Do nothing when back button is pressed
    @Override
    public void onBackPressed() {
    }

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validation() {
        boolean valid = true;
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Invalid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            passwordText.setError("Password should be between 4 and 12 characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
