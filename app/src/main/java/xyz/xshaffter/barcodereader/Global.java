package xyz.xshaffter.barcodereader;

import android.content.Context;

import java.util.ArrayList;

import xyz.xshaffter.barcodereader.utilities.Driver;

public class Global {
    public static String codigoBR = "";
    public static ArrayList<String> codigos = new ArrayList<>();
    public static Driver driver = null;
    public static Context applicationContext = null;

    public static void setDriver(Context context) {
        applicationContext = context;
        driver = Driver.getInstance(context);
    }
}
