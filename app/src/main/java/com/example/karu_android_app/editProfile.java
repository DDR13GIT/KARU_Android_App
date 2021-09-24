package com.example.karu_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editProfile extends AppCompatActivity {
    private ImageButton back;
    private EditText fullName;
    private EditText Email;
    private EditText Dob;
    private EditText phnNum;
    private Button save;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore root = FirebaseFirestore.getInstance();


    public static final String Key_name = "name";
    public static final String Key_dob = "dob";
    public static final String Key_phn = "phone";
    public static final String Key_email = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        back = findViewById(R.id.backBTN);


        fullName = (EditText) findViewById(R.id.editFullName);
        Email = (EditText) findViewById(R.id.editEmail);
        phnNum = (EditText) findViewById(R.id.editPhnNum);
        Dob = (EditText) findViewById(R.id.editDOB);
        save = findViewById(R.id.saveBTN);


        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String intentName = bundle.getString("name_PreIntent");
        String intentPhn = bundle.getString("phn_PreIntent");
        String intentEmail = bundle.getString("email_PreIntent");
        String intentDob = bundle.getString("dob_PreIntent");

        fullName.setText(intentName);
        Email.setText(intentEmail);
        phnNum.setText(intentPhn);
        Dob.setText(intentDob);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = fullName.getText().toString();
                String email = Email.getText().toString();
                String phn = phnNum.getText().toString();
                String dob = Dob.getText().toString();


                Map<String,Object> userInfo = new HashMap<>();
                userInfo.put(Key_name,name);
                userInfo.put(Key_phn,phn);
                userInfo.put(Key_dob,dob);
                userInfo.put(Key_email,email);

                DocumentReference documentReference = root.collection("Users").document(user.getUid()).collection("basic_info").document("name");

                documentReference.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>()
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
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), settingFragment.class);
                finish();
            }
        });
    }
}