package com.example.smartfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfinance.Database.SessionManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class ReportActivity extends AppCompatActivity {


    TextView tv_current_date, tv_total_monthly, tv_monthly_food_drink, tv_monthly_transport, tv_monthly_house, tv_monthly_bills, tv_monthly_health, tv_monthly_travel, tv_monthly_ent, tv_monthly_invest, tv_monthly_others;
    TextView tv_monthly_salary, tv_monthly_gift, tv_monthly_business, tv_monthly_dividends, tv_monthly_others_income, tv_total_monthly_income, tv_test;
    TextView tv_monthly_expense,tv_monthly_income,tv_monthly_net_spending;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    PieChart pieChart,pieChartIncome,pieChartNetSpend;

    ProgressBar progressBar_food_drink, progressBar_transport, progressBar_house, progressBar_bills, progressBar_health, progressBar_travel, progressBar_ent, progressBar_invest, progressBar_others;
    ProgressBar progressBar_salary, progressBar_gift, progressBar_business, progressBar_dividends, progressBar_others_income;
    ProgressBar progressBar_income,progressBar_expense;


    String userID;
    public String totalExpenseString;
    String totalIncomeString = "";
    int mMonth, mYear;
    double totalExpense;
    double totalIncome = 0.0;
    Typeface tfLight;
    double kk = 0;
    double kk2 = 0;
    double price, price2;
    double fullInt;
    double fullIncome = 0.0;
    String keep;

    ArrayList<PieEntry> expense_category,income_category,total_monthly_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        tv_current_date = findViewById(R.id.tv_current_date);
        tv_total_monthly = findViewById(R.id.tv_total_monthly);
        tv_monthly_food_drink = findViewById(R.id.tv_monthly_food_drink_expense);
        tv_monthly_transport = findViewById(R.id.tv_monthly_transport_expense);
        tv_monthly_house = findViewById(R.id.tv_monthly_house_expense);
        tv_monthly_bills = findViewById(R.id.tv_monthly_bills_expense);
        tv_monthly_health = findViewById(R.id.tv_monthly_health_expense);
        tv_monthly_travel = findViewById(R.id.tv_monthly_travel_expense);
        tv_monthly_ent = findViewById(R.id.tv_monthly_ent_expense);
        tv_monthly_invest = findViewById(R.id.tv_monthly_invest_expense);
        tv_monthly_others = findViewById(R.id.tv_monthly_others_expense);

        tv_monthly_salary = findViewById(R.id.tv_monthly_salary_income);
        tv_monthly_gift = findViewById(R.id.tv_monthly_gift_income);
        tv_monthly_business = findViewById(R.id.tv_monthly_business_income);
        tv_monthly_dividends = findViewById(R.id.tv_monthly_dividends_income);
        tv_monthly_others_income = findViewById(R.id.tv_monthly_others_income);
        tv_total_monthly_income = findViewById(R.id.tv_total_monthly_income);

        tv_monthly_expense=findViewById(R.id.tv_monthly_expense2);
        tv_monthly_income=findViewById(R.id.tv_monthly_income2);
        tv_monthly_net_spending=findViewById(R.id.tv_monthly_net_spend);

        progressBar_food_drink = findViewById(R.id.progressBar_food_drink_monthly_expense);
        progressBar_transport = findViewById(R.id.progress_bar_transport_monthly_expense);
        progressBar_house = findViewById(R.id.progress_bar_house_monthly_expense);
        progressBar_bills = findViewById(R.id.progress_bar_bills_monthly_expense);
        progressBar_health = findViewById(R.id.progress_bar_health_monthly_expense);
        progressBar_travel = findViewById(R.id.progress_bar_travel_monthly_expense);
        progressBar_ent = findViewById(R.id.progress_bar_ent_monthly_expense);
        progressBar_invest = findViewById(R.id.progress_bar_invest_monthly_expense);
        progressBar_others = findViewById(R.id.progress_bar_others_monthly_expense);

        progressBar_salary = findViewById(R.id.progressBar_salary_monthly_income);
        progressBar_gift = findViewById(R.id.progressBar_gift_monthly_income);
        progressBar_business = findViewById(R.id.progressBar_business_monthly_income);
        progressBar_dividends = findViewById(R.id.progressBar_dividends_monthly_income);
        progressBar_others_income = findViewById(R.id.progressBar_others_monthly_income);

        progressBar_income=findViewById(R.id.progressBar_income);
        progressBar_expense=findViewById(R.id.progressBar_expense);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> expenseTotalDetail = sessionManager.getExpenseTotal();
        String expenseTotal = expenseTotalDetail.get(SessionManager.KEY_MONTHLY_EXPENSE);
        double expenseTotalDouble = Double.parseDouble(expenseTotal);

        HashMap<String, String> incomeTotalDetail = sessionManager.getIncomeTotal();
        String incomeTotal = incomeTotalDetail.get(SessionManager.KEY_MONTHLY_INCOME);
        double incomeTotalDouble = Double.parseDouble(incomeTotal);

        //pie chart
        pieChart = findViewById(R.id.pie_chart_monthly_expense);
        pieChartIncome=findViewById(R.id.pie_chart_monthly_income);
        pieChartNetSpend=findViewById(R.id.pie_chart_monthly_net_spend);

        //pie chart for expense monthly
        expense_category = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        PieDataSet pieDataSet = new PieDataSet(expense_category, "Category");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setValueTypeface(tfLight);;
        PieData pieData = new PieData(pieDataSet);

        Legend legend = pieChart.getLegend();

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        pieChart.setTouchEnabled(true);
        pieChart.setData(pieData);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawEntryLabels(false);//remove textlabel on pie chart
        pieDataSet.setDrawValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Monthly Expense");
        pieChart.setTransparentCircleRadius(1f);
        IMarker mv = new MyMarkerView(ReportActivity.this, R.layout.custom_marker_view_layout);
        pieChart.setMarker(mv);
        pieChart.animateXY(1000, 1000);


        //pie chart for monthly income
        income_category = new ArrayList<>();

        PieDataSet pieDataSetIncome = new PieDataSet(income_category, "Category");
        pieDataSetIncome.setColors(colors);
        pieDataSetIncome.setValueTextColor(Color.BLACK);
        pieDataSetIncome.setValueTextSize(12f);

        pieDataSet.setValueTypeface(tfLight);
        PieData pieDataIncome = new PieData(pieDataSetIncome);
        Legend legend2 = pieChartIncome.getLegend();

        legend2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend2.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend2.setDrawInside(false);

        pieChartIncome.setTouchEnabled(true);
        pieChartIncome.setData(pieDataIncome);
        pieChartIncome.setDrawCenterText(true);
        pieChartIncome.setDrawEntryLabels(false);//remove textlabel on pie chart
        pieDataSetIncome.setDrawValues(false);
        pieChartIncome.getDescription().setEnabled(false);
        pieChartIncome.setCenterText("Monthly Income");
        pieChartIncome.setTransparentCircleRadius(1f);
        IMarker mv2 = new MyMarkerView(ReportActivity.this, R.layout.custom_marker_view_layout);
        pieChartIncome.setMarker(mv2);
        pieChartIncome.animateXY(1000, 1000);

        total_monthly_category = new ArrayList<>();
        PieDataSet pieDataSetMonthly = new PieDataSet(total_monthly_category, "Category");
        pieDataSetMonthly.setColors(colors);
        pieDataSetMonthly.setValueTextColor(Color.BLACK);
        pieDataSetMonthly.setValueTextSize(12f);
        pieDataSetMonthly.setValueTypeface(tfLight);
        pieDataSetMonthly.setDrawValues(false);

        PieData pieDataMonthly = new PieData(pieDataSetMonthly);

        Legend legend3 = pieChartNetSpend.getLegend();
        legend3.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend3.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend3.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend3.setDrawInside(false);

        pieChartNetSpend.setTouchEnabled(true);
        pieChartNetSpend.setData(pieDataMonthly);
        pieChartNetSpend.setDrawCenterText(true);
        pieChartNetSpend.setDrawEntryLabels(false);//remove textlabel on pie chart
        pieChartNetSpend.getDescription().setEnabled(false);
        pieChartNetSpend.setCenterText("Monthly Net");
        pieChartNetSpend.setTransparentCircleRadius(1f);
        IMarker mv3 = new MyMarkerView(ReportActivity.this, R.layout.custom_marker_view_layout);
        pieChartNetSpend.setMarker(mv3);
        pieChartNetSpend.animateXY(1000, 1000);


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
        tv_current_date.setText(day + month + year);

        //Calculate monthly net spending
        double net = incomeTotalDouble-expenseTotalDouble;
        double fullTotal=incomeTotalDouble+expenseTotalDouble;
        String incomeToString=String.format(Locale.US, "%.2f",incomeTotalDouble);
        tv_monthly_income.setText(""+incomeToString);
        String expenseToString=String.format(Locale.US, "%.2f",expenseTotalDouble);
        tv_monthly_expense.setText(""+expenseToString);
        String netString=String.format(Locale.US, "%.2f", net);
        tv_monthly_net_spending.setText("RM "+ netString);

        int pIncome= (int) (((incomeTotalDouble * 1.0) / (fullTotal * 1.0)) * 100);
        int pIncome2 = (int) Math.round(pIncome);
        progressBar_income.setProgress(pIncome);

        double pExpense = (double) ((expenseTotalDouble/fullTotal) * 100);
        int pExpense2 = (int) Math.round(pExpense);
        progressBar_expense.setProgress(pExpense2);

        pieDataSetMonthly.addEntry(new PieEntry(pIncome, "Income"));
        pieDataSetMonthly.addEntry(new PieEntry(pExpense2, "Expense"));

        DecimalFormat formater = new DecimalFormat("0.0");

        CollectionReference collectionReference = mFirestore.collection("Transaction Record")
                .document(userID).collection("Expense");

        Task<QuerySnapshot> totalRef = collectionReference.whereEqualTo("TransactionYear", mYear)
                .whereEqualTo("TransactionMonth", mMonth)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalExpense += itemCost;

                            }
                            fullInt = totalExpense;
                            totalExpenseString = String.format(Locale.US, "%.2f", totalExpense);
                            tv_total_monthly.setText(totalExpenseString);
                        }
                    }
                });


        Task<QuerySnapshot> fooddrinkRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Food & Drink")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalFood = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalFood += itemCost;
                            }
//                            String total2 = String.valueOf(totalFood);
                            String total2= String.format(Locale.US, "%.2f", totalFood);
                            tv_monthly_food_drink.setText(total2);
                            double t = (double) ((totalFood / (expenseTotalDouble * 1.0)) * 100);
                            int tt = (int) t;
                            progressBar_food_drink.setProgress(tt);
                            pieDataSet.addEntry(new PieEntry(tt, "Food & Drink"));
                            pieData.notifyDataChanged(); // let the data know a dataSet changed
                            pieChart.notifyDataSetChanged(); // let the chart know it's data changed
                            pieChart.invalidate(); // refresh
                        }
                    }
                });

        Task<QuerySnapshot> transportRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Transport")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalTrans = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalTrans += itemCost;
                            }
//                            String total2 = String.valueOf(totalTrans);
                            String total2= String.format(Locale.US, "%.2f", totalTrans);
                            tv_monthly_transport.setText(total2);
                            double t = (double) ((totalTrans / (expenseTotalDouble * 1.0)) * 100);
                            int tt = (int) t;
                            progressBar_transport.setProgress(tt);
                            pieDataSet.addEntry(new PieEntry(tt, "Transport"));
                            pieData.notifyDataChanged(); // let the data know a dataSet changed
                            pieChart.notifyDataSetChanged(); // let the chart know it's data changed
                            pieChart.invalidate(); // refresh

                        }
                    }
                });

        Task<QuerySnapshot> houseRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "House")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalHouse = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalHouse += itemCost;
                            }
//                            String total2 = String.valueOf(totalHouse);
                            String total2= String.format(Locale.US, "%.2f", totalHouse);
                            tv_monthly_house.setText(total2);
                            int t3 = (int) (((totalHouse * 1.0) / (expenseTotalDouble * 1.0)) * 100);
                            progressBar_house.setProgress(t3);
                            pieDataSet.addEntry(new PieEntry(t3, "House"));
                            pieData.notifyDataChanged();
                            pieChart.notifyDataSetChanged();
                            pieChart.invalidate();
                        }
                    }
                });

        Task<QuerySnapshot> billsRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Bills")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalBills = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalBills += itemCost;
                            }
//                            String total2 = String.valueOf(totalBills);
                            String total2= String.format(Locale.US, "%.2f", totalBills);
                            tv_monthly_bills.setText(total2);
                            int t4 = (int) (((totalBills * 1.0) / (expenseTotalDouble * 1.0)) * 100);
                            progressBar_bills.setProgress(t4);
                            pieDataSet.addEntry(new PieEntry(t4, "Bills"));
                            pieData.notifyDataChanged();
                            pieChart.notifyDataSetChanged();
                            pieChart.invalidate();
                        }
                    }
                });

        Task<QuerySnapshot> healthRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Health")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalHealth = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalHealth += itemCost;
                            }
//                            String total2 = String.valueOf(totalHealth);
                            String total2= String.format(Locale.US, "%.2f", totalHealth);
                            tv_monthly_health.setText(total2);
                            double t5 = (double) ((totalHealth / expenseTotalDouble) * 100);
                            int tt5 = (int) Math.round(t5);
                            progressBar_health.setProgress(tt5);
                            pieDataSet.addEntry(new PieEntry(tt5, "Health"));
                            pieData.notifyDataChanged();
                            pieChart.notifyDataSetChanged();
                            pieChart.invalidate();
                        }
                    }
                });

        Task<QuerySnapshot> travelRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Travel")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalTra = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalTra += itemCost;
                            }
//                            String total2 = String.valueOf(totalTra);
                            String total2= String.format(Locale.US, "%.2f", totalTra);
                            tv_monthly_travel.setText(total2);
                            double t6 = (double) ((totalTra / expenseTotalDouble) * 100);
                            int tt6 = (int) Math.round(t6);
                            progressBar_travel.setProgress(tt6);
                            pieDataSet.addEntry(new PieEntry(tt6, "Travel"));
                            pieData.notifyDataChanged();
                            pieChart.notifyDataSetChanged();
                            pieChart.invalidate();
                        }
                    }
                });

        Task<QuerySnapshot> entRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Entertainment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalEnt = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalEnt += itemCost;
                            }
//                            String total2 = String.valueOf(totalEnt);
                            String total2= String.format(Locale.US, "%.2f", totalEnt);
                            tv_monthly_ent.setText(total2);
                            double t7 = (double) ((totalEnt / expenseTotalDouble) * 100);
                            int tt7 = (int) Math.round(t7);
                            progressBar_ent.setProgress(tt7);
                            pieDataSet.addEntry(new PieEntry(tt7, "Entertainment"));
                            pieData.notifyDataChanged();
                            pieChart.notifyDataSetChanged();
                            pieChart.invalidate();
                        }
                    }
                });
        Task<QuerySnapshot> investRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Investment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalInv = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalInv += itemCost;
                            }
//                            String total2 = String.valueOf(totalInv);
                            String total2= String.format(Locale.US, "%.2f", totalInv);
                            tv_monthly_invest.setText(total2);
                            double t8 = (double) ((totalInv / expenseTotalDouble) * 100);
                            int tt8 = (int) Math.round(t8);
                            progressBar_invest.setProgress(tt8);
                            pieDataSet.addEntry(new PieEntry(tt8, "Investment"));
                            pieData.notifyDataChanged();
                            pieChart.notifyDataSetChanged();
                            pieChart.invalidate();
                        }
                    }
                });

        Task<QuerySnapshot> otherRef = collectionReference.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Others")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalOthersExp = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalOthersExp += itemCost;
                            }
//                            String total2 = String.valueOf(totalOthersExp);
                            String total2= String.format(Locale.US, "%.2f", totalOthersExp);
                            tv_monthly_others.setText(total2);
                            double t9 = (double) ((totalOthersExp / expenseTotalDouble) * 100);
                            int tt9 = (int) Math.round(t9);
                            progressBar_others.setProgress(tt9);
                            pieDataSet.addEntry(new PieEntry(tt9, "Others"));
                            pieData.notifyDataChanged();
                            pieChart.notifyDataSetChanged();
                            pieChart.invalidate();
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
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalIncome += itemCost;
                            }

                            totalIncomeString = String.format(Locale.US, "%.2f", totalIncome);
                            tv_total_monthly_income.setText(totalIncomeString);
                            fullIncome = Double.parseDouble(totalIncomeString);
//                            pieDataSetMonthly.addEntry(new PieEntry(pIncome, "Income"));
//                            pieDataMonthly.notifyDataChanged(); // let the data know a dataSet changed
//                            pieChartNetSpend.notifyDataSetChanged(); // let the chart know it's data changed
//                            pieChartNetSpend.invalidate(); // refresh
                        }
                    }
                });

        Task<QuerySnapshot> salaryRef = collectionReference2.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Salary")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalSalary = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalSalary += itemCost;
                            }
//                            String total2 = String.valueOf(totalSalary);
                            String total2= String.format(Locale.US, "%.2f", totalSalary);
                            tv_monthly_salary.setText(total2);
                            double tIncome = (double) ((totalSalary / incomeTotalDouble) * 100);
                            int ttIncome = (int) Math.round(tIncome);
                            progressBar_salary.setProgress(ttIncome);
                            pieDataSetIncome.addEntry(new PieEntry(ttIncome, "Salary"));
                            pieDataIncome.notifyDataChanged(); // let the data know a dataSet changed
                            pieChartIncome.notifyDataSetChanged(); // let the chart know it's data changed
                            pieChartIncome.invalidate(); // refresh
                        }
                    }
                });

        Task<QuerySnapshot> giftRef = collectionReference2.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Gift")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalGift = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalGift += itemCost;
                            }
//                            String total2 = String.valueOf(totalGift);
                            String total2= String.format(Locale.US, "%.2f", totalGift);
                            tv_monthly_gift.setText(total2);
                            double tIncome2 = (double) ((totalGift / incomeTotalDouble) * 100);
                            int ttIncome2 = (int) Math.round(tIncome2);
                            progressBar_gift.setProgress(ttIncome2);
                            pieDataSetIncome.addEntry(new PieEntry(ttIncome2, "Gift"));
                            pieDataIncome.notifyDataChanged(); // let the data know a dataSet changed
                            pieChartIncome.notifyDataSetChanged(); // let the chart know it's data changed
                            pieChartIncome.invalidate(); // refresh
                        }
                    }
                });

        Task<QuerySnapshot> businessRef = collectionReference2.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Business")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalBus = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalBus += itemCost;
                            }
//                            String total2 = String.valueOf(totalBus);
                            String total2= String.format(Locale.US, "%.2f", totalBus);
                            tv_monthly_business.setText(total2);
                            double tIncome3 = (double) ((totalBus / incomeTotalDouble) * 100);
                            int ttIncome3 = (int) Math.round(tIncome3);
                            progressBar_business.setProgress(ttIncome3);
                            pieDataSetIncome.addEntry(new PieEntry(ttIncome3, "Business"));
                            pieDataIncome.notifyDataChanged(); // let the data know a dataSet changed
                            pieChartIncome.notifyDataSetChanged(); // let the chart know it's data changed
                            pieChartIncome.invalidate(); // refresh
                        }
                    }
                });

        Task<QuerySnapshot> dividendsRef = collectionReference2.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Dividends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalDiv = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalDiv += itemCost;
                            }
//                            String total2 = String.valueOf(totalDiv);
                            String total2= String.format(Locale.US, "%.2f", totalDiv);
                            tv_monthly_dividends.setText(total2);
                            double tIncome4 = (double) ((totalDiv / incomeTotalDouble) * 100);
                            int ttIncome4 = (int) Math.round(tIncome4);
                            progressBar_dividends.setProgress(ttIncome4);
                            pieDataSetIncome.addEntry(new PieEntry(ttIncome4, "Dividends"));
                            pieDataIncome.notifyDataChanged(); // let the data know a dataSet changed
                            pieChartIncome.notifyDataSetChanged(); // let the chart know it's data changed
                            pieChartIncome.invalidate(); // refresh
                        }
                    }
                });

        Task<QuerySnapshot> othersIncomeRef = collectionReference2.whereEqualTo("TransactionYear", mYear).whereEqualTo("TransactionMonth", mMonth).whereEqualTo("TransactionCategory", "Others")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalOthersInc = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String amount = document.getString("TransactionAmount");
                                double itemCost = Double.parseDouble(amount);
                                totalOthersInc += itemCost;
                            }
//                            String total2 = String.valueOf(totalOthersInc);
                            String total2= String.format(Locale.US, "%.2f", totalOthersInc);
                            tv_monthly_others_income.setText(total2);
                            double tIncome5 = (double) ((totalOthersInc / incomeTotalDouble) * 100);
                            int ttIncome5 = (int) Math.round(tIncome5);
                            progressBar_others_income.setProgress(ttIncome5);
                            pieDataSetIncome.addEntry(new PieEntry(ttIncome5, "Others"));
                            pieDataIncome.notifyDataChanged(); // let the data know a dataSet changed
                            pieChartIncome.notifyDataSetChanged(); // let the chart know it's data changed
                            pieChartIncome.invalidate(); // refresh
                        }
                    }
                });






    }

    public void onBackPressed() {
        finish();
    }

//    public void onValueSelected(Entry e, Highlight h) {
//        // enter your code here
////        pieChart.setDrawEntryLabels(true);
//        if (e == null)
//            return;
//        Log.i("VAL SELECTED",
//                "Value: " + e.getY() + ", index: " + h.getX()
//                        + ", DataSet index: " + h.getDataSetIndex());
//
//    }
//
//
//    public void onNothingSelected() {
//        // do nothing
//    }

}