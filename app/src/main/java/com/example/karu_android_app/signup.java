package com.example.karu_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpEmail, signUpPass;
    private TextView signInTextView;
    private Button signUpBtn;
    private EditText fullName;
    private EditText Dob;
    private EditText phnNum;
    private FirebaseAuth mAuth;

//    FirebaseDatabase rootNode;
//    DatabaseReference reference;
    private FirebaseUser user;

    public static final String Key_name = "name";
    public static final String Key_dob = "dob";
    public static final String Key_phn = "phone";
    public static final String Key_email = "email";
    public static final String Key_pass = "password";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Sign Up Activity");

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        signUpEmail = findViewById(R.id.signUpEmailText);
        signUpPass = findViewById(R.id.passwordInput);

        signInTextView = findViewById(R.id.alreadyAccount);
        signUpBtn = findViewById(R.id.signupBTN);

        signInTextView.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);

        fullName = (EditText) findViewById(R.id.fullNameInput);
        phnNum = (EditText) findViewById(R.id.phoneNumberInput);
        Dob = (EditText) findViewById(R.id.dobInput);
    }
    //public static String GlobalName = fullName.getText().toString();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.signupBTN:
                userRegister();
                break;

            case R.id.alreadyAccount:
                Intent intent = new Intent(getApplicationContext(), signIN.class);
                startActivity(intent);
                break;
        }

    }

    private void userRegister() {

         String name = fullName.getText().toString();
        String phn = phnNum.getText().toString();
        String dob = Dob.getText().toString();
        String email = signUpEmail.getText().toString().trim();
        String pass = signUpPass.getText().toString().trim();

        //userInfo userInfo = new userInfo(name, email, phn, dob);


        if (TextUtils.isEmpty(email)) {
            signUpEmail.setError("Email Required");
            signUpEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpEmail.setError("Enter a valid email");
            signUpEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            signUpPass.setError("Password Required");
            signUpEmail.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            signUpPass.setError("Password must be at least six digit");
            signUpEmail.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register is successful", Toast.LENGTH_SHORT).show();


                    //wer are passing info to realtime database here.
//                    user = FirebaseAuth.getInstance().getCurrentUser();
//                    rootNode = FirebaseDatabase.getInstance();
//                    reference = rootNode.getReference().child(user.getUid());
//                    reference.child(phn).setValue(userInfo);
                    //Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();



                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put(Key_name,name);
                    userInfo.put(Key_phn,phn);
                    userInfo.put(Key_dob,dob);
                    userInfo.put(Key_email,email);
                    userInfo.put(Key_pass,pass);
                    db.collection("Users").document(name).set(userInfo);

                    finish();
                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

    }


}

