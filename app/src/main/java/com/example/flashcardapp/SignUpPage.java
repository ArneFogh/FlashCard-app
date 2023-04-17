package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Arne
public class SignUpPage extends AppCompatActivity {


    //I will write an "m" in front of all of the names to better know what belongs to what
    ProgressBar mprogressBarSignUp;
    private ImageView mgoToHomeFromSignUp;
    private EditText msignUpEmail, msignUpPassword;
    private RelativeLayout msignUp;
    private TextView mgoToMainActivity;
    private FirebaseAuth firebaseAuth;

//Arne
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        //This removes the action bar in the top of the screen
        getSupportActionBar().hide();

        mgoToHomeFromSignUp = findViewById(R.id.goToHomeFromForgotPassword);
        mprogressBarSignUp = findViewById(R.id.progressBarSignUp);
        msignUpEmail = findViewById(R.id.signUpEmail);
        msignUpPassword = findViewById(R.id.signUpPassword);
        msignUp = findViewById(R.id.signUp);
        mgoToMainActivity = findViewById(R.id.goToMainActivity);

        firebaseAuth = FirebaseAuth.getInstance();

        //When goToMainActivity is clicked, it will take you to MainActivity
        mgoToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Arne

        //When logo is clicked go to main activity
        mgoToHomeFromSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Arne
        //When sign up button is clicked
        msignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get input from the email edittext
                String mail = msignUpEmail.getText().toString().trim();

                //Get input from the password edittext
                String password = msignUpPassword.getText().toString().trim();

                //Making sure the customer have put in an email and a password
                if (mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();

                    //Making sure that the password is longer than 7 characters
                } else if (password.length() < 7) {
                    Toast.makeText(getApplicationContext(), "Password must be more than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else {

                    //Register account
                    firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //If register is successful this message will be shown
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            //If register isn't successful this message will be shown
                            else{
                                Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    //Arne

    //Send email verification
    private void sendEmailVerification(){
        //Get the user from the firebase database
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //If firebase user is not null then do the following
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Send toast showing this message
                    Toast.makeText(getApplicationContext(), "Verification email has been sent, verify it and login again", Toast.LENGTH_SHORT).show();
                    //When sign up is done, it will log you out, take you back to main activity for you to verify your email and now log in
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(SignUpPage.this, MainActivity.class));
                }
            });
        }

        //If mail haven't been verified this message will be shown
        else {
            Toast.makeText(getApplicationContext(), "Failed to send verification email", Toast.LENGTH_SHORT).show();
        }
    }


}