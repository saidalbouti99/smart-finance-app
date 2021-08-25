package com.example.smartfinance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfinance.Database.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class FixedDepositDetailActivity1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "FD_AMOUNT";
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    Spinner spinner;
    TextView tv_fd_interest_rate;
    Button btn_proceed_fd;
    int amount;
    double result=0;
    SessionManager sessionManager;
    private boolean initializedView = false;
//    SharedPreferences prefs;
//    SharedPreferences.Editor edit;

    private String[] arraySpinner;

    EditText et_fd_bank_rakyat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit_detail1);
        spinner=findViewById(R.id.spinner_fd_bankrakyat);
        tv_fd_interest_rate=findViewById(R.id.tv_fd_interest_rates);
        et_fd_bank_rakyat=findViewById(R.id.et_fd_bank_rakyat);
        btn_proceed_fd=findViewById(R.id.btn_proceed_fd_bank_rakyat);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        sessionManager = new SessionManager(FixedDepositDetailActivity1.this);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.fdterm, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
//        String myString = "6 Months";
//        int spinnerPosition = adapter.getPosition(myString);
//        spinner.setSelection(spinnerPosition);
        int initialposition=spinner.getSelectedItemPosition();
        spinner.setSelection(initialposition, false);
        spinner.setOnItemSelectedListener(this);

//        arraySpinner = new String[]{"6 Months", "1 Year", "2 Years"};

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);
//        spinner.setSelection(1);
//        spinner.setOnItemSelectedListener(this);

//        prefs= getSharedPreferences("fd_bank_rakyat", MODE_PRIVATE);
//        edit= prefs.edit();

        btn_proceed_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= null;
                if (et_fd_bank_rakyat.getText().toString().isEmpty()) {
                    et_fd_bank_rakyat.setError("Please input amount");
                    et_fd_bank_rakyat.requestFocus();
                }
                else if ( (Integer.parseInt(et_fd_bank_rakyat.getText().toString()))<500) {
                    et_fd_bank_rakyat.setError("The minimum amount is RM500");
                    et_fd_bank_rakyat.requestFocus();
                    AlertDialog.Builder builder= builder = new AlertDialog.Builder(FixedDepositDetailActivity1.this);
                    builder.setMessage("For this fixed deposit, the minimum amount that you need to invest is RM 500.")
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
                else if(initializedView ==  false) {
                    Toast.makeText(getApplicationContext(),"None selected",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), FDBankRakyatActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        initializedView = false;
        String text=spinner.getItemAtPosition(position).toString();
        NumberFormat formatter = new DecimalFormat("#0.00");
        if (initializedView ==  true) {
            initializedView = false;
        }
        else {
            initializedView=true;
        if (text.equals("6 Months")){
            tv_fd_interest_rate.setText("2.15% annual interest");

            double fd= 0;
            double result6M=0;
            try {
                fd = Double.valueOf(et_fd_bank_rakyat.getText().toString());
                result6M=fd+((fd*2.15*0.5)/100);
                String k=formatter.format(result6M);
                String l=String.format("%.2f", result6M);
                sessionManager.createFDsession(l);
            } catch (NumberFormatException e) {
                Log.d(TAG,"Error");
            }
        }
        else if (text.equals("1 Year")){
            tv_fd_interest_rate.setText("2.4% annual interest");
            initializedView=true;
            double fd= 0;
            try {
                fd = Double.valueOf(et_fd_bank_rakyat.getText().toString());
                result=fd+((fd*2.4*1)/100);
                String k=formatter.format(result);
                sessionManager.createFDsession(k);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        else if (text.equals("2 Years")) {
            tv_fd_interest_rate.setText("2.5% annual interest");
            initializedView=true;
            double fd = 0;
            try {
                fd = Double.valueOf(et_fd_bank_rakyat.getText().toString());
                result = fd + ((fd * 2.5 * 2) / 100);
                String l = String.format("%.2f", result);
                String resultS = String.valueOf(result);
                sessionManager.createFDsession(l);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        TextView errorText = (TextView)spinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);
        errorText.setText("Nothing selected");
    }


}