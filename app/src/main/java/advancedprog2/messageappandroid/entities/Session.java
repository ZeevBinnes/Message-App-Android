package advancedprog2.messageappandroid.entities;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.api.ContextApplication;

public class Session {
    public static String user;
    public static String Token;
    public static String server = ContextApplication.context.getString(R.string.BaseUrl);
}
