package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import soup.neumorphism.NeumorphCardView;

public class AssetDetailsActivity1 extends AppCompatActivity {

    NeumorphCardView cv_fd_bankrakyat,cv_fd_maybank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details1);
        cv_fd_bankrakyat=findViewById(R.id.cv_fd_bank_rakyat);
        cv_fd_maybank=findViewById(R.id.cv_fd_maybank);

        cv_fd_bankrakyat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FixedDepositDetailActivity1.class));
            }
        });

        cv_fd_maybank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FixedDepositDetailActivity2.class));
            }
        });
    }
}