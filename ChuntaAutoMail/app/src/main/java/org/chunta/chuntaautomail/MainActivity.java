package org.chunta.chuntaautomail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * MainActivity.<br />
 * refs<br />
 * mail : http://qiita.com/usatie/items/e71b9bb34963b74a8b57<br />
 * alarm : https://tech.mokelab.com/android/<br />
 * http://qiita.com/hiroaki-dev/items/e3149e0be5bfa52d6a51<br />
 * alarm parameter : http://y-anz-m.blogspot.jp/2011/03/alarmmanager.html<br />
 * date dialog : http://www110.kir.jp/Android/chap9_3_1.html<br />
 * SharedPreferences : http://qiita.com/YAmi/items/626ea488e69965d75e38<br />
 * Service parameter : http://yuki312.blogspot.jp/2012/07/androidserviceonstartcommand.html<br />
 * layout : http://asky.hatenablog.com/entry/2016/05/04/194303
 */
public class MainActivity extends AppCompatActivity {
    /** singleton instance. */
    private static MainActivity sInstance;
    /** UserDataリスト */
    private UserDataList userDataList;
    /** selectedNo */
    int selectedNo;
    /** UserData. */
    private UserData userData;
    /** PreferenceManager */
    private PreferenceManager preferenceManager;

    // 日付設定ダイアログのインスタンスを格納する変数
    private DatePickerDialog.OnDateSetListener varDateSetListener;
    // 時刻設定ダイアログのインスタンスを格納する変数
    private TimePickerDialog.OnTimeSetListener varTimeSetListener;


    /**
     * onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        // メール送信のためにThreadPolicyを変更
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // preferenceからUserDataを取得
        userDataList = preferenceManager.getUserDataList(MainActivity.getInstance());
        selectedNo = preferenceManager.getSelectedNo(MainActivity.getInstance());
        userData = userDataList.getUserDataList().get(selectedNo);

        // エディットテキストのインスタンスを取得
        final EditText editTextYyyymmdd = (EditText) findViewById(R.id.editTextYyyymmdd);
        final EditText editTextHhmm = (EditText) findViewById(R.id.editTextHhmm);

        // イベントリスナーのインスタンス化を実施、日付設定ダイアログに登録する
        setYyyymmddListener(editTextYyyymmdd);
        // イベントリスナーのインスタンス化を実施、日付設定ダイアログに登録する
        setHhmmListener(editTextHhmm);

        setYyyymmddOnClickListener();
        setHhmmOnClickListener();
        setSaveOnClickListener();
        setCancelOnClickListener();

        setView(userData);
    }

    /**
     * イベントリスナーのインスタンス化を実施、日付設定ダイアログに登録する.
     */
    private void setYyyymmddListener(final EditText editTextYyyymmdd) {
        varDateSetListener
                = new DatePickerDialog.OnDateSetListener() {
            // 日付設定ダイアログの[OK]ボタンがクリックされたときの処理
            @Override
            public void onDateSet(
                    DatePicker view, int year, int monthOfYear,
                    int dayOfMonth) {
                editTextYyyymmdd.setText(year + "/"
                        + (monthOfYear + 1) + "/"
                        + dayOfMonth);

                userData.setYear(year);
                userData.setMonth(monthOfYear);
                userData.setDay(dayOfMonth);
            }
        };
    }

    /**
     * イベントリスナーのインスタンス化を実施、日付設定ダイアログに登録する.
     */
    private void setHhmmListener(final EditText editTextHhmm) {
        varTimeSetListener
                = new TimePickerDialog.OnTimeSetListener() {
            // 時刻設定ダイアログの[OK]ボタンがクリックされたときの処理
            public void onTimeSet(
                    TimePicker view, int hourOfDay, int minute) {
                editTextHhmm.setText(hourOfDay + ":" + minute);

                userData.setHour(hourOfDay);
                userData.setMinute(minute);
            }
        };
    }

    /**
     * 日付を設定ボタン押下時の処理を登録.
     */
    private void setYyyymmddOnClickListener() {
        ((Button) findViewById(R.id.buttonYyyymmdd))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        DatePickerDialog dateDialog = new DatePickerDialog(
                                MainActivity.this,
                                varDateSetListener,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        dateDialog.show();
                    }
                });
    }

    /**
     * 日時を設定ボタン押下時の処理を登録.
     */
    private void setHhmmOnClickListener() {
        ((Button) findViewById(R.id.buttonHhmm))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        TimePickerDialog timeDialog = new TimePickerDialog(
                                MainActivity.this,
                                varTimeSetListener,
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                false);
                        timeDialog.show();
                    }
                });
    }

    /**
     * Saveボタン押下時の処理を登録.
     */
    private void setSaveOnClickListener() {
        Button buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputParam();
                new Alerm().setAlerm(userData);

                // SharedPreferencesに値を登録
                userDataList.getUserDataList().set(selectedNo, userData);
                preferenceManager.commitUserDataList(userDataList, MainActivity.getInstance());

                Toast.makeText(MainActivity.getInstance(), "設定を保存しました。", Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    /**
     * Cancelボタン押下時の処理を登録.
     */
    private void setCancelOnClickListener() {
        Button buttonCansel = (Button) findViewById(R.id.buttonCancel);

        buttonCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.getInstance(), "設定は保存されませんでした。", Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    /**
     * 入力値を設定する.
     */
    private void setInputParam() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        userData.setIsSet(checkBox.isChecked());

        EditText editTextBody = (EditText) findViewById(R.id.editTextBody);
        userData.setStrBody(editTextBody.getText().toString());

        EditText editTextFrom = (EditText) findViewById(R.id.editTextFrom);
        userData.setStrFrom(editTextFrom.getText().toString());

        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        userData.setStrPass(editTextPass.getText().toString());

        EditText editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        userData.setStrSubject(editTextSubject.getText().toString());

        EditText editTextTo = (EditText) findViewById(R.id.editTextTo);
        userData.setStrTo(editTextTo.getText().toString());
    }

    /**
     * viewに値を設定する.
     */
    private void setView(UserData userData) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(userData.getIsSet());

        EditText editTextYyyymmdd = (EditText) findViewById(R.id.editTextYyyymmdd);
        editTextYyyymmdd.setText(userData.getYear() + "/"
                + (userData.getMonth() + 1) + "/"
                + userData.getDay());

        EditText editTextHhmm = (EditText) findViewById(R.id.editTextHhmm);
        editTextHhmm.setText(userData.getHour() + ":" + userData.getMinute());

        EditText editTextBody = (EditText) findViewById(R.id.editTextBody);
        editTextBody.setText(userData.getStrBody());

        EditText editTextFrom = (EditText) findViewById(R.id.editTextFrom);
        editTextFrom.setText(userData.getStrFrom());

        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPass.setText(userData.getStrPass());

        EditText editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextSubject.setText(userData.getStrSubject());

        EditText editTextTo = (EditText) findViewById(R.id.editTextTo);
        editTextTo.setText(userData.getStrTo());
    }

    /**
     * getUserDataList context.<br />
     * 他クラスでコンテキストを呼び出す際に使用する
     */
    public static synchronized MainActivity getInstance() {
        return sInstance;
    }
}
