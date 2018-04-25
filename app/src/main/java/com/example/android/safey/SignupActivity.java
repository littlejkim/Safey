package com.example.android.safey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by littlejkim on 05/04/2018.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


    EditText nameText;
    EditText addressText;
    EditText emailText;
    EditText mobileText;
    EditText passwordText;
    EditText reEnterPasswordText;
    Button signupButton;
    TextView loginLink;
    String name;
    String address;
    String email;
    String mobile;
    String password;
    String reEnterPassword;
    int results = 0;

    String serverAddress = "http://ec2-18-188-77-130.us-east-2.compute.amazonaws.com/UserRegistration.php";
    HashMap<String, String> hashMap = new HashMap<>();
    UserDataParsing httpParse = new UserDataParsing();
    String finalResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameText = (EditText) findViewById(R.id.input_name);
        addressText = (EditText) findViewById(R.id.input_address);
        emailText = (EditText) findViewById(R.id.input_email);
        mobileText = (EditText) findViewById(R.id.input_mobile);
        passwordText = (EditText) findViewById(R.id.input_password);
        reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), BasicActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        name = nameText.getText().toString();
        address = addressText.getText().toString();
        email = emailText.getText().toString();
        mobile = mobileText.getText().toString();
        password = passwordText.getText().toString();
        reEnterPassword = reEnterPasswordText.getText().toString();

       class RegistrationInnerClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                if(httpResponseMsg.equalsIgnoreCase("Success")){

                    results = 1;
                    Intent intent = new Intent(SignupActivity.this, BasicActivity.class);
                    intent.putExtra("email", email);
                    setResult(RESULT_OK);
                    startActivityForResult(intent, 1);
                    progressDialog.dismiss();
                    finish();
                } else {
                    onSignupFailed();
                    progressDialog.dismiss();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("name", name);
                hashMap.put("address", address);
                hashMap.put("email", email);
                hashMap.put("mobile", mobile);
                hashMap.put("password", password);
                finalResult = httpParse.postRequest(hashMap, serverAddress);
                return finalResult;
            }
        }

        RegistrationInnerClass registerUser = new RegistrationInnerClass();
        registerUser.execute();

    }



    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Account creation failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (address.isEmpty()) {
            addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            passwordText.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 15 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
    @Override
    public void onBackPressed() {


    }
}
