package com.example.karu_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

//import com.travijuu.numberpicker.library.NumberPicker;

public class postDetails extends AppCompatActivity {
    private TextView title,price;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

     /*   NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setMax(15);
        numberPicker.setMin(5);
        numberPicker.setUnit(2);
        numberPicker.setValue(10);
        int count = numberPicker.getValue();*/

        title= findViewById(R.id.postTitle);
        price= findViewById(R.id.postPrice);
        image= findViewById(R.id.postImage);

    }
}