package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {
    Button btn_rm_3000, btn_rm_4000, btn_rm_5000, btn_rm_6000,btn_proceed_calculator;
    EditText et_income_amount_calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        btn_rm_3000=findViewById(R.id.btn_rm_3000);
        btn_rm_4000=findViewById(R.id.btn_rm_4000);
        btn_rm_5000=findViewById(R.id.btn_rm_5000);
        btn_rm_6000=findViewById(R.id.btn_rm_6000);
        btn_proceed_calculator=findViewById(R.id.btn_proceed_calculator);
        et_income_amount_calculator=findViewById(R.id.et_income_amount_calculator);

        btn_rm_3000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_income_amount_calculator.setText("3000");
            }
        });

        btn_rm_4000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_income_amount_calculator.setText("4000");
            }
        });

        btn_rm_5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_income_amount_calculator.setText("5000");
            }
        });

        btn_rm_6000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_income_amount_calculator.setText("6000");
            }
        });

        btn_proceed_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value= et_income_amount_calculator.getText().toString();
                int finalValue;
                Double housingloan, grocery,utility,loan,savings,insurance,transportation;
                String housingLoan2;
                if (value.isEmpty()){
                    et_income_amount_calculator.setError("Please input the amount");
                    et_income_amount_calculator.requestFocus();
                }
                else {
                    finalValue = Integer.parseInt(value);
                    if (finalValue > 0) {
                        housingloan = 0.3 * finalValue;
                        grocery = 0.25 * finalValue;
                        loan = 0.15 * finalValue;
                        utility = 0.10 * finalValue;
                        savings = 0.10 * finalValue;
                        insurance = 0.05 * finalValue;
                        transportation = 0.05 * finalValue;
                        Intent intent2 = new Intent(getApplicationContext(), CalculatorResultActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("total", finalValue);
                        b.putDouble("house", housingloan);
                        b.putDouble("grocery", grocery);
                        b.putDouble("loan", loan);
                        b.putDouble("utility", utility);
                        b.putDouble("savings", savings);
                        b.putDouble("insurance", insurance);
                        b.putDouble("transport", transportation);
                        intent2.putExtras(b);
                        finish();
                        startActivity(intent2);
                    } else {
                        Toast.makeText(getApplicationContext(), "Amount cannot be 0 or less than 0", Toast.LENGTH_SHORT).show();
                    }
                }
                }


        });

    }
}