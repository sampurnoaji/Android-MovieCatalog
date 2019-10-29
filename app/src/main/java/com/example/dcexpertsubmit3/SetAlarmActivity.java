package com.example.dcexpertsubmit3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dcexpertsubmit3.reminder.AlarmReceiver;

public class SetAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        setTitle(getString(R.string.alarm));

        Switch daily = findViewById(R.id.switch_daily);
        Switch release = findViewById(R.id.switch_release);
        
        final AlarmReceiver alarmReceiver = new AlarmReceiver();

        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    alarmReceiver.setRepeatingAlarm(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_on), Toast.LENGTH_SHORT).show();
                } else {
                    alarmReceiver.cancelAlarm(getApplicationContext(), AlarmReceiver.TYPE_REPEATING);
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_off), Toast.LENGTH_SHORT).show();
                }
            }
        });

        release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    alarmReceiver.setReleaseAlarm(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_on), Toast.LENGTH_SHORT).show();
                } else {
                    alarmReceiver.cancelAlarm(getApplicationContext(), AlarmReceiver.TYPE_RELEASE);
                    Toast.makeText(getApplicationContext(), getString(R.string.alarm_off), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
