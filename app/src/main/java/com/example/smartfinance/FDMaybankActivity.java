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

import java.util.HashMap;

public class FDMaybankActivity extends AppCompatActivity {
    TextView tv_fd_result;
    double fdTotal;
    Button btn_try_again,btn_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_d_maybank);

        tv_fd_result=findViewById(R.id.tv_fd_maybank_amount_result);
        btn_try_again=findViewById(R.id.btn_try_again_fd_maybank);
        btn_other=findViewById(R.id.btn_try_other_simulator);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> fdTotalDetail = sessionManager.getFDMaybankResult();
        String fdTotalString = fdTotalDetail.get(SessionManager.KEY_FD_MAYBANK_AMOUNT);
        fdTotal = Double.parseDouble(fdTotalString);

        startCountAnimation();

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FixedDepositDetailActivity2.class));
                finish();
            }
        });


        btn_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SimulationActivity.class));
                finish();

            }
        });
    }

    private void startCountAnimation() {
        float f = (float)fdTotal;
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