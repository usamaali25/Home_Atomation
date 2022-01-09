package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Water_Level_indicator extends AppCompatActivity {

    TextView sensorData;
    ImageView imageView;
    DatabaseReference databaseReference;
    int statusFb, status, max=20, statusKorr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level_indicator);

        sensorData = (TextView) findViewById(R.id.percentLabel);
        imageView = (ImageView) findViewById(R.id.waterImage);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Water_Level_Indicator");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statusFb = Integer.parseInt(snapshot.child("Distance is").getValue().toString());
                statusKorr = max - statusFb;
                status = (int) (((double) statusKorr / (double) max) * 100);



                Log.d("statpc", Integer.toString(status));

                //sensorData.setText(Integer.toString(status) + "%");


                if(status >= 95 && status < 100)
                {
                    imageView.setImageResource(R.drawable.full);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 90 && status < 95)
                {
                    imageView.setImageResource(R.drawable.ninety);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 60 && status < 90)
                {
                    imageView.setImageResource(R.drawable.sixty);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 40 && status < 60)
                {
                    imageView.setImageResource(R.drawable.forty);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 10 && status < 40)
                {
                    imageView.setImageResource(R.drawable.ten);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else
                {
                    imageView.setImageResource(R.drawable.empty);
                    sensorData.setText(Integer.toString(status) + "%");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}