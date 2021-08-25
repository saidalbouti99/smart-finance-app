package com.example.smartfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
    TextView tv_login;
    EditText et_name,et_email,et_password,et_confirm_password;
    Button btn_register;

    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tv_login=findViewById(R.id.tv_login);
        et_name=findViewById(R.id.et_name_signup);
        et_email=findViewById(R.id.et_email_signup);
        et_password=findViewById(R.id.et_password_signup);
        et_confirm_password=findViewById(R.id.et_confirm_password_signup);
        btn_register=findViewById(R.id.btn_register);

        mFirebaseAuth=FirebaseAuth.getInstance();

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                String email=et_email.getText().toString();
                String password=et_password.getText().toString();
                String confirmPassword=et_confirm_password.getText().toString();
                if (name.isEmpty()){
                    et_name.setError("Name is required");
                    et_name.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    et_email.setError("Email is required");
                    et_email.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    et_password.setError("Password is required");
                    et_password.requestFocus();
                    return;
                }
                if (confirmPassword.isEmpty()){
                    et_confirm_password.setError("Confirm password is required");
                    et_confirm_password.requestFocus();
                    return;
                }
                if (!password.equals(confirmPassword)){
                    et_confirm_password.setError("Password does not match");
                    et_confirm_password.requestFocus();
                    return;
                }
                mFirebaseAuth.createUserWithEmailAndPassword
                        (email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}