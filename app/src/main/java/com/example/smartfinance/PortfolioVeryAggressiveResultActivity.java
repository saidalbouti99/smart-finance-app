package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartfinance.Database.SessionManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class PortfolioVeryAggressiveResultActivity extends AppCompatActivity {
    TextView tv_vc_result;
    Button btn_try_again,btn_try_other;
    double vaTotal,vaTotal2;
    float vaTotal3;
    String m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_very_aggressive_result);
        tv_vc_result=findViewById(R.id.tv_robo_va_amount_result);
        btn_try_again=findViewById(R.id.btn_try_again_very_aggr);
        btn_try_other=findViewById(R.id.btn_try_again_robo_va_simulator);


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> vaTotalDetail = sessionManager.getVAresult();
        String vaTotalString = vaTotalDetail.get(SessionManager.KEY_RA_VERY_AGGRESSIVE_AMOUNT);
        NumberFormat formatter = new DecimalFormat("#0.00");
        DecimalFormat formater = new DecimalFormat("#0.00");
        vaTotal = Double.parseDouble(vaTotalString);

        DecimalFormat decim = new DecimalFormat("0.00");
        Double price2 = Double.parseDouble(decim.format(vaTotal));

        startCountAnimation();

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PortfolioVeryAggressiveActivity.class));
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

        int f = (int)vaTotal;
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