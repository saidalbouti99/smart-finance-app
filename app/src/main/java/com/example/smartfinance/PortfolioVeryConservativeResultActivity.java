package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartfinance.Database.SessionManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class PortfolioVeryConservativeResultActivity extends AppCompatActivity {
    TextView tv_vc_result;
    Button btn_try_again,btn_try_other;
    double vcTotal,vcTotal2;
    float vcTotal3;
    String m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_very_conservative_result);
        tv_vc_result=findViewById(R.id.tv_robo_vc_amount_result);
        btn_try_again=findViewById(R.id.btn_try_again_very_cons);
        btn_try_other=findViewById(R.id.btn_try_again_robo_simulator);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> vcTotalDetail = sessionManager.getVCresult();
        String vcTotalString = vcTotalDetail.get(SessionManager.KEY_RA_VERY_CONSERVATIVE_AMOUNT);
        NumberFormat formatter = new DecimalFormat("#0.00");
        DecimalFormat formater = new DecimalFormat("#0.00");
        vcTotal = Double.parseDouble(vcTotalString);
        String f=formater.format(vcTotal);
        vcTotal3=Float.valueOf(f);

        DecimalFormat decim = new DecimalFormat("0.00");
        Double price2 = Double.parseDouble(decim.format(vcTotal));

        System.out.println(" "+vcTotalString+" "+vcTotal+" "+f+" "+vcTotal3+" "+price2);
        startCountAnimation();

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PortfolioVeryConservativeActivity.class));
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


    private void startCountAnimation() {

        int f = (int)vcTotal;
        System.out.println(f);
        ValueAnimator animator = ValueAnimator.ofInt(0, f); //0 is min number, 600 is max number
        animator.setDuration(3000); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_vc_result.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
}