package com.js.calculator.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast;

    public static void showLastShortMessage(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

}