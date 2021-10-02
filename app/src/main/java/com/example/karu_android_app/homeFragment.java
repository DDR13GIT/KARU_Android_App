package com.example.karu_android_app;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class homeFragment extends Fragment {
 private CardView sellArtFunction, buyArtFunction, exhibitionFunction;
 private ImageButton cart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sellArtFunction = v.findViewById(R.id.cardView2);
        buyArtFunction = v.findViewById(R.id.cardView1);
        exhibitionFunction = v.findViewById(R.id.cardView3);
        cart=v.findViewById(R.id.cartBTN);

        sellArtFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), sellArt.class);
                startActivity(intent);

            }
        });

        buyArtFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), buyART.class);
                startActivity(intent);

            }
        });
        exhibitionFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), showExhibition.class);
                startActivity(intent);

            }
        });

        ////////////////////////////        cart if else            //////////////////////////////
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), main_cart.class);
                startActivity(intent);

            }
        });

        return v;
    }
}