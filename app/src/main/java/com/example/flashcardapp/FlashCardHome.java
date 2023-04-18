package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class FlashCardHome extends AppCompatActivity {


    FloatingActionButton mcreateFlashCardFab;


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_home);

        getSupportActionBar().setTitle("All flash cards");

        mcreateFlashCardFab = findViewById(R.id.createFlashCardFab);
        firebaseAuth = FirebaseAuth.getInstance();

        mcreateFlashCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlashCardHome.this, Create_flashCard.class));
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Made so that we easily can implement more options
        switch (item.getItemId()){
            case R.id.logOut:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(FlashCardHome.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}