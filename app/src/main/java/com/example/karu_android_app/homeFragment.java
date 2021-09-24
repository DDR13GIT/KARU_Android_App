package com.example.karu_android_app;



import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class homeFragment extends Fragment {
 private CardView sellArtFunction, buyArtFunction, exhibitionFunction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sellArtFunction = v.findViewById(R.id.cardView2);
        buyArtFunction = v.findViewById(R.id.cardView1);
        exhibitionFunction = v.findViewById(R.id.cardView3);

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

        return v;
    }
}