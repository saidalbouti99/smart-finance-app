package com.example.smartfinance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfinance.Database.SessionManager;

public class FixedDepositDetailActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    TextView tv_fd_interest_rate;
    EditText et_fd_maybank;
    Button btn_proceed_fd;
    double result;
    SessionManager sessionManager;

    private boolean initializedView = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit_detail2);

        spinner=findViewById(R.id.spinner_fd_maybank);
        tv_fd_interest_rate=findViewById(R.id.tv_fd_interest_rates_maybank);
        et_fd_maybank=findViewById(R.id.et_fd_maybank);
        btn_proceed_fd=findViewById(R.id.btn_proceed_fd_maybank);
        sessionManager = new SessionManager(FixedDepositDetailActivity2.this);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.fdtermmaybank, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        int initialposition=spinner.getSelectedItemPosition();
        spinner.setSelection(initialposition, false);
        spinner.setOnItemSelectedListener(this);

        btn_proceed_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_fd_maybank.getText().toString().isEmpty()) {
                    et_fd_maybank.setError("Please input amount");
                    et_fd_maybank.requestFocus();
                }
                else if ( (Integer.parseInt(et_fd_maybank.getText().toString()))<1000) {
                    et_fd_maybank.setError("The minimum amount is RM1000");
                    et_fd_maybank.requestFocus();
                    AlertDialog.Builder builder= builder = new AlertDialog.Builder(FixedDepositDetailActivity2.this);
                    builder.setMessage("For this fixed deposit, the minimum amount that you need to invest is RM 1000.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Minimum Amount");
                    alert.show();
                }
//                else if (spinner.getSelectedItem().toString() =="Please select duration"){
//                    et_fd_maybank.setError("Please select duration");
//                }
                else if(initializedView ==  false) {
                    Toast.makeText(getApplicationContext(),"None selected",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), FDMaybankActivity.class);
//                    intent.putExtra("fd_amount_br", et_fd_maybank.getText().toString());
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=spinner.getItemAtPosition(position).toString();

        if (initializedView ==  true) {
            initializedView = false;
        }
        else {
            initializedView=true;
            if (text.equals("6 Months")) {
//            Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
                tv_fd_interest_rate.setText("1.8% annual interest");

                double fd = 0;
                try {
                    fd = Double.valueOf(et_fd_maybank.getText().toString());
                    result = fd + ((fd * 1.8 * 0.5) / 100);
                    String l = String.format("%.2f", result);
                    String resultS = String.valueOf(result);
                    sessionManager.createFDMaybanksession(l);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else if (text.equals("1 Year")) {
                tv_fd_interest_rate.setText("1.85% annual interest");

                double fd = 0;
                try {
                    fd = Double.valueOf(et_fd_maybank.getText().toString());
                    result = fd + ((fd * 1.85 * 1) / 100);
                    String l = String.format("%.2f", result);
                    String resultS = String.valueOf(result);
                    sessionManager.createFDMaybanksession(l);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else if (text.equals("5 Years")) {
                tv_fd_interest_rate.setText("2.1% annual interest");

                double fd = 0;
                try {
                    fd = Double.valueOf(et_fd_maybank.getText().toString());
                    result = fd + ((fd * 2.1 * 5) / 100);
                    String l = String.format("%.2f", result);
                    String resultS = String.valueOf(result);
                    sessionManager.createFDMaybanksession(l);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(getApplicationContext(),"Please select duration first",Toast.LENGTH_SHORT).show();
    }
}