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

public class PortfolioModerateResultActivity extends AppCompatActivity {
    TextView tv_vc_result;
    Button btn_try_again,btn_try_other;
    double mTotal,mTotal2;
    float mTotal3;
    String m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_moderate_result);
        tv_vc_result=findViewById(R.id.tv_robo_mod_amount_result);
        btn_try_again=findViewById(R.id.btn_try_again_moderate);
        btn_try_other=findViewById(R.id.btn_try_again_robo_moderate_simulator);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> mTotalDetail = sessionManager.getMresult();
        String mTotalString = mTotalDetail.get(SessionManager.KEY_RA_MODERATE_AMOUNT);
        NumberFormat formatter = new DecimalFormat("#0.00");
        DecimalFormat formater = new DecimalFormat("#0.00");
        mTotal = Double.parseDouble(mTotalString);
        String f=formater.format(mTotal);
        mTotal3=Float.valueOf(f);

        DecimalFormat decim = new DecimalFormat("0.00");
        Double price2 = Double.parseDouble(decim.format(mTotal));

        System.out.println(" "+mTotalString+" "+mTotal+" "+f+" "+mTotal3+" "+price2);
        startCountAnimation();

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PortfolioModerateActivity.class));
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

        int f = (int)mTotal;
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