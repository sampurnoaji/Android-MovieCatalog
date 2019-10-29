package com.example.dcexpertsubmit3.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.dcexpertsubmit3.MainActivity;
import com.example.dcexpertsubmit3.R;
import com.example.dcexpertsubmit3.connectivity.Connectivity;
import com.example.dcexpertsubmit3.model.Movie;
import com.example.dcexpertsubmit3.view_model.MovieViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_REPEATING = "repeating_alarm";
    public static final String TYPE_RELEASE = "release_alarm";

    public static final int ID_REPEATING = 100;
    public static final int ID_RELEASE = 200;

    private static final String EXTRA_MESSAGE = "message";
    private static final String EXTRA_TYPE = "type";

    private int releaseCount;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        int notifId = type.equalsIgnoreCase(TYPE_REPEATING) ? ID_REPEATING : ID_RELEASE;

        showAlarmNotification(context, notifId, message);
    }

    private void showAlarmNotification(Context context, int notifId, String message){
        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "channel_alarm";

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (manager != null){
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (manager != null){
            manager.notify(notifId, notification);
        }
    }

    public void setRepeatingAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, TYPE_REPEATING);
        intent.putExtra(EXTRA_MESSAGE, context.getString(R.string.daily_reminder));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

    public void setReleaseAlarm(final Context context){
        final AlarmManager[] alarmManager = new AlarmManager[1];
        final Intent[] intent = new Intent[1];

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);
        Log.d("Current date: ", formatDate);

        Connectivity.TODAY_DATE = formatDate;
        new AsyncHttpClient().get(Connectivity.ENDPOINT_RELEASE, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray responseArray = responseObject.getJSONArray("results");
                    releaseCount = responseArray.length();
                    Log.d("releaseCountin: ", String.valueOf(releaseCount));

                    alarmManager[0] = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    intent[0] = new Intent(context, AlarmReceiver.class);
                    intent[0].putExtra(EXTRA_TYPE, TYPE_RELEASE);
                    intent[0].putExtra(EXTRA_MESSAGE, context.getString(R.string.message_release) +" "+ releaseCount);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent[0], 0);
                    if (alarmManager[0] != null){
                        alarmManager[0].setInexactRepeating(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY,
                                pendingIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        Log.d("releaseCountout: ", String.valueOf(releaseCount));
    }

    public void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_REPEATING) ? ID_REPEATING : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
