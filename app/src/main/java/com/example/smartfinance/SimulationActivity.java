package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import soup.neumorphism.NeumorphCardView;

public class SimulationActivity extends AppCompatActivity {

    NeumorphCardView cv_fixed_depo,cv_gold,cv_robo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        cv_fixed_depo=findViewById(R.id.neumorphCardView_fixed_depo);
//        cv_gold=findViewById(R.id.neumorphCardView_gold);
        cv_robo=findViewById(R.id.neumorphCardView_robo);

        cv_fixed_depo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AssetDetailsActivity1.class));
            }
        });

//        cv_gold.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),AssetDetailsActivity2.class));
//            }
//        });

        cv_robo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AssetDetailActivity3.class));
            }
        });
    }
}