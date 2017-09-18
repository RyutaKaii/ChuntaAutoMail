package org.chunta.chuntaautomail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuActivity.
 */
public class MenuActivity extends Activity {
    /** アラーム数. */
    private static final int ALERM_SIZE = 3;
    /** アラーム1のindex */
    private static final int ALERM_INDEX_FIRST = 0;
    /** アラーム2のindex */
    private static final int ALERM_INDEX_SECOND = 1;
    /** アラーム3のindex */
    private static final int ALERM_INDEX_THERD = 2;

    /** singleton instance. */
    private static MenuActivity sInstance;
    /** UserDataList */
    private UserDataList userDataList;
    /** PreferenceManager */
    private PreferenceManager preferenceManager;

    /**
     * onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /**
     * onResume.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // contextを取得しておく
        sInstance = this;

        // preferenceManager
        preferenceManager = new PreferenceManager();

        userDataList = preferenceManager.getUserDataList(MenuActivity.getInstance());

        // preferenceにデータが存在しない場合、もしくはアラーム数に満たない場合は新規作成
        if (userDataList == null || userDataList.getUserDataList().size() != ALERM_SIZE) {
            userDataList = createNewUserDataList(ALERM_SIZE);
        }

        setEditOnClickListener();

        setViewList(userDataList);
    }

    /**
     * 指定された数分、UserDataリストを新規に生成.
     */
    private UserDataList createNewUserDataList(int size) {
        userDataList = new UserDataList();
        List<UserData> list = new ArrayList<UserData>();

        for (int i = 0;i < size; i++) {
            list.add(new UserData(i));
        }

        userDataList.setUserDataList(list);

        return userDataList;
    }

    /**
     * viewにUserdataListを設定する.
     */
    private void setViewList(UserDataList userDataList) {
        for (UserData userData : userDataList.getUserDataList()) {
            setView(userData);
        }
    }

    /**
     * viewにUserdataを設定する.
     */
    private void setView(UserData userData) {
        switch (userData.getNo()) {
            case ALERM_INDEX_FIRST:
                setTextView((TextView) findViewById(R.id.textViewOnOff),
                        (TextView) findViewById(R.id.textViewTime),
                        (TextView) findViewById(R.id.textViewSubject),
                        userData);
                break;
            case ALERM_INDEX_SECOND:
                setTextView((TextView) findViewById(R.id.textViewOnOff2),
                        (TextView) findViewById(R.id.textViewTime2),
                        (TextView) findViewById(R.id.textViewSubject2),
                        userData);
                break;
            case ALERM_INDEX_THERD:
                setTextView((TextView) findViewById(R.id.textViewOnOff3),
                        (TextView) findViewById(R.id.textViewTime3),
                        (TextView) findViewById(R.id.textViewSubject3),
                        userData);
                break;
            default:
        }
    }

    /**
     * TextViewに値を設定する.
     */
    private void setTextView(TextView textViewOnOff, TextView textViewTime, TextView textViewSubject, UserData userData) {
        if (userData.getIsSet()) {
            textViewOnOff.setText("ON");
        } else {
            textViewOnOff.setText("OFF");
        }
        textViewTime.setText(userData.getYear() + "/" + (userData.getMonth() + 1) + "/" + userData.getDay() + " " + userData.getHour() + ":" + userData.getMinute());
        textViewSubject.setText(userData.getStrSubject());
    }

    /**
     * Editボタン押下時の処理を登録.
     */
    private void setEditOnClickListener() {
        Button buttonEdit = (Button) findViewById(R.id.buttonEdit);
        Button buttonEdit2 = (Button) findViewById(R.id.buttonEdit2);
        Button buttonEdit3 = (Button) findViewById(R.id.buttonEdit3);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferenceManager(ALERM_INDEX_FIRST);
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

        buttonEdit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferenceManager(ALERM_INDEX_SECOND);
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

        buttonEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferenceManager(ALERM_INDEX_THERD);
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });
    }

    /**
     * SharedPreferencesに値を登録する.
     */
    private void setPreferenceManager(int btnNo) {
        preferenceManager.commitUserDataList(userDataList, MenuActivity.getInstance());
        preferenceManager.commitSelectedNo(btnNo, MenuActivity.getInstance());
    }

    /**
     * getUserDataList context.<br />
     * 他クラスでコンテキストを呼び出す際に使用する
     */
    public static synchronized MenuActivity getInstance() {
        return sInstance;
    }
}
