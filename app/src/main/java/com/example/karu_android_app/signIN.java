package com.example.karu_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class signIN extends AppCompatActivity implements OnClickListener{
    private EditText signInEmail,signInPass;
    private TextView signUpTextView;
    private Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.setTitle("Sign In Activity");

        signInEmail=findViewById(R.id.emailText);
        signInPass=findViewById(R.id.passwordText);

        signUpTextView=findViewById(R.id.newHere);
        signInBtn=findViewById(R.id.logInBTN);

        signUpTextView.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.logInBTN:

                break;

            case R.id.newHere:
                Intent intent = new Intent(getApplicationContext(),signup.class);
                startActivity(intent);

                break;
        }

    }
}