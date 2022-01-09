package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Plamt_Watering extends AppCompatActivity {

    TextView sensorData;
    ImageView imageView;
    DatabaseReference databaseReference;
    int statusFb, status, max=750, statusKorr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plamt_watering);

        sensorData = (TextView) findViewById(R.id.percentLabel);
        imageView = (ImageView) findViewById(R.id.waterImage);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plant_Watering");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statusFb = Integer.parseInt(snapshot.child("Humidity").getValue().toString());
                statusKorr = max -  statusFb;
                status = (int) (((double) statusKorr / (double) max) * 100);



                Log.d("statpc", Integer.toString(status));

               // sensorData.setText(Integer.toString(status) + "%");


                if(status >= 99)
                {
                    imageView.setImageResource(R.drawable.hunderedper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 95 && status < 99)
                {
                    imageView.setImageResource(R.drawable.ninety5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 90 && status < 95)
                {
                    imageView.setImageResource(R.drawable.ninetyper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 85 && status < 90)
                {
                    imageView.setImageResource(R.drawable.eighty5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 80 && status < 85)
                {
                    imageView.setImageResource(R.drawable.eightyper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 75 && status < 80)
                {
                    imageView.setImageResource(R.drawable.seventy5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 70 && status < 75)
                {
                    imageView.setImageResource(R.drawable.seventyper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 65 && status < 70)
                {
                    imageView.setImageResource(R.drawable.seventy5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 60 && status < 65)
                {
                    imageView.setImageResource(R.drawable.sixtyper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 55 && status < 60)
                {
                    imageView.setImageResource(R.drawable.fifty5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 50 && status < 55)
                {
                    imageView.setImageResource(R.drawable.fiftyper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 45 && status < 50)
                {
                    imageView.setImageResource(R.drawable.forty5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 35 && status < 45)
                {
                    imageView.setImageResource(R.drawable.thirty5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 30 && status < 55)
                {
                    imageView.setImageResource(R.drawable.thirtyper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 25 && status < 30)
                {
                    imageView.setImageResource(R.drawable.twenty5per);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 20 && status < 25)
                {
                    imageView.setImageResource(R.drawable.twentyper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 15 && status < 20)
                {
                    imageView.setImageResource(R.drawable.fifeteenper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 10 && status < 15)
                {
                    imageView.setImageResource(R.drawable.tenper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else if (status >= 5 && status < 10)
                {
                    imageView.setImageResource(R.drawable.fiveper);
                    sensorData.setText(Integer.toString(status) + "%");
                }
                else
                {
                    imageView.setImageResource(R.drawable.zero);
                    sensorData.setText(Integer.toString(status) + "%");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}