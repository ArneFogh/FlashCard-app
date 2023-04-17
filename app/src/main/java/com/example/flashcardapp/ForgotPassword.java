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
import com.google.firebase.auth.FirebaseAuth;

//Arne

public class ForgotPassword extends AppCompatActivity {

    ProgressBar mprogressBarForgotPassword;
    private ImageView mgoToHomeFromForgotPassword;
    private EditText mforgotPasswordEmail;
    private RelativeLayout mrecoverPassword;
    private TextView mgoToMainActivity;

    private FirebaseAuth firebaseAuth;

    //Arne
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //This removes the action bar in the top of the screen
        getSupportActionBar().hide();

        mgoToHomeFromForgotPassword = findViewById(R.id.goToHomeFromForgotPassword);
        mforgotPasswordEmail = findViewById(R.id.forgotPasswordEmail);
        mrecoverPassword = findViewById(R.id.recoverPassword);
        mgoToMainActivity = findViewById(R.id.goToMainActivity);
        mprogressBarForgotPassword = findViewById(R.id.progressBarForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        //Go to the home page when clicked on the "Remember password" text
        mgoToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Arne

        //Go to the home page when clicked on the logo
        mgoToHomeFromForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Arne

        //When clicked on recover password
        mrecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Converting input in the email-field
                String mail = mforgotPasswordEmail.getText().toString().trim();

                //Checking if the email is provided
                if (mail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter your email first", Toast.LENGTH_SHORT).show();
                }
                else {

                    //Send password recover email
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Checking if it was successful
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Mail sent, check your mail to recover your password", Toast.LENGTH_SHORT).show();

                                //If successful, finish task and go back to login
                                finish();
                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                            }
                            //Making a toast if it wasn't
                            else {
                                Toast.makeText(getApplicationContext(), "Account does not exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}