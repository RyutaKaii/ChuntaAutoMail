package org.chunta.chuntaautomail;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * PreferenceManagerクラス.<br />
 * Preferenceを操作する。
 */
public class PreferenceManager {
    /**
     * UserDataリストを登録する.
     */
    public void commitUserDataList(UserDataList userDataList, Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(Const.PREFERENCE_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        gson.toJson(userDataList);
        pref.edit().putString("userDataList", gson.toJson(userDataList)).commit();
    }

    /**
     * UserDataリストを取得する.
     */
    public UserDataList getUserDataList(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(Const.PREFERENCE_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        UserDataList userDataList = gson.fromJson(pref.getString("userDataList", ""), UserDataList.class);

        return userDataList;
    }

    /**
     * selectedNoを登録する.
     */
    public void commitSelectedNo(int selectedNo, Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(Const.PREFERENCE_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        gson.toJson(selectedNo);
        pref.edit().putString("selectedNo", gson.toJson(selectedNo)).commit();
    }

    /**
     * selectedNoを取得する.
     */
    public int getSelectedNo(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(Const.PREFERENCE_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        int selectedNo = gson.fromJson(pref.getString("selectedNo", ""), Integer.class);

        return selectedNo;
    }
}
