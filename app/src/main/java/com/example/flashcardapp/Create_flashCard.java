package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Create_flashCard extends AppCompatActivity {

    EditText mcreateDeckTitle, mcreateQuestion, mcreateAnswer;
    TextView mgoToNextSlide;
    RelativeLayout msaveFlashCard;
    ProgressBar mprogressBarOfCreateFlashCard;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
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

        mprogressBarOfCreateFlashCard = findViewById(R.id.progressBarOfCreateFlashCard);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        msaveFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mcreateDeckTitle.getText().toString();

                String answer = mcreateAnswer.getText().toString();

                String question = mcreateQuestion.getText().toString();

                //If any of the fields are empty, show toast with this message
                if (title.isEmpty() || answer.isEmpty() || question.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    mprogressBarOfCreateFlashCard.setVisibility(View.VISIBLE);

                    //Creating the firebase database structure, and assigning it to the specific user
                    DocumentReference documentReference = firebaseFirestore.collection("flashCards").document(firebaseUser.getUid()).collection("myFlashCards").document();
                    Map<String, Object> flashCard = new HashMap<>();
                    flashCard.put("title", title);
                    flashCard.put("answer", answer);
                    flashCard.put("question", question);

                    documentReference.set(flashCard).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Flash card have been created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Create_flashCard.this, FlashCardHome.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to create flash card", Toast.LENGTH_SHORT).show();
                            mprogressBarOfCreateFlashCard.setVisibility(View.INVISIBLE);
                        }
                    });


                }
            }
        });


    }
}