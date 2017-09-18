package org.chunta.chuntaautomail;

import java.io.Serializable;

/**
 * UserDataクラス.<br />
 * ユーザーの入力値を保持する。
 */
public class UserData implements Serializable {
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** コンストラクタ. */
    public UserData(int no) {
        this.no = no;
        this.isSet = false;
        strFrom = Const.EMAIL_ADDRESS_DEFAULT;
        strTo = Const.EMAIL_ADDRESS_DEFAULT;
        strPass = Const.EMAIL_PASSWORD_DEFAULT;
        strSubject = "Subject";
        strBody = "Body";
    }

    /** No. */
    private int no;
    /** getIsSet. */
    private boolean isSet;

    /** From. */
    private String strFrom;
    /** To. */
    private String strTo;
    /** Password. */
    private String strPass;
    /** Subject. */
    private String strSubject;
    /** Body. */
    private String strBody;

    /** Year. */
    private int year;
    /** Month. */
    private int month;
    /** day. */
    private int day;
    /** hour. */
    private int hour;
    /** minute. */
    private int minute;

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getNo() {
        return no;
    }

    public boolean getIsSet() {
        return isSet;
    }

    public String getStrFrom() {
        return strFrom;
    }

    public String getStrTo() {
        return strTo;
    }

    public String getStrPass() {
        return strPass;
    }

    public String getStrSubject() {
        return strSubject;
    }

    public String getStrBody() {
        return strBody;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setIsSet(boolean isSet) {
        this.isSet = isSet;
    }

    public void setStrFrom(String strFrom) {
        this.strFrom = strFrom;
    }

    public void setStrTo(String strTo) {
        this.strTo = strTo;
    }

    public void setStrPass(String strPass) {
        this.strPass = strPass;
    }

    public void setStrSubject(String strSubject) {
        this.strSubject = strSubject;
    }

    public void setStrBody(String strBody) {
        this.strBody = strBody;
    }
}
