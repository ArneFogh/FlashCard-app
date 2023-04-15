package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //I will write an "m" in front of all of the names to better know what belongs to what
    ProgressBar mprogressBarMainActivity;
    private EditText mlogInEmail, mlogInPassword;
    private TextView mgoToForgotPassword;
    private RelativeLayout mlogIn, mgoToSignUpPage;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This removes the action bar in the top of the screen
        getSupportActionBar().hide();


        //Here i am connecting the .java with .xml, to make it interactive
        mgoToSignUpPage = findViewById(R.id.goToSignUpPage);
        mprogressBarMainActivity = findViewById(R.id.progressBarMainActivity);
        mlogInEmail = findViewById(R.id.logInEmail);
        mlogInPassword = findViewById(R.id.logInPassword);
        mgoToForgotPassword = findViewById(R.id.goToForgotPassword);
        mlogIn = findViewById(R.id.login);


        //Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //This will check if the user is already in the firebase database,
        // and if it is it will take them to the Flash card home page
        if (firebaseUser != null){
            finish();
            startActivity(new Intent(MainActivity.this, FlashCardHome.class));
        }

        //When the sign up button has been clicked, go to sign up page
        mgoToSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpPage.class));
            }
        });

        //When go to forgot password has been clicked, go to forgot password
        mgoToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

        mlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mlogInEmail.getText().toString().trim();
                String password = mlogInPassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Login the user
                    mprogressBarMainActivity.setVisibility(View.VISIBLE);

                    //Firebase authentication
                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //If task is successful call the check mail verification
                            if (task.isSuccessful()){
                                checkMailVerification();
                            }
                            else{
                                //If task is not successful display this message
                                Toast.makeText(getApplicationContext(), "Account does not exists", Toast.LENGTH_SHORT).show();
                                mprogressBarMainActivity.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }
            }
        });

    }

    private void checkMailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //Firebase authentication. Check if user is verified, if it is, then go to the flash card home page
        if (firebaseUser.isEmailVerified()==true){
            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, FlashCardHome.class));
        }
        else {
            //Progressbar
            mprogressBarMainActivity.setVisibility(View.INVISIBLE);
            //If the user isn't verified, show this message
            Toast.makeText(getApplicationContext(), "Verify your email first", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}