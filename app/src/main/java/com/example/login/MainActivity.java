package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView createnewAccount,forgotpass;

    EditText email,pass;
    Button login;
    ImageView btnGoogle;

    String namepattern = "^[A-Z][a-zA-Z]*$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //findViewById
        createnewAccount = findViewById(R.id.AlreadyhaveAccount);
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPassword);
        login = findViewById(R.id.btnlogin);
        btnGoogle = findViewById(R.id.btnGoogle);
        forgotpass = findViewById(R.id.ForgotPassword);
        //ProgressDialog and mAuth,mUser
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //OnclickListener for create new Account
        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        //OnclickListener for login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        //OnclickListener for btnGoogle
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your Email To Recieve Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {

                                Toast.makeText(MainActivity.this, "Reset Link send to your Email", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();

            }
        });
    }

    private void performLogin() {
        String Email = email.getText().toString();
        String Pass = pass.getText().toString();


        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            {
                email.setError("Enter Correct Email");
            }
        else if(!Pass.matches(PASSWORD_PATTERN))
            {
                pass.setError("Enter Proper Password");
            }
        else
            {
                progressDialog.setMessage("Please while Login..");
                progressDialog.setTitle("Login");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}