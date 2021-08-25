package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import soup.neumorphism.NeumorphCardView;

public class AssetDetailActivity3 extends AppCompatActivity {

    NeumorphCardView cv_very_conservative,cv_moderate,cv_very_aggressive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail3);
        cv_very_conservative=findViewById(R.id.cv_robo_very_conservative);
        cv_moderate=findViewById(R.id.cv_robo_moderate);
        cv_very_aggressive=findViewById(R.id.cv_robo_very_aggressive);

        cv_very_conservative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PortfolioVeryConservativeActivity.class));
            }
        });

        cv_moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PortfolioModerateActivity.class));
            }
        });

        cv_very_aggressive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PortfolioVeryAggressiveActivity.class));
            }
        });
    }
}