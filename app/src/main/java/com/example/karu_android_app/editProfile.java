package com.example.karu_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfile extends AppCompatActivity {
private ImageButton back;


private EditText fullName;
    private EditText Email;
    private EditText Dob;
    private EditText phnNum;
private Button save;
FirebaseDatabase rootNode;
DatabaseReference reference;
private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        back = findViewById(R.id.backBTN);


        fullName=(EditText) findViewById(R.id.editFullName);
        Email=(EditText)findViewById(R.id.editEmail);
        phnNum =(EditText) findViewById(R.id.editPhnNum);
        Dob=(EditText)findViewById(R.id.editDOB);
        save=findViewById(R.id.saveBTN);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                rootNode = FirebaseDatabase.getInstance();
             //   reference = rootNode.getReference("user");/////
                reference = rootNode.getReference().child(user.getUid());

                String name = fullName.getText().toString();
                String email = Email.getText().toString();
                String phn = phnNum.getText().toString();
                String dob = Dob.getText().toString();
                userInfo userInfo = new userInfo(name,email,phn,dob);

                reference.child(phn).setValue(userInfo);
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),settingFragment.class);
                finish();
            }
        });
    }
}