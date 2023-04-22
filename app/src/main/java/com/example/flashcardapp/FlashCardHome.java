package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class FlashCardHome extends AppCompatActivity {


    FloatingActionButton mcreateFlashCardFab;
    private FirebaseAuth firebaseAuth;
    RecyclerView mrecyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter<firebaseModel, FlashCardViewHolder> flashCardAdapter;

    String deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_home);

        mcreateFlashCardFab = findViewById(R.id.createFlashCardFab);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //deckId = firebaseFirestore.collection("flashCards").document(firebaseUser.getUid()).collection("myFlashCards").document().getId();

        getSupportActionBar().setTitle("All flash cards");
        mcreateFlashCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlashCardHome.this, Create_flashCard.class));
            }
        });

        //            firebaseFirestore.collection("flashCards").document(firebaseUser.getUid()).collection("myFlashCards").document(deckId).collection("cards").document(flashcardId);
        //            firebaseFirestore.collection("flashCards").document(firebaseUser.getUid()).collection("myFlashCards").document();

        // firebaseFirestore.collection("flashCards").document(firebaseUser.getUid()).collection("myFlashCards").document(deckId).collection("cards").orderBy("title", Query.Direction.ASCENDING);

        Query query = firebaseFirestore.collection("flashCards").document(firebaseUser.getUid()).collection("myFlashCards").orderBy("title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<firebaseModel> allUserFlashCards = new FirestoreRecyclerOptions.Builder<firebaseModel>().setQuery(query, firebaseModel.class).build();

        flashCardAdapter = new FirestoreRecyclerAdapter<firebaseModel, FlashCardViewHolder>(allUserFlashCards) {

            @Override
            protected void onBindViewHolder(@NonNull FlashCardViewHolder flashCardViewHolder, int i, @NonNull firebaseModel firebaseModel) {


                flashCardViewHolder.flashCardTitle.setText(firebaseModel.getTitle());
                //flashCardViewHolder.flashCardAnswer.setText(firebaseModel.getAnswer());
                //flashCardViewHolder.flashCardQuestion.setText(firebaseModel.getQuestion());

                String deckId = flashCardAdapter.getSnapshots().getSnapshot(i).getId();
                flashCardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Intent intent = new Intent(v.getContext(), FlashCardDetails.class);
                        intent.putExtra("title", firebaseModel.getTitle());
                        intent.putExtra("answer", firebaseModel.getAnswer());
                        intent.putExtra("question", firebaseModel.getQuestion());
                        intent.putExtra("deckId", deckId);

                        v.getContext().startActivity(intent);
                    }
                });

            }



            @NonNull
            @Override
            public FlashCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_card_layout, parent, false);
                return new FlashCardViewHolder(view);
            }
        };

        mrecyclerView = findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mrecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mrecyclerView.setAdapter(flashCardAdapter);


    }


    public class FlashCardViewHolder extends RecyclerView.ViewHolder{

        private TextView flashCardTitle;
        private TextView flashCardAnswer;
        private TextView flashCardQuestion;
        LinearLayout mflashCard;

        public FlashCardViewHolder(@NonNull View itemView) {
            super(itemView);
            flashCardTitle = itemView.findViewById(R.id.flashCardTitle);
            mflashCard = itemView.findViewById(R.id.flashCard);

        }
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


    @Override
    protected void onStart() {
        super.onStart();
        flashCardAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (flashCardAdapter != null){
            flashCardAdapter.stopListening();
        }
    }
}