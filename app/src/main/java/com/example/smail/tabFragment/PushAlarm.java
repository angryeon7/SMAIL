package com.example.smail.tabFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
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
import android.widget.TextView;
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
    private Button btn_date;
    private Button btn_time;
    TextView txt_time;
    Button btn_alarm;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    final Calendar calendar = Calendar.getInstance();
    static final int DATE_DIALOG_ID=0;
    static final int TIME_DIALOG_ID=1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pushalarm,container,false);
        sender__ = (EditText) view.findViewById(R.id.sender_alarm);
        msg__ = (EditText) view.findViewById(R.id.msg_alarm);
        btn_date = (Button)view.findViewById(R.id.btn_date);
        btn_time = (Button)view.findViewById(R.id.btn_time);
        txt_time = (TextView) view.findViewById(R.id.txt_time);

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(DATE_DIALOG_ID).show();
            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(TIME_DIALOG_ID).show();
            }
        });

        calendar.set(mYear,mMonth,mDay,mHour,mMinute);
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
        System.out.println(calendar.getTime());
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);


        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), BroadcastD.class);
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        Toast.makeText(getActivity(),calendar.getTime().toString(), Toast.LENGTH_SHORT).show();
        am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            String channelName = "Channel Name";
//            NotificationChannel channel = null;
//            channel = new NotificationChannel("id","name", NotificationManager.IMPORTANCE_HIGH);
//            channel.setDescription("Desc");
//            notificationManager.createNotificationChannel(channel);
//        }
//
//
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(getActivity(),"id").setSmallIcon(R.mipmap.ic_launcher)
//                        .setWhen(calendar.getTimeInMillis())
//                        .setContentTitle(title)
//                        .setContentText(msg)
//                        .setAutoCancel(true);
//        Intent intent2 = new Intent(getActivity(),PushAlarm.class);
//        PendingIntent pi = PendingIntent.getActivity(getActivity(),0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationManagerCompat notificationM = NotificationManagerCompat.from(getActivity());
//        notificationBuilder.setContentIntent(pi);
//        notificationM.notify(0,notificationBuilder.build());
    }
    private void updateDisplay(){
        txt_time.setText(String.format("%d년 %d월 %d일 %d시 %d분", mYear, mMonth+1, mDay, mHour, mMinute));
        calendar.set(mYear,mMonth,mDay,mHour,mMinute);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListner =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private TimePickerDialog.OnTimeSetListener mTimeSetListner =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay();
                }
            };

    protected Dialog onCreateDialog(int id){
        switch(id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(getActivity(),mDateSetListner,mYear,mMonth,mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(getActivity(),mTimeSetListner,mHour,mMinute,false);
        }
      return null;
    }
}
