package com.example.smartfinance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class PortfolioModerateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "VC_AMOUNT";
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    Spinner spinner;
    Button btn_proceed;
    int amount;
    double result=0;
    SessionManager sessionManager;
    private boolean initializedView = false;

    EditText et_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_moderate);
        spinner=findViewById(R.id.spinner_moderate);
        et_amount=findViewById(R.id.et_robo_moderate_amount);
        btn_proceed=findViewById(R.id.btn_proceed_moderate);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        sessionManager = new SessionManager(PortfolioModerateActivity.this);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.veryconservative, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        int initialposition=spinner.getSelectedItemPosition();
        spinner.setSelection(initialposition, false);
        spinner.setOnItemSelectedListener(this);

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= null;
                if ( et_amount.getText().toString().isEmpty()) {
                    et_amount.setError("Please input amount");
                    et_amount.requestFocus();
                }
                else if ( (Integer.parseInt( et_amount.getText().toString()))<100) {
                    et_amount.setError("The minimum amount is RM100");
                    et_amount.requestFocus();
                    AlertDialog.Builder builder= builder = new AlertDialog.Builder(PortfolioModerateActivity.this);
                    builder.setMessage("For this investment, the minimum amount that you need to invest is RM 100.")
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
                    Intent intent = new Intent(getApplicationContext(), PortfolioModerateResultActivity.class);
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
            if (text.equals("1 Year")){

                double vc= 0;
                double result=0;
                try {
                    vc = Double.valueOf(et_amount.getText().toString());
                    result=Math.round(1.0722*vc);
                    String n=String.valueOf(result);
                    sessionManager.createMsession(n);


                } catch (NumberFormatException e) {
                    Log.d(TAG,"Error");
                }

            }
            else if (text.equals("2 Years")){
                initializedView=true;
                double vc= 0;
                double result=0;
                double result2=0;
                try {
                    vc = Double.valueOf(et_amount.getText().toString());
                    result=1.0722*vc;
                    result2=Math.round(1.0722*result);
                    String n=String.valueOf(result2);
                    sessionManager.createMsession(n);

                } catch (NumberFormatException e) {
                    Log.d(TAG,"Error");
                }
            }
            else if (text.equals("5 Years")) {
                initializedView=true;
                double vc= 0;
                double result=0;
                double result2=0;
                try {
                    vc = Double.valueOf(et_amount.getText().toString());
                    result=1.0722*vc;
                    result2=1.0722*result;
                    result2=1.0722*result2;
                    result2=1.0722*result2;
                    result2=Math.round(1.0722*result2);
                    String n=String.valueOf(result2);
                    sessionManager.createMsession(n);

                } catch (NumberFormatException e) {
                    Log.d(TAG,"Error");
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        TextView errorText = (TextView)spinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);//just to highlight that this is an error
        errorText.setText("my actual error text");//changes the selected item text to this
    }


}