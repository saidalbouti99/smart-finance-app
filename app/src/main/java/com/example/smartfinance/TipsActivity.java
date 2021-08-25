package com.example.smartfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class TipsActivity extends AppCompatActivity {
    private static final String TAG = "TIPS_TEXT";
    Button btn_next_tips;
    TextView tv_tips;
    FirebaseFirestore mFirestore;
    String randomTips;
    private  List<Tips> allTipsList=new ArrayList<>();
    private int totalNumberTips=11;
    private List<Tips> tips=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        tv_tips=findViewById(R.id.tv_tips_tips);
        btn_next_tips=findViewById(R.id.btn_next_tips);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference tipsCollectionReference = rootRef.collection("Tips");
        tipsCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                  allTipsList=task.getResult().toObjects(Tips.class);
                    pickTips();
                } else {
                    Log.d(TAG, "Error getting documents: "+ task.getException());
                }
            }
        });
        btn_next_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    private void pickTips() {
        for (int i=0;i<totalNumberTips;i++){
            int randomNumber=getRandomInteger(allTipsList.size(),0);
            tips.add(allTipsList.get(randomNumber));
            Log.d(TAG, "Questions: "+ allTipsList.get(i).getTipsText());
            loadTips(randomNumber);
        }
    }

    private void loadTips(int i){
        tv_tips.setText(allTipsList.get(i).getTipsText());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_tips.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }
    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum-minimum)))+minimum;
    };
}