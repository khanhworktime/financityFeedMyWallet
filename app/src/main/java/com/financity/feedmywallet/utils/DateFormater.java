package com.financity.feedmywallet.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormater {
    public static SimpleDateFormat defaultFormater = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat dateOnlyFormater = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    public DateFormater() {
    }


}
