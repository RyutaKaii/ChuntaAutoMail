package org.chunta.chuntaautomail;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * TaskServiceクラス.
 */
public class TaskService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        UserData userData = (UserData) intent.getSerializableExtra("userData");

        if (userData.getIsSet()) {
            // 起動フラグtrueのときのみ処理を実施
            new Mail().sendMail(userData);
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}