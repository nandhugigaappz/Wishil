package com.wishill.wishill.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jojo on 26-07-2018.
 */

public class Utils {
    public static final int minLength = 6,
            maxLength = 15,
            minNumbers = 1,
            minLetters = 0,
            minLowerCase = 1,
            minUpperCase = 1,
            minSymbols = 0,
            maxSymbols = 3;
    public static final String allowedSymbols = "#, _ , ! ";

    public static Boolean NoInternetConnection(Context context) {
        Boolean isWifiConn = false, isMobileConn = false;
        try {
            ConnectivityManager cMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cMgr.getActiveNetworkInfo();
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn = networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn = networkInfo.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (!isWifiConn && !isMobileConn);
    }

    public static ArrayList<PasswordValidation> validatePassword(String password) {
        char[] passwordCharArray = password.toCharArray();
        ArrayList<PasswordValidation> arrayList = new ArrayList<>();
        if (password.length() < minLength)
            arrayList.add(PasswordValidation.MIN_LENGTH);
        if (password.length() > maxLength)
            arrayList.add(PasswordValidation.MAX_LENGTH);
        int checkFlag = 0;
        int i;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isDigit(passwordCharArray[i])) {
                checkFlag++;
            }
        }
        if (checkFlag < minNumbers) {
            arrayList.add(PasswordValidation.MIN_NUMBERS);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isLetter(passwordCharArray[i])) {
                checkFlag++;
            }
        }
        if (checkFlag < minLetters) {
            arrayList.add(PasswordValidation.MIN_LETTERS);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isLetter(passwordCharArray[i])) {
                if (Character.isLowerCase(passwordCharArray[i])) {
                    checkFlag++;
                }
            }
        }
        if (checkFlag < minLowerCase) {
            arrayList.add(PasswordValidation.MIN_LOWER_CASE);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isLetter(passwordCharArray[i])) {
                if (Character.isUpperCase(passwordCharArray[i])) {
                    checkFlag++;
                }
            }
        }
        if (checkFlag < minUpperCase) {
            arrayList.add(PasswordValidation.MIN_UPPER_CASE);
        }

        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (!Character.isLetterOrDigit(passwordCharArray[i])) {
                if (allowedSymbols.contains(passwordCharArray[i] + ""))
                    checkFlag++;
            }
        }
        if (checkFlag < minSymbols) {
            arrayList.add(PasswordValidation.MIN_SYMBOLS);
        }
        if (checkFlag >= maxSymbols) {
            arrayList.add(PasswordValidation.MAX_SYMBOLS);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (!Character.isLetterOrDigit(passwordCharArray[i])) {
                if (passwordCharArray[i] != '#' && passwordCharArray[i] != '_' && passwordCharArray[i] != '!')
                    checkFlag++;
            }
        }
        if (checkFlag > 0) {
            arrayList.add(PasswordValidation.NOT_ALLOWED_SYMBOLS);
        }

        if (password.contains(" ")) {
            arrayList.add(PasswordValidation.CONTAIN_BLANK_SPACE);
        }
        if (arrayList.size() == 0)
            arrayList.add(PasswordValidation.PASSWORD_OK);
        return arrayList;
    }

    public static String[] convertStringToList(String arr_string) {


        String temp = arr_string.replace(" ", "");
        String[] attrIdArray = temp.split(",");
        return attrIdArray;


    }


    public static String currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String todaysDate() {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static void expand(final View view, int duration) {
        view.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 1;
        view.setVisibility(View.VISIBLE);
        Animation anim = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static void collapse(final View view, int duration) {
        final int initialHeight = view.getMeasuredHeight();
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static String convertDateFormat(String strDate) {
        String formattedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = format.parse(strDate);
            formattedDate = formatDate.format(date);
            formattedDate = formattedDate.trim();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    public static String OnlyDate(String strDate) {
        String formattedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        // SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy  hh:mm a", Locale.getDefault());
        // SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy ", Locale.getDefault());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date date = format.parse(strDate);
            formattedDate = formatDate.format(date);
            formattedDate = formattedDate.trim();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String replaceCommas(String str) {

        if (str != null) {
            return str.replaceAll(",", "");
        }
        return "";
    }
    public static String isNull(String str)
    {
        if(str==null)
        {
            return " ";
        }
        else
        {
          return   str.trim();
        }
    }
}
