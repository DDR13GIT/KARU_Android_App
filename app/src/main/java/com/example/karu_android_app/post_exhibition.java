package com.example.karu_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class post_exhibition extends AppCompatActivity {
    private ImageButton back;
    private EditText event_name,event_place,event_date,event_price,event_host,host_nid,payment_num;


    public static final String Key_event_name = "Event Name";
    public static final String Key_event_place = "Event Place";
    public static final String Key_event_date = "Event Date";
    public static final String Key_event_price= "Ticket Price";
    public static final String Key_event_host = "Event Host";
    public static final String Key_host_nid = "Host NID";
    public static final String Key_payment_num = "Payment No";
    String name;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore root = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_exibition);

        back = findViewById(R.id.backBTN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        event_name = findViewById(R.id.event_name);
        event_place = findViewById(R.id.event_place);
        event_date= findViewById(R.id.event_date);
        event_host = findViewById(R.id.event_host);
        host_nid= findViewById(R.id.host_nid);
        event_price = findViewById(R.id.event_price);
        payment_num = findViewById(R.id.payment_num);

    }


    public void publishExhibition (View view){
        String eventName = event_name.getText().toString();
        String eventPlace= event_place.getText().toString();
        String eventDate = event_date.getText().toString();
        String eventHost = event_host.getText().toString();
        double price = Integer.parseInt(event_price.getText().toString());
        double nid = Integer.parseInt(host_nid.getText().toString());
        double payment = Integer.parseInt(payment_num.getText().toString());

        Map<String,Object> postInfo = new HashMap<>();
        postInfo.put(Key_event_name,event_name);
        postInfo.put(Key_event_place,event_place);
        postInfo.put(Key_event_date,event_date);
        postInfo.put(Key_event_host,event_host);
        postInfo.put(Key_host_nid,host_nid);
        postInfo.put(Key_event_price,event_price);
        postInfo.put(Key_payment_num,payment_num);
        DocumentReference documentReference = root.collection("Exhibition").document();
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