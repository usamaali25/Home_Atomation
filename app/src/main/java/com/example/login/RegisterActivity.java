package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyhaveaccount;
    EditText name,email,pass,confirm_pass;
    Button register;
    String namepattern = "^[A-Z][a-zA-Z]*$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_register);
        alreadyhaveaccount = findViewById(R.id.AlreadyhaveAccount);
        name = findViewById(R.id.inputName);
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPassword);
        confirm_pass = findViewById(R.id.inputConfirmPassword);
        register = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String  Name = name.getText().toString();
        String Email = email.getText().toString();
        String Pass = pass.getText().toString();
        String cnfrm = confirm_pass.getText().toString();

        if(Name.isEmpty() || Name.length()<4 || !Name.matches(namepattern) )
            {
                name.setError("Enter Proper Name");
            }
        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            {
                email.setError("Enter Correct Email");
            }
        else if(!Pass.matches(PASSWORD_PATTERN))
            {
                pass.setError("Enter Proper Password");
            }
        else if(!Pass.equals(cnfrm))
            {
                confirm_pass.setError("Password doesn't match");
            }
        else
        {
            progressDialog.setMessage("Please while Registration..");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}