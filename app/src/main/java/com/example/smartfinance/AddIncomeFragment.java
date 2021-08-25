package com.example.smartfinance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddIncomeFragment extends Fragment {
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;

    String userID, s;
    Date date;
    int selectedId;
    Double a;
    int lastChildPos;
    SimpleDateFormat sdf;
    String currentDateandTime;

    FloatingActionButton fab_confirm,fab_cancel;
    EditText et_income_amount, et_income_date, et_income_notes;
    Button btn_upload_receipt, btn_cat_business, btn_cat_dividend, btn_cat_others;
    String category;
    Double amount;
    CardView card_cat;

    RadioGroup radioGroup;
    RadioButton btn_cat_salary, btn_cat_gift, radioButton;

    private int mYear;
    private int mMonth;

    private int mDay;
    private int chosenMonth;
    private int chosenDay;
    private int chosenYear;
    static final int DATE_DIALOG_ID = 0;

    public AddIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        userID = mFirebaseAuth.getCurrentUser().getUid();
        mFirestore = FirebaseFirestore.getInstance();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        sdf = new SimpleDateFormat("dd.MM.yy G 'at' HH:mm:ss z");
        currentDateandTime = sdf.format(new Date());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_income, container, false);

        View view = inflater.inflate(R.layout.fragment_add_income, container, false);
        fab_confirm = view.findViewById(R.id.fab_confirm_add_income);
        fab_cancel = view.findViewById(R.id.fab_cancel_add_income);
        et_income_amount = view.findViewById(R.id.et_amount_add_income);
        et_income_date = view.findViewById(R.id.et_date_add_income);
        et_income_notes = view.findViewById(R.id.et_notes_add_income);
//        btn_upload_receipt = view.findViewById(R.id.btn_upload_add_income);
        radioGroup = view.findViewById(R.id.radio_group);

//        btn_upload_receipt.setText(currentDateandTime);

        btn_cat_salary = view.findViewById(R.id.cat_salary_income);
        btn_cat_gift = view.findViewById(R.id.cat_gift_income);
//        btn_cat_salary=view.findViewById(R.id.cat_salary_income);
//        btn_cat_gift=view.findViewById(R.id.cat_gift_income);
//        btn_cat_business=view.findViewById(R.id.cat_business_income);
//        btn_cat_dividend=view.findViewById(R.id.cat_dividends_income);
//        btn_cat_others=view.findViewById(R.id.cat_others_income);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId = radioGroup.getCheckedRadioButtonId();
                switch (checkedId) {
                    case R.id.cat_salary_income:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_gift_income:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_business_income:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_dividend_income:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_others_income:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    default:
                }

            }
        });
        // get selected radio button from radioGroup


        // find the radiobutton by returned id
//        radioButton = (RadioButton) view.findViewById(selectedId);

//        Toast.makeText(getContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
        et_income_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(et_income_date);
            }
        });


        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        fab_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastChildPos = radioGroup.getChildCount() - 1;
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    ((RadioButton) radioGroup.getChildAt(lastChildPos)).setError("No category selected");
                    Toast.makeText(getContext(),"No category selected",Toast.LENGTH_SHORT).show();
                } else if (et_income_amount.getText().toString().isEmpty()) {
                    et_income_amount.setError("Please input amount");
                    et_income_amount.requestFocus();
                } else if (et_income_date.getText().toString().isEmpty()) {
                    et_income_date.setError("Please input date");
                    Toast.makeText(getContext(),"No date selected",Toast.LENGTH_SHORT).show();
                    et_income_date.requestFocus();
                } else {
                    s = et_income_amount.getText().toString();
                    a = Double.valueOf(s.trim()).doubleValue();
                    DocumentReference documentReference = mFirestore
                            .collection("Transaction Record")
                            .document(userID)
                            .collection("Income").document();

                    final Map<String, Object> income = new HashMap<>();
                    income.put("TransactionAmount", et_income_amount.getText().toString());
                    income.put("TransactionCategory", category);
                    income.put("TransactionDate", et_income_date.getText().toString());
                    income.put("TransactionDay", chosenDay);
                    income.put("TransactionMonth", chosenMonth + 1);
                    income.put("TransactionYear", chosenYear);
                    income.put("TransactionTimeStamp", FieldValue.serverTimestamp());
                    income.put("TransactionNotes", et_income_notes.getText().toString());

                    documentReference.set(income).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Income is recorded", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Record Income Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                chosenMonth = month;
                chosenDay = dayOfMonth;
                chosenYear = year;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };


        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}