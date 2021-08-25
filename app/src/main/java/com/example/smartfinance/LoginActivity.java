package com.example.smartfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;
import java.util.zip.Inflater;

public class LoginActivity extends AppCompatActivity {
    TextView tv_forgot_password, tv_no_account;
    EditText et_email,et_password;
    Button btn_login;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        tv_forgot_password=findViewById(R.id.tv_forgot_password);
        tv_no_account=findViewById(R.id.tv_no_account);
        btn_login=findViewById(R.id.btn_login);
        et_email=findViewById(R.id.et_email_login);
        et_password=findViewById(R.id.et_password_login);


        tv_no_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your registered email to receive reset link");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialogs  = passwordResetDialog.create();
                dialogs.show();

                dialogs.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = resetEmail.getText().toString();
                        if (value.isEmpty()) {
                            resetEmail.setError("Please enter email");
                        }
                        else{
                            String mail = resetEmail.getText().toString();
                            mFirebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginActivity.this,
                                            "Reset link successfully sent to your email", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this,
                                            "Reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialogs.dismiss();
                        }
                    }
                });
                dialogs.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogs.dismiss();
                    }
                });
            }
        });

        mFirebaseAuth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_email.getText().toString().isEmpty()){
                    et_email.setError("Please input your email");
                    et_email.requestFocus();
                    return;
                }
                if (et_password.getText().toString().isEmpty()){
                    et_password.setError("Please input your password");
                    et_password.requestFocus();
                    return;
                }
                mFirebaseAuth.signInWithEmailAndPassword(et_email.getText().toString(),
                        et_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}