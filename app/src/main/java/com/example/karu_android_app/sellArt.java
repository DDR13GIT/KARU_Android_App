package com.example.karu_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sellArt extends AppCompatActivity {

    private ImageButton back;
    private EditText title, size, category, description, price;


    public static final String Key_title = "title";
    public static final String Key_size = "size";
    public static final String Key_category = "category";
    public static final String Key_description = "description";
    public static final String Key_price = "price";
      String name;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore root = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_art);

        back = findViewById(R.id.backBTN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        title = findViewById(R.id.post_title);
        size = findViewById(R.id.post_size);
        category = findViewById(R.id.post_category);
        description = findViewById(R.id.post_description);
        price = findViewById(R.id.post_price);

    }

    public void publish (View view){
        String post_title = title.getText().toString();
        int post_size = Integer.parseInt(size.getText().toString());
        String post_category = category.getText().toString();
        String post_description = description.getText().toString();
        int post_price = Integer.parseInt(price.getText().toString());

        Map<String,Object> postInfo = new HashMap<>();
        postInfo.put(Key_title,post_title);
        postInfo.put(Key_size,post_size);
        postInfo.put(Key_category,post_category);
        postInfo.put(Key_description,post_description);
        postInfo.put(Key_price,post_price);
        DocumentReference documentReference = root.collection("Posts").document();


        // db.collection("Users").document(signup.GlobalName).collection("UserPosts").set(postInfo);
//        documentReference.collection("Posts").document().set(postInfo);
        documentReference.set(postInfo).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }

}