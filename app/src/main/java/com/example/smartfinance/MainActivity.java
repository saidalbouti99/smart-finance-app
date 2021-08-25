package com.example.smartfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smartfinance.Database.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    CircleMenu circleMenu;
    CardView card_report, card_calculator_main, card_sim_main,card_daily_report;

   MaterialButton btn_menu_reports,btn_tips,btn_daily_reports,btn_menu_calculator,btn_menu_simulator,btn_menu_add_trans;
    SessionManager sessionManager;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    String userID;
    int mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleMenu = findViewById(R.id.circle_menu);

        btn_tips=findViewById(R.id.btn_menu_tips);
        btn_menu_reports=findViewById(R.id.btn_menu_reports);
        btn_daily_reports=findViewById(R.id.btn_menu_daily_reports);
        btn_menu_simulator=findViewById(R.id.btn_menu_simulator);
        btn_menu_calculator=findViewById(R.id.btn_menu_calculator);
        btn_menu_add_trans=findViewById(R.id.btn_menu_add_trans);

        mFirebaseAuth = FirebaseAuth.getInstance();
        userID = mFirebaseAuth.getCurrentUser().getUid();
        mFirestore = FirebaseFirestore.getInstance();

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        String month = String.valueOf(currentMonth);
        mMonth = Integer.parseInt(month);
        String day = String.valueOf(currentDay);
        String year = String.valueOf(currentYear);
        mYear = Integer.parseInt(year);

        sessionManager = new SessionManager(MainActivity.this);

        btn_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TipsActivity.class));
            }
        });

        btn_menu_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReportActivity.class));
            }
        });

        btn_daily_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DailyReportMainActivity.class));
            }
        });

        btn_menu_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CalculatorActivity.class));
            }
        });

        btn_menu_simulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SimulationActivity.class));
            }
        });

        btn_menu_add_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),AddTransactionActivity.class));
//                finish();
            }
        });

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_ic_actions_menu, R.drawable.ic_ic_actions_close_simple)
                .addSubMenu(Color.parseColor("#88bef5"), R.drawable.ic_assessment_24px)
                .addSubMenu(Color.parseColor("#1DC18E"), R.drawable.ic_add_24px)
                .addSubMenu(Color.parseColor("#83e85a"), R.drawable.ic_info_24px___copy)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.ic_trending_up_24px)
                .addSubMenu(Color.parseColor("#ba53de"), R.drawable.ic_calculate_24px)
                .addSubMenu(Color.parseColor("#ff8a5c"), R.drawable.ic_ic_actions_log_out)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {

                        switch (index) {

                            case 0:
                                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(getApplicationContext(), AddTransactionActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(getApplicationContext(), TipsActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(getApplicationContext(), SimulationActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(getApplicationContext(), CalculatorActivity.class));
                                break;
                            case 5:
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                        }
                    }
                });
        CollectionReference collectionReference = mFirestore.collection("Transaction Record").document(userID).collection("Expense");

        DecimalFormat formater = new DecimalFormat("0.0");

        Task<QuerySnapshot> totalExpenseRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalExpense = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalExpense += itemCost;

                            }
                            String totalExpenseString = String.valueOf(formater.format(totalExpense));
                            sessionManager.createExpenseTotal(totalExpenseString);

                        }

                    }
                });

        CollectionReference collectionReference2 = mFirestore.collection("Transaction Record").document(userID).collection("Income");

        Task<QuerySnapshot> totalIncomeRef = collectionReference2.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalIncome = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalIncome += itemCost;

                            }
                            String totalIncomeString = String.valueOf(formater.format(totalIncome));
                            sessionManager.createIncomeTotal(totalIncomeString);

                        }

                    }
                });

    }
}