package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.login.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notifications extends AppCompatActivity {

    DatabaseReference databaseReference,databaseReference1;
    int statusFb, status, max=300, statusKorr;
    int statfb, stat, statkor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        createNotificationChannel();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Smoke");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Motion");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statusFb = Integer.parseInt(snapshot.child("Value").getValue().toString());
                statusKorr = statusFb;
                status = (int) (((double) statusKorr / (double) max) * 100);

                Log.d("statpc", Integer.toString(status));

                if(status >= 80 )
                {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Notifications.this,"Smoke_Alarm")
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle("Fire Alarm!!")
                            .setContentText("Smoke Detector has detected smoke/fire in your house. Be Safe!!")
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Notifications.this);
                    notificationManagerCompat.notify(123,builder.build());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statusFb = Integer.parseInt(snapshot.child("motion detected or not").getValue().toString());
                status = (int) (((double) statusFb ));

                Log.d("statpc", Integer.toString(status));

                if(status == 1)
                {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Notifications.this,"Motion_Alarm")
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle("Motion Alarm!!")
                            .setContentText("Motion has been detected in your house. Be Safe!!")
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Notifications.this);
                    notificationManagerCompat.notify(112,builder.build());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Alarm";
            CharSequence name1 = "Motion";
            String description = "Channel for Fire Alarm";
            String description1 = "Channel for Motion Alarm";
            int importance  = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Smoke_Alarm",name,importance);
            channel.setDescription(description);

            NotificationChannel channel1 = new NotificationChannel("Motion_Alarm",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }

    }

}