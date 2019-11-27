package com.example.dcexpertsubmit3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dcexpertsubmit3.reminder.NotificationReceiver;

public class SetAlarmActivity extends AppCompatActivity {
    private NotificationReceiver notificationReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        setTitle(getString(R.string.alarm));

        Switch daily = findViewById(R.id.switch_daily);
        Switch release = findViewById(R.id.switch_release);

        notificationReceiver = new NotificationReceiver(this);

        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    notificationReceiver.setDailyReminder();
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_on), Toast.LENGTH_SHORT).show();
                } else {
                    notificationReceiver.cancelDailyReminder(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_off), Toast.LENGTH_SHORT).show();
                }
            }
        });

        release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    notificationReceiver.setReleaseTodayReminder();
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_on), Toast.LENGTH_SHORT).show();
                } else {
                    notificationReceiver.cancelReleaseToday(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_off), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
