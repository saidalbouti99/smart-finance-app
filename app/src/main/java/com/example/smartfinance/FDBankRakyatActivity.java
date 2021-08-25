package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartfinance.Database.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class FDBankRakyatActivity extends AppCompatActivity {
    TextView tv_fd_result;
    Button btn_try_again,btn_try_other;
    double fdTotal,fdTotal2;
    float fdTotal3;
    String m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_d_bank_rakyat);
        tv_fd_result=findViewById(R.id.tv_fd_bank_rakyat_amount_result);
        btn_try_again=findViewById(R.id.btn_try_again_fd_bank_rakyat);
        btn_try_other=findViewById(R.id.btn_try_again_simulator);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> fdTotalDetail = sessionManager.getFDResult();
        String fdTotalString = fdTotalDetail.get(SessionManager.KEY_FD_BANK_RAKYAT_AMOUNT);
        NumberFormat formatter = new DecimalFormat("#0.00");
        DecimalFormat formater = new DecimalFormat("#0.00");
        fdTotal = Double.parseDouble(fdTotalString);
//        fdTotal2=round(fdTotal,2);
        String f=formater.format(fdTotal);
//        fdTotal2 = Double.valueOf(f);
        fdTotal3=Float.valueOf(f);

        DecimalFormat decim = new DecimalFormat("0.00");
        Double price2 = Double.parseDouble(decim.format(fdTotal));

        System.out.println(" "+fdTotalString+" "+fdTotal+" "+f+" "+fdTotal3+" "+price2);
//
//        SharedPreferences bb = getSharedPreferences("fd_bank_rakyat", 0);
//        m = bb.getString("fd_amount", "");

        startCountAnimation();

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FixedDepositDetailActivity1.class));
                finish();
            }
        });

        btn_try_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SimulationActivity.class));
                finish();
            }
        });
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();}

    private void startCountAnimation() {

        float f = (float)fdTotal;


        System.out.println(f);
//        DecimalFormat formater = new DecimalFormat("#0.00");
//        String g=formater.format(f);
//        float gg= Float.valueOf(g);
//        float f1 = Float.parseFloat(m);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, f); //0 is min number, 600 is max number
        animator.setDuration(3000); //Duration is in milliseconds
        animator.setEvaluator(new TypeEvaluator<Float>() {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue) {
                return (startValue + (endValue - startValue) * fraction);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_fd_result.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }


}