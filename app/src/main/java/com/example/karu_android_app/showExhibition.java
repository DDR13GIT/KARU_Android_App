package com.example.karu_android_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class showExhibition extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postReference = db.collection("Exhibition");

    private exhibitionPostAdapter adapter;
    private FloatingActionButton floatingBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exhibition);

        floatingBTN = findViewById(R.id.plusBTN);
        floatingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), post_exhibition.class);
                startActivity(intent);
            }
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = postReference.orderBy("eventDate", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<exhibitionPostInfo> options = new FirestoreRecyclerOptions.Builder<exhibitionPostInfo>()
                .setQuery(query, exhibitionPostInfo.class)
                .build();
        adapter = new exhibitionPostAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.Exhibition_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}