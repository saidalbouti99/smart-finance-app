package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class CalculatorResultActivity extends AppCompatActivity {
    TextView tv_housing_loan,tv_groceries,tv_loan_payment,tv_utility,tv_savings,tv_insurance,tv_transportation,tv_total;

    Button btn_tryy_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);
        tv_housing_loan=findViewById(R.id.tv_housingloan_calculator);
        tv_groceries=findViewById(R.id.tv_groceries_calculator);
        tv_loan_payment=findViewById(R.id.tv_loan_calculator);
        tv_utility=findViewById(R.id.tv_utility_bills_calculator);
        tv_savings=findViewById(R.id.tv_savings_calculator);
        tv_insurance=findViewById(R.id.tv_insurance_calculator);
        tv_transportation=findViewById(R.id.tv_transportation_calculator);
        tv_total=findViewById(R.id.tv_total_calculator2);

        btn_tryy_again=findViewById(R.id.btn_try_again_calculator);
        Bundle b = getIntent().getExtras();
        Double house = b.getDouble("house");
        Double grocery=b.getDouble("grocery");
        Double loan=b.getDouble("loan");
        Double utility=b.getDouble("utility");
        Double savings=b.getDouble("savings");
        Double insurance=b.getDouble("insurance");
        Double transport=b.getDouble("transport");
        int total=b.getInt("total");

        String h= String.format(Locale.US, "%.2f", house);
        String g= String.format(Locale.US, "%.2f", grocery);
        String l= String.format(Locale.US, "%.2f", loan);
        String u= String.format(Locale.US, "%.2f",utility);
        String s= String.format(Locale.US, "%.2f", savings);
        String i= String.format(Locale.US, "%.2f", insurance);
        String t= String.format(Locale.US, "%.2f", transport);
        String tot=Integer.toString(total);

        tv_housing_loan.setText(h);
        tv_groceries.setText(g);
        tv_loan_payment.setText(l);
        tv_utility.setText(u);
        tv_savings.setText(s);
        tv_insurance.setText(i);
        tv_transportation.setText(t);
        tv_total.setText(tot);

        btn_tryy_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CalculatorActivity.class));
                finish();
            }
        });
    }
}