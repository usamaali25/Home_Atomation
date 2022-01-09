package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Fans extends AppCompatActivity {

    SwitchCompat switch1,switch2,switch3;
    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);

        switch1 = findViewById(R.id.Fan1);
        switch2 = findViewById(R.id.Fan2);
        switch3 = findViewById(R.id.Fan3);
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        rootRef = FirebaseDatabase.getInstance().getReference();

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch1.isChecked())
                {
                    int id = 3;
                    HashMap hashMap = new HashMap();
                    hashMap.put("Bulb",id);
                    rootRef.child("Home_Automation").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(@NonNull Object o) {
                            Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Data is not Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    int id = 4;
                    HashMap hashMap = new HashMap();
                    hashMap.put("Bulb",id);
                    rootRef.child("Home_Automation").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(@NonNull Object o) {
                            Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Data is not Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch2.isChecked())
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Fan");
                    myRef.setValue(1);
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switch2.setChecked(true);
                }else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switch2.setChecked(false);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Fan");
                    myRef.setValue(0);
                }
            }
        });

        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch3.isChecked())
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Fans");
                    myRef.setValue(1);
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switch3.setChecked(true);
                }else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switch3.setChecked(false);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Fan");
                    myRef.setValue(0);
                }
            }
        });
    }
}