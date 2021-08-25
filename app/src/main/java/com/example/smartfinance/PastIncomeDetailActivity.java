package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PastIncomeDetailActivity extends AppCompatActivity {

    TextView tv_category,tv_amount,tv_dates,tv_notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_income_detail);
        tv_category=findViewById(R.id.tv_cat_past_income_details);
        tv_amount=findViewById(R.id.tv_amount_past_income_details);
        tv_dates=findViewById(R.id.tv_date_past_income_details);
        tv_notes=findViewById(R.id.tv_notes_past_income_details);

        Bundle b = getIntent().getExtras();
        String category = b.getString("category");
        String amount = b.getString("amount");
        String date = b.getString("date");
        String notes= b.getString("notes");

        tv_category.setText(category);
        tv_amount.setText(amount);
        tv_dates.setText(date);
          tv_notes.setText(notes);
    }
}