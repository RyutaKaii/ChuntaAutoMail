package org.chunta.chuntaautomail;

import java.io.Serializable;
import java.util.List;


/**
 * UserDataリストクラス.
 */
public class UserDataList implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    /** UserDataリスト */
    private List<UserData> userDataList;

    public List<UserData> getUserDataList() {
        return userDataList;
    }

    public void setUserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }
}
