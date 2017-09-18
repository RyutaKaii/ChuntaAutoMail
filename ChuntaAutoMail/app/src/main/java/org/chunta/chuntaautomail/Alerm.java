package org.chunta.chuntaautomail;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

/**
 * アラームクラス.
 */
public class Alerm {
    /**
     * アラームをセットする.<br />
     * リクエストコードによってintentを区別する
     */
    public void setAlerm(UserData userData) {
        Intent it = new Intent(MainActivity.getInstance().getApplicationContext(), TaskService.class);
        it.putExtra("userData", userData);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.getInstance().getApplicationContext(), userData.getNo(), it, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) MainActivity.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Calendar getupTime = getGetupTime(userData);

        // APIバージョンによってセットするメソッドを変える
        if (Build.VERSION.SDK_INT < 19){
            manager.set(AlarmManager.RTC_WAKEUP, getupTime.getTimeInMillis(), pendingIntent);
        } else if (19 <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT < 23) {
            manager.setExact(AlarmManager.RTC_WAKEUP, getupTime.getTimeInMillis(), pendingIntent);
        } else {
//            manager.setAlarmClock(new AlarmManager.AlarmClockInfo(getupTime.getTimeInMillis(), null), pendingIntent);
        }
    }

    /**
     * 起動時間を取得.
     */
    private Calendar getGetupTime(UserData userData) {
        Calendar getupTime = Calendar.getInstance();
        getupTime.setTimeInMillis(0);
        getupTime.set(Calendar.YEAR, userData.getYear());
        getupTime.set(Calendar.MONTH, userData.getMonth());
        getupTime.set(Calendar.DAY_OF_MONTH, userData.getDay());
        getupTime.set(Calendar.HOUR_OF_DAY, userData.getHour());
        getupTime.set(Calendar.MINUTE, userData.getMinute());
        getupTime.set(Calendar.SECOND, 0);

        return getupTime;
    }
}
