package com.ui.signinfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricManager;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
EditText email,pass;
TextView login,fp;

FirebaseAuth auth;
ProgressDialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.lEmail);
        pass=findViewById(R.id.lPassword);
        login=findViewById(R.id.buttonlogin);
       fp=findViewById(R.id.buttonfp);
        dialog=new ProgressDialog(this);
        dialog.setTitle("Please Wait...");
        auth=FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.show();
                String mail,password;
                mail=email.getText().toString();
                password=pass.getText().toString();
                if (mail.equals("") ||  password.equals("")) {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, " Fields can't be empty ,Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "logged In successfully", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });




        BiometricManager biometricManager= BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
//                txt.setText("use fingerprint sensor to login");

                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
//                txt.setText("no fingerprint sensor found");

                login.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
//                txt.setText(" fingerprint sensor currently unavailable");

                login.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
//                txt.setText(" your device does not have any fingerprint sensor ");

                login.setVisibility(View.GONE);
                break;
        }





        Executor executor= ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt=new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {

            @Override

            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {

                super.onAuthenticationError(errorCode, errString);

                Toast.makeText(LoginActivity.this, "so u choose not to login.... ", Toast.LENGTH_LONG).show();

            }


            @Override

            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {

                super.onAuthenticationSucceeded(result);

                Toast.makeText(LoginActivity.this, "Login Success ", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);


            }


            @Override

            public void onAuthenticationFailed() {

                super.onAuthenticationFailed();

                Toast.makeText(LoginActivity.this, "Login Failed ", Toast.LENGTH_LONG).show();

            }

        });


        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()

                        .setTitle("Login")

                        .setSubtitle("Use your Fingerprint to login in App")

                        .setNegativeButtonText("Cancel")

                        .build();



                biometricPrompt.authenticate(promptInfo);

            }
        });
    }
}