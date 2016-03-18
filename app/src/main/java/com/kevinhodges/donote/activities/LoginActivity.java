package com.kevinhodges.donote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kevinhodges.donote.MainActivity;
import com.kevinhodges.donote.R;
import com.kevinhodges.donote.utils.Constants;

import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Firebase firebase;
    private AutoCompleteTextView emailInput;
    private EditText passwordInput;
    private String emailString;
    private String passwordString;
    private Button enterButton;
    private Button loginButton;
    private Button signupButton;
    private Intent intentToMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create instace of Firebase
        firebase = new Firebase(Constants.FIREBASE_URL);

        // Intent to main activity
        intentToMainActivity = new Intent(LoginActivity.this, MainActivity.class);

        // If the user is auth then go to main activity
        if (firebase.getAuth() != null) {
            startActivity(intentToMainActivity);
        }

        /////////////////////////////////////////////////////////////
        emailInput = (AutoCompleteTextView) findViewById(R.id.ac_email);
        passwordInput = (EditText) findViewById(R.id.et_password);
        loginButton = (Button) findViewById(R.id.button_login);
        signupButton = (Button) findViewById(R.id.button_signup);
        ///////////////////////////////////////////////////////////

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = emailInput.getText().toString().toLowerCase();
                passwordString = passwordInput.getText().toString().toLowerCase();
                signUp(emailString, passwordString);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = emailInput.getText().toString().toLowerCase();
                passwordString = passwordInput.getText().toString().toLowerCase();
                login(emailString, passwordString);
            }
        });
    }

    public void signUp(final String email, final String password) {

        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Toast.makeText(LoginActivity.this, "You have successfully signed up and can now log in!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(LoginActivity.this, "It looks like there was a problem while signing up", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Sign up error " + firebaseError);
                Log.d(TAG, "Sign up error " + emailString);
                Log.d(TAG, "Sign up error " + passwordString);

            }
        });

    }

    public void login(String email, String password) {

        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Toast.makeText(LoginActivity.this, "You have logged into Do Note!", Toast.LENGTH_LONG).show();
                startActivity(intentToMainActivity);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(LoginActivity.this, "It looks like there was a problem during login", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Login error " + firebaseError);
                Log.d(TAG, "Login error " + emailString);
                Log.d(TAG, "Login error " + passwordString);
            }
        });
    }

    public void changePassword(String email, String oldPassword, String newPassword) {

        firebase.changePassword(email, oldPassword, newPassword, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                // passwordInput changed
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // error encountered
            }
        });
    }

    public void resetPassword(String email) {

        firebase.resetPassword(email, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "Check your emailInput for passwordInput reset instructions", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(LoginActivity.this, "The emailInput you have entered does not appear to be valid. Please try again.", Toast.LENGTH_LONG).show();
            }
        });

    }


}

