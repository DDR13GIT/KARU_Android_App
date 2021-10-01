package com.example.karu_android_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.NumberPicker;

public class postDetails extends AppCompatActivity {

    TextView title_view, price_view, description_view;
    ImageView image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setMax(15);
        numberPicker.setMin(0);
        numberPicker.setUnit(1);
        numberPicker.setValue(1);

        int count = numberPicker.getValue();


        Intent data = getIntent();
        String title = data.getStringExtra("title");
        String price = data.getStringExtra("price");
        String description = data.getStringExtra("description");

        String image_uri = data.getStringExtra("image_uri");

        title_view = findViewById(R.id.title_details);
        price_view = findViewById(R.id.details_price);
        description_view = findViewById(R.id.description_details);
        image_view = findViewById(R.id.imageViewDetails);

        title_view.setText(title);
        price_view.setText("BDT " + price + " ৳");
        description_view.setText(description);
        Picasso.get().load(image_uri).into(image_view);

    }
}