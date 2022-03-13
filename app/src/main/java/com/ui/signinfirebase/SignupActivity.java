package com.ui.signinfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.function.ToDoubleBiFunction;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    EditText name, email, pass;
    TextView reg,login;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        name = findViewById(R.id.Rname);
        email = findViewById(R.id.REmail);
        pass = findViewById(R.id.RPassword);
       login = findViewById(R.id.btnlog);

//        String email = etLoginEmail.getText().toString();
//        String password = etLoginPassword.getText().toString();
//
//        if (TextUtils.isEmpty(email)){
//            etLoginEmail.setError("Email cannot be empty");
//            etLoginEmail.requestFocus();
//        }else if (TextUtils.isEmpty(password)){
//            etLoginPassword.setError("Password cannot be empty");
//            etLoginPassword.requestFocus();
//        }else{
//            mAu

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));
           }
       });


        reg = findViewById(R.id.btnreg);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namebox, emailbox, passbox;
                namebox = name.getText().toString();
                emailbox = email.getText().toString();
                passbox = pass.getText().toString();

                if (namebox.equals("") || emailbox.equals("") || passbox.equals(""))

                    Toast.makeText(SignupActivity.this, " Fields can't be empty ,Please fill all the fields", Toast.LENGTH_SHORT).show();

//                   else  {
//                   mAwesomeValidation.validate();
//
//               }

             else {

                    auth.createUserWithEmailAndPassword(emailbox,passbox)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "SignUp Suucess", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");

                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


        }

       }

        });

    }
}