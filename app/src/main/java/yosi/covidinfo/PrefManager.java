package yosi.covidinfo;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "introslider";
    private static final String IS_FISRT_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    public boolean isFirstTimeLaunch() {
        return  preferences.getBoolean(IS_FISRT_TIME_LAUNCH, true);
    }

    public void setFirstTimelaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FISRT_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
}
