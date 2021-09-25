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
    private EditText Event_name,Event_place,Event_date,Event_price,Event_host,Host_nid,Payment_num;


    public static final String Key_event_name = "eventName";
    public static final String Key_event_place = "eventPlace";
    public static final String Key_event_date = "eventDate";
    public static final String Key_event_price= "ticketPrice";
    public static final String Key_event_host = "eventHost";
    public static final String Key_host_nid = "hostNID";
    public static final String Key_payment_num = "paymentNumber";
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

        Event_name = findViewById(R.id.event_name);
        Event_place = findViewById(R.id.event_place);
        Event_date= findViewById(R.id.event_date);
        Event_host = findViewById(R.id.event_host);
        Host_nid= findViewById(R.id.host_nid);
        Event_price = findViewById(R.id.event_price);
        Payment_num = findViewById(R.id.payment_num);

    }


    public void publishExhibition (View view){
        String eventName = Event_name.getText().toString();
        String eventPlace= Event_place.getText().toString();
        String eventDate = Event_date.getText().toString();
        String eventHost = Event_host.getText().toString();
        double price = Double.parseDouble(Event_price.getText().toString());
        double nid = Double.parseDouble(Host_nid.getText().toString());
        double payment = Double.parseDouble(Payment_num.getText().toString());


        Map<String,Object> postInfo = new HashMap<>();
        postInfo.put(Key_event_name,eventName);
        postInfo.put(Key_event_place,eventPlace);
        postInfo.put(Key_event_date,eventDate);
        postInfo.put(Key_event_host,eventHost);
        postInfo.put(Key_host_nid,price);
        postInfo.put(Key_event_price,nid);
        postInfo.put(Key_payment_num,payment);
        postInfo.put("userUid", user.getUid());
        DocumentReference documentReference = root.collection("Exhibition").document();

        documentReference.set(postInfo).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Exhibition hosted successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }
}