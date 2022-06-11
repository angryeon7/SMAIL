package com.example.smail.tabFragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.smail.BroadcastD;
import com.example.smail.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PushAlarm extends Fragment {


     EditText sender__;
     EditText msg__;
    DatePicker datePicker;
    TimePicker timePicker;
    Button btn_alarm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pushalarm,container,false);
        sender__ = (EditText) view.findViewById(R.id.sender_alarm);
        msg__ = (EditText) view.findViewById(R.id.msg_alarm);
        datePicker = (DatePicker) view.findViewById(R.id.pick_date);
        timePicker = (TimePicker) view.findViewById(R.id.pick_time);
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getHour(),timePicker.getMinute());
         btn_alarm = (Button) view.findViewById(R.id.alarm_button);
         btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification(sender__.getText().toString(),msg__.getText().toString(),calendar);
            }
        });
        // Inflate the layout for this fragmentz
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);

        /*FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Log", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(com.google.android.gms.base.R.string.common_open_on_phone
                                , token);
                        Log.d("FCM Log", msg);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });*/
    }


    private void showNotification(String title, String msg,Calendar calendar){
        //NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);


        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), BroadcastD.class);
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        Toast.makeText(getActivity(),"dkdkdkdkdkdkd", Toast.LENGTH_SHORT);

/*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "Channel Name";
            NotificationChannel channel = null;
            channel = new NotificationChannel("id","name", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Desc");
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity(),"id").setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setAutoCancel(true);
        Intent intent = new Intent(getActivity(),PushAlarm.class);
        PendingIntent pi = PendingIntent.getActivity(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);*/
        am.set(AlarmManager.RTC,calendar.getTimeInMillis(),sender);
        /*NotificationManagerCompat notificationM = NotificationManagerCompat.from(getActivity());
        notificationBuilder.setContentIntent(pi);
        notificationM.notify(0,notificationBuilder.build());*/
    }
}
