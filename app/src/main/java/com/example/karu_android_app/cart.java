package com.example.karu_android_app;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class cart extends AppCompatActivity {
    private ImageButton back;
    private Button browse;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference cartReference = db.collection("Users").document(user.getUid()).collection("cart"); //////////////////////////////
    FirestoreRecyclerAdapter<cartDataModel, cartHolder> recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cart);

        back = findViewById(R.id.backBTN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

     /*   browse = findViewById(R.id.browseBTN);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),main_cart.class);
                startActivity(intent);
            }
        });*/
        class WrapContentLinearLayoutManager extends LinearLayoutManager {
            public WrapContentLinearLayoutManager(Context context) {
                super(context);
            }

            public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
                super(context, orientation, reverseLayout);
            }

            public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
                super(context, attrs, defStyleAttr, defStyleRes);
            }

            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("TAG", "meet a IOOBE in RecyclerView");
                }
            }
        }


        Query query = cartReference.orderBy("title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<cartDataModel> allinfo = new FirestoreRecyclerOptions.Builder<cartDataModel>().setQuery(query, cartDataModel.class).build();
        recyclerAdapter = new FirestoreRecyclerAdapter<cartDataModel, cartHolder>(allinfo) {
            @Override
            protected void onBindViewHolder(@NonNull cartHolder holder, int position, @NonNull cartDataModel model) {

                String docId = recyclerAdapter.getSnapshots().getSnapshot(position).getId();
                holder.title.setText(model.getTitle());
                holder.price.setText("BDT " + model.getPrice() + " ৳");
                Picasso.get().load(model.getImageUrl()).into(holder.image);
               /* holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), postDetails.class);
                        intent.putExtra("title", model.getTitle());
                        intent.putExtra("price", String.valueOf(model.getPrice()));
                        intent.putExtra("image_uri", model.getImageUrl());

                        startActivity(intent);
                    }
                });*/
            }

            @NonNull
            @Override
            public cartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.activity_sample_cart, parent, false);
                return new cartHolder(view);
            }
        };
        RecyclerView recyclerView = findViewById(R.id.Cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public class cartHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        ImageView image;
        public cartHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.cart_postTitle);
            price = itemView.findViewById(R.id.cart_postPrice);
            image = itemView.findViewById(R.id.cart_imageFromDatabase);
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



