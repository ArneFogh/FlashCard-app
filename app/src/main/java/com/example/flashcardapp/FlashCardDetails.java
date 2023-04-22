package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

//Arne
public class FlashCardDetails extends AppCompatActivity {

    LinearLayout mgoBackToFlashCardHome;

    private TextView mflashCardTitleDetail, mflipFlashCard, mflashCardContentDetail;

    RelativeLayout mgoToNextFlashCard, mgoToBackFlashCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_details);

        mgoBackToFlashCardHome = findViewById(R.id.goBackToFlashCardHome);
        mflashCardTitleDetail = findViewById(R.id.flashCardTitleDetail);
        mflipFlashCard = findViewById(R.id.flipFlashCard);
        mflashCardContentDetail = findViewById(R.id.flashCardContentDetail);
        mgoToNextFlashCard = findViewById(R.id.goToNextFlashCard);
        mgoToBackFlashCard = findViewById(R.id.goToBackFlashCard);
        mflipFlashCard = findViewById(R.id.flipFlashCard);


        mflipFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I need to make a flip animation
                //I need to replace the answer content with the question content
            }
        });

        mgoToNextFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I need to replace the question and answer with the next flash card in the deck
            }
        });

        mgoToBackFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I need to be able to go back to the previous flash card
            }
        });

        mgoBackToFlashCardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlashCardDetails.this, FlashCardHome.class));
            }
        });

        Intent data = getIntent();

        mflashCardTitleDetail.setText(data.getStringExtra("title"));
        mflashCardContentDetail.setText(data.getStringExtra("answer"));
        mflashCardContentDetail.setText(data.getStringExtra("question"));


        mflipFlashCard.setOnClickListener(new View.OnClickListener() {
            boolean isAnswerShown = false;

            @Override
            public void onClick(View v) {
                if (!isAnswerShown) {
                    mflashCardContentDetail.setText(data.getStringExtra("answer"));
                    isAnswerShown = true;
                } else {
                    mflashCardContentDetail.setText(data.getStringExtra("question"));
                    isAnswerShown = false;
                }
            }
        });




    }
}