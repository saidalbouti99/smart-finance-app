package com.example.smartfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartfinance.Database.PastExpense;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PastIncomeActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID,email;
    StorageReference mStorageReference;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mFirestoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_income);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();
        mFirestoreList=findViewById(R.id.recycler_view_past_income);
        userID=mFirebaseAuth.getCurrentUser().getUid();

        //Query
        Query query=mFirestore.collection("Transaction Record").document(userID).collection("Income")
                .orderBy("TransactionYear", Query.Direction.DESCENDING)
                .orderBy("TransactionMonth",Query.Direction.DESCENDING)
                .orderBy("TransactionDay",Query.Direction.DESCENDING);
        //RecyclerOptions

        FirestoreRecyclerOptions<PastExpense> options=new FirestoreRecyclerOptions.Builder<PastExpense>()
                .setQuery(query,PastExpense.class)
                .build();

        adapter=new FirestoreRecyclerAdapter<PastExpense, IncomeViewHolder>(options) {
            @NonNull
            @Override
            public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.past_income_row,parent,false);
                return new IncomeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull IncomeViewHolder holder, int position, @NonNull PastExpense model) {

                holder.tv_cat.setText(model.getTransactionCategory());
                holder.tv_amount.setText(model.getTransactionAmount());
                holder.tv_date.setText(model.getTransactionDate());
                if (model.getTransactionNotes().equals("")) {

                    holder.tv_notes.setText("-");
                }
                else {
                    holder.tv_notes.setText(model.getTransactionNotes());
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PastIncomeActivity.this, PastIncomeDetailActivity.class);
                        Bundle b = new Bundle();
                        b.putString("id", model.getTransactionID());
                        b.putString("category", model.getTransactionCategory());
                        b.putString("amount", model.getTransactionAmount());
                        b.putString("date", model.getTransactionDate());
                        if (model.getTransactionNotes().equals("")) {
                            b.putString("notes","-");
                        }
                        else {
                            b.putString("notes",model.getTransactionNotes());
                        }
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }

        };

        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private class IncomeViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_cat,tv_date,tv_amount,tv_notes;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_cat=itemView.findViewById(R.id.tv_cat_past_income);
            tv_date=itemView.findViewById(R.id.tv_date_past_income);
            tv_amount=itemView.findViewById(R.id.tv_amount_past_income);
            tv_notes=itemView.findViewById(R.id.tv_notes_past_income);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
