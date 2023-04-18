package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Edit_flashCard extends AppCompatActivity {

    private EditText meditDeckTitle, meditQuestion, meditAnswer;
    private TextView meditNextSlide;
    private RelativeLayout msaveEditFlashCard;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flash_card);

        getSupportActionBar().hide();


        meditDeckTitle = findViewById(R.id.editDeckTitle);
        meditQuestion = findViewById(R.id.editQuestion);
        meditAnswer = findViewById(R.id.editAnswer);
        meditNextSlide = findViewById(R.id.editNextSlide);
        msaveEditFlashCard = findViewById(R.id.saveEditFlashCard);

        msaveEditFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the edit of the flash card to firebase and go to flash card home page
                startActivity(new Intent(Edit_flashCard.this, FlashCardHome.class));
            }
        });


    }
}