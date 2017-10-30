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
    /** シングルトンのAlermインスタンス. */
    private static Alerm alerm = new Alerm();

    /**
     * シングルトンのためのprivateコンストラクタ.
     */
    private Alerm() {
    }

    /**
     * インスタンス取得.
     * @return インスタンス
     */
    public static Alerm getInstanse() {
        return alerm;
    }

    /**
     * アラームをセットする.<br />
     * リクエストコードによってintentを区別する
     */
    public void setAlerm(UserData userData, Intent intent) {
        // service側でintentを利用するためにuserDataを挿入
        intent.putExtra("userData", userData);

        // 第2引数にNoを指定することでintentを区別する
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.getInstance().getApplicationContext(), userData.getNo(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
     * アラームをキャンセルする.<br />
     * リクエストコードによってintentを区別する
     */
    public void canselAlerm(UserData userData, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.getInstance().getApplicationContext(), userData.getNo(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) MainActivity.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        manager.cancel(pendingIntent);
        pendingIntent.cancel();
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
