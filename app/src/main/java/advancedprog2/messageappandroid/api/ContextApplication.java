package advancedprog2.messageappandroid.api;

import android.app.Application;
import android.content.Context;

public class ContextApplication extends Application {
    public static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }
}
