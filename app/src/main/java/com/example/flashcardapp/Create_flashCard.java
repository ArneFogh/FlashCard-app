package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Create_flashCard extends AppCompatActivity {

    private EditText mcreateDeckTitle, mcreateQuestion, mcreateAnswer;
    private TextView mgoToNextSlide;
    private RelativeLayout msaveFlashCard;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flash_card);

        getSupportActionBar().hide();

        mcreateDeckTitle = findViewById(R.id.createDeckTitle);
        mcreateAnswer = findViewById(R.id.createAnswer);
        mcreateQuestion = findViewById(R.id.createQuestion);
        mgoToNextSlide = findViewById(R.id.goToNextSlide);
        msaveFlashCard = findViewById(R.id.saveFlashCard);

        firebaseAuth = FirebaseAuth.getInstance();

        msaveFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the flash card to firebase
                startActivity(new Intent(Create_flashCard.this, FlashCardHome.class));
            }
        });


    }
}