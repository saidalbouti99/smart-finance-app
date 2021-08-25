package com.example.smartfinance.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences monthlyExpenseSessions;
    SharedPreferences.Editor editor;
    Context context;

//    private static final String MONTHLY_EXPENSE_TOTAL="";

    public static final String KEY_MONTHLY_EXPENSE = "expense";
    public static final String KEY_MONTHLY_FOOD = "food";
    public static final String KEY_MONTHLY_TRANSPORT = "transport";
    public static final String KEY_MONTHLY_BILLS = "bills";
    public static final String KEY_MONTHLY_HEALTH = "health";
    public static final String KEY_MONTHLY_TRAVEL = "travel";
    public static final String KEY_MONTHLY_ENT = "ent";
    public static final String KEY_MONTHLY_INVEST = "invest";
    public static final String KEY_MONTHLY_OTHERS = "others";

    public static final String KEY_MONTHLY_INCOME = "income";
    public static final String KEY_MONTHLY_SALARY = "salary";
    public static final String KEY_MONTHLY_GIFT = "transport";
    public static final String KEY_MONTHLY_BUSINESS = "business";
    public static final String KEY_MONTHLY_DIVIDENDS = "dividends";
    public static final String KEY_MONTHLY_OTHERS_INCOME = "others_income";

    public static final String KEY_FD_BANK_RAKYAT_AMOUNT = "fd_br_amount";
    public static final String KEY_FD_MAYBANK_AMOUNT = "fd_maybank_amount";
    public static final String KEY_RA_VERY_CONSERVATIVE_AMOUNT = "robo_very_conservative";
    public static final String KEY_RA_MODERATE_AMOUNT = "robo_moderate";
    public static final String KEY_RA_VERY_AGGRESSIVE_AMOUNT = "robo_very_aggressive";

    public SessionManager(Context _context) {
        context = _context;
        monthlyExpenseSessions = context.getSharedPreferences("monthlyExpense", Context.MODE_PRIVATE);
        editor = monthlyExpenseSessions.edit();
    }

    public void createExpenseTotal(String expense) {
        editor.putString(KEY_MONTHLY_EXPENSE, expense);

        editor.commit();

    }

    public void createExpenseSession(String expense, String food, String transport, String bills, String health, String travel, String ent, String invest, String others) {
        editor.putString(KEY_MONTHLY_EXPENSE, expense);
        editor.putString(KEY_MONTHLY_FOOD, food);
        editor.putString(KEY_MONTHLY_TRANSPORT, transport);
        editor.putString(KEY_MONTHLY_BILLS, bills);
        editor.putString(KEY_MONTHLY_HEALTH, health);
        editor.putString(KEY_MONTHLY_TRAVEL, travel);
        editor.putString(KEY_MONTHLY_ENT, ent);
        editor.putString(KEY_MONTHLY_INVEST, invest);
        editor.putString(KEY_MONTHLY_OTHERS, others);

        editor.commit();

    }

    public HashMap<String, String> getExpenseTotal() {
        HashMap<String, String> expenseData2 = new HashMap<String, String>();
        expenseData2.put(KEY_MONTHLY_EXPENSE, monthlyExpenseSessions.getString(KEY_MONTHLY_EXPENSE, null));

        return expenseData2;
    }

    public HashMap<String, String> getExpenseSession() {
        HashMap<String, String> expenseData = new HashMap<String, String>();
        expenseData.put(KEY_MONTHLY_EXPENSE, monthlyExpenseSessions.getString(KEY_MONTHLY_EXPENSE, null));
        expenseData.put(KEY_MONTHLY_FOOD, monthlyExpenseSessions.getString(KEY_MONTHLY_FOOD, null));
        expenseData.put(KEY_MONTHLY_TRANSPORT, monthlyExpenseSessions.getString(KEY_MONTHLY_TRANSPORT, null));
        expenseData.put(KEY_MONTHLY_BILLS, monthlyExpenseSessions.getString(KEY_MONTHLY_BILLS, null));
        expenseData.put(KEY_MONTHLY_HEALTH, monthlyExpenseSessions.getString(KEY_MONTHLY_HEALTH, null));
        expenseData.put(KEY_MONTHLY_TRAVEL, monthlyExpenseSessions.getString(KEY_MONTHLY_TRAVEL, null));
        expenseData.put(KEY_MONTHLY_ENT, monthlyExpenseSessions.getString(KEY_MONTHLY_ENT, null));
        expenseData.put(KEY_MONTHLY_INVEST, monthlyExpenseSessions.getString(KEY_MONTHLY_INVEST, null));
        expenseData.put(KEY_MONTHLY_OTHERS, monthlyExpenseSessions.getString(KEY_MONTHLY_OTHERS, null));

        return expenseData;
    }

    public void createIncomeTotal(String income) {
        editor.putString(KEY_MONTHLY_INCOME, income);

        editor.commit();
    }

    public void createIncomeSession(String income, String salary, String gift, String business, String dividends, String others_income) {
        editor.putString(KEY_MONTHLY_INCOME, income);
        editor.putString(KEY_MONTHLY_SALARY, salary);
        editor.putString(KEY_MONTHLY_GIFT, gift);
        editor.putString(KEY_MONTHLY_BUSINESS, business);
        editor.putString(KEY_MONTHLY_DIVIDENDS, dividends);
        editor.putString(KEY_MONTHLY_OTHERS_INCOME, others_income);

        editor.commit();
    }

    public HashMap<String, String> getIncomeTotal() {
        HashMap<String, String> incomeData2 = new HashMap<String, String>();
        incomeData2.put(KEY_MONTHLY_INCOME, monthlyExpenseSessions.getString(KEY_MONTHLY_INCOME, null));

        return incomeData2;
    }

    public HashMap<String, String> getIncomeSession() {
        HashMap<String, String> incomeData = new HashMap<String, String>();
        incomeData.put(KEY_MONTHLY_INCOME, monthlyExpenseSessions.getString(KEY_MONTHLY_INCOME, null));
        incomeData.put(KEY_MONTHLY_SALARY, monthlyExpenseSessions.getString(KEY_MONTHLY_SALARY, null));
        incomeData.put(KEY_MONTHLY_GIFT, monthlyExpenseSessions.getString(KEY_MONTHLY_GIFT, null));
        incomeData.put(KEY_MONTHLY_BUSINESS, monthlyExpenseSessions.getString(KEY_MONTHLY_BUSINESS, null));
        incomeData.put(KEY_MONTHLY_DIVIDENDS, monthlyExpenseSessions.getString(KEY_MONTHLY_DIVIDENDS, null));
        incomeData.put(KEY_MONTHLY_OTHERS_INCOME, monthlyExpenseSessions.getString(KEY_MONTHLY_OTHERS_INCOME, null));

        return incomeData;
    }

    public void createFDsession(String fd_amount) {
        editor.putString(KEY_FD_BANK_RAKYAT_AMOUNT, fd_amount);
        editor.commit();
    }

    public HashMap<String, String> getFDResult() {
        HashMap<String, String> fdAmountData = new HashMap<String, String>();
        fdAmountData.put(KEY_FD_BANK_RAKYAT_AMOUNT, monthlyExpenseSessions.getString(KEY_FD_BANK_RAKYAT_AMOUNT, null));
        return fdAmountData;
    }

    public void createFDMaybanksession(String fd_amount) {
        editor.putString(KEY_FD_MAYBANK_AMOUNT, fd_amount);
        editor.commit();
    }

    public HashMap<String, String> getFDMaybankResult() {
        HashMap<String, String> fdAmountData = new HashMap<String, String>();
        fdAmountData.put(KEY_FD_MAYBANK_AMOUNT, monthlyExpenseSessions.getString(KEY_FD_MAYBANK_AMOUNT, null));
        return fdAmountData;
    }

    public void createVCsession(String vc_amount) {
        editor.putString(KEY_RA_VERY_CONSERVATIVE_AMOUNT, vc_amount);
        editor.commit();
    }

    public HashMap<String, String> getVCresult() {
        HashMap<String, String> vcAmountData = new HashMap<String, String>();
        vcAmountData.put(KEY_RA_VERY_CONSERVATIVE_AMOUNT, monthlyExpenseSessions.getString(KEY_RA_VERY_CONSERVATIVE_AMOUNT, null));
        return vcAmountData;
    }

    public void createMsession(String m_amount) {
        editor.putString(KEY_RA_MODERATE_AMOUNT, m_amount);
        editor.commit();
    }

    public HashMap<String, String> getMresult() {
        HashMap<String, String> mAmountData = new HashMap<String, String>();
        mAmountData.put(KEY_RA_MODERATE_AMOUNT, monthlyExpenseSessions.getString(KEY_RA_MODERATE_AMOUNT, null));
        return mAmountData;
    }

    public void createVAsession(String m_amount) {
        editor.putString(KEY_RA_VERY_AGGRESSIVE_AMOUNT, m_amount);
        editor.commit();
    }

    public HashMap<String, String> getVAresult() {
        HashMap<String, String> vaAmountData = new HashMap<String, String>();
        vaAmountData.put(KEY_RA_VERY_AGGRESSIVE_AMOUNT, monthlyExpenseSessions.getString(KEY_RA_VERY_AGGRESSIVE_AMOUNT, null));
        return vaAmountData;
    }

    public void logOutSession() {
        editor.clear();
        editor.commit();
    }
}
