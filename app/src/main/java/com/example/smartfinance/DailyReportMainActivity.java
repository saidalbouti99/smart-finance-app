package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailyReportMainActivity extends AppCompatActivity {

    Button view_income,view_expense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report_main);
        view_income=findViewById(R.id.btn_view_daily_income);
        view_expense=findViewById(R.id.btn_view_daily_expense);

        view_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PastExpenseActivity.class));
            }
        });

        view_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PastIncomeActivity.class));
            }
        });
    }
}