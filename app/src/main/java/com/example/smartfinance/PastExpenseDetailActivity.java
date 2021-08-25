package com.example.smartfinance;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import soup.neumorphism.NeumorphImageView;

public class PastExpenseDetailActivity extends AppCompatActivity {

    TextView tv_category, tv_amount, tv_dates, tv_notes;
    NeumorphImageView img_receipt;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseStorage mFirebaseStorage;
    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_expense_detail);
        tv_category = findViewById(R.id.tv_cat_past_expense_details);
        tv_amount = findViewById(R.id.tv_amount_past_expense_details);
        tv_dates = findViewById(R.id.tv_date_past_expense_details);
        tv_notes = findViewById(R.id.tv_notes_past_expense_details);
        img_receipt=findViewById(R.id.img_receipt_past_expense_details);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference();


        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        String category = b.getString("category");
        String amount = b.getString("amount");
        String date = b.getString("date");
        String notes = b.getString("notes");

        tv_category.setText(category);
        tv_amount.setText(amount);
        tv_dates.setText(date);
        tv_notes.setText(notes);

        StorageReference profileRef = mStorageReference.child("receipt/" + mFirebaseAuth.getCurrentUser().getUid() + "/"+id + "/receipt.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img_receipt);
            }
        });

    }
}