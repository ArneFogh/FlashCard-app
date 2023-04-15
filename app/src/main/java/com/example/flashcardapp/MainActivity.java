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

        mgoToSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpPage.class));
            }
        });

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

                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                checkMailVerification();
                            }
                            else{
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

        if (firebaseUser.isEmailVerified()==true){
            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, FlashCardHome.class));
        }
        else {
            mprogressBarMainActivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Verify your email first", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}