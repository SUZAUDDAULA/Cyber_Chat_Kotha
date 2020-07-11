package com.opus_bd.myapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utilities {
    public static final String BASE_URL = "http://33d27f6e.ngrok.io/";
    public static int GETCOUNT;
    public static Double GETTOTAL;
    public static Double NETTOTAL = 0.0;

    public static void showLogcatMessage(String message) {
        Log.d("tag", message);
    }

    public static String postdateformate(String inputDateStr) {
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputFormat.format(date);
    }

    public static String getdateformate(String inputDateStr) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyy");
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr2 = outputFormat.format(date);

        return outputDateStr2;
    }

    public static String dateformate(String inputDateStr) {
        DateFormat inputFormat = new SimpleDateFormat("MMMM d yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr2 = outputFormat.format(date);

        return outputDateStr2;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void logout(Context context) {
        SharedPrefManager.getInstance(context).logout();
    }

    public static String getEnrtyTimeformate(String inputDateStr) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr2 = outputFormat.format(date);

        return outputDateStr2;
    }

    public static String getEnrtyDateformate(String inputDateStr) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr2 = outputFormat.format(date);

        return outputDateStr2;
    }


    public static String getMonthForNumber(String number) {

        showLogcatMessage(number);
        String month = "";
        int num = -1;
        try {
            num = Integer.parseInt(number);
            num--; // Since calender month is 0 based
        } catch (Exception e) {

        }

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    public static String getAddressFromLatLong(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            return obj.getAddressLine(0);

        } catch (Exception ignored) {
        }
        return "";
    }

    public static String getTwoDigitMonth(int month) {
        String formattedMonth = "";
        if (month < 10)
            formattedMonth += "0";
        formattedMonth += month;
        return formattedMonth;
    }

    public static String getTwoDigitDay(int day) {
        String formattedDay = "";
        if (day < 10)
            formattedDay += "0";
        formattedDay += day;
        return formattedDay;
    }
}
