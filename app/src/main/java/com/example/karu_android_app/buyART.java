package com.example.karu_android_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class buyART extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postReference = db.collection("Posts");
    private curatedPostAdapter c_adapter;
//    private PostAdapter adapter;
    private Button search_button;
        FirestoreRecyclerAdapter<postDataModel,postHolder> recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_art);
        search_button = findViewById(R.id.searchBTN);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_page.class);
                startActivity(intent);
            }
        });

        Query query = postReference.orderBy("price", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<postDataModel> allinfo =new  FirestoreRecyclerOptions.Builder<postDataModel>().setQuery(query,postDataModel.class).build();
        recyclerAdapter = new FirestoreRecyclerAdapter<postDataModel, postHolder>(allinfo) {
            @Override
            protected void onBindViewHolder(@NonNull postHolder holder, int position, @NonNull postDataModel model) {

               String docId =  recyclerAdapter.getSnapshots().getSnapshot(position).getId();
                holder.title.setText(model.getTitle());
                holder.price.setText("BDT " + String.valueOf(model.getPrice())+ " ৳");
                holder.category.setText(model.getCategory());
                Picasso.get().load(model.getImageUrl()).into(holder.image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),postDetails.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("price",String.valueOf(model.getPrice()));
                        intent.putExtra("image_uri",model.getImageUrl());
                        intent.putExtra("description",model.getDescription());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public postHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.postitem,parent,false);
                return new postHolder(view);
            }
        };
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);


    }

    public class postHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView price;
        TextView category;
        ImageView image;

        public postHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.postTitle);
            price = itemView.findViewById(R.id.postPrice);
            category = itemView.findViewById(R.id.postCategory);
            image = itemView.findViewById(R.id.imageFromDatabase);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }
}