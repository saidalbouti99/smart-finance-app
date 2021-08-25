package com.example.smartfinance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = AddTransactionActivity.class.getSimpleName();
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        chipNavigationBar = findViewById(R.id.chip_nav_add_trans);
        if (savedInstanceState == null) {
            chipNavigationBar.setItemSelected(R.id.income, true);
            fragmentManager = getSupportFragmentManager();
            AddIncomeFragment addIncomeFragment = new AddIncomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, addIncomeFragment).commit();
        }
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id) {
                    case R.id.income:
                        fragment = new AddIncomeFragment();
                        break;
                    case R.id.expense:
                        fragment = new AddExpenseFragment();
                        break;
                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Log.e(TAG, "Error fragment");
                }
            }
        });
    }
}