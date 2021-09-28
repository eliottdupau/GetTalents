package com.eliottdup.gettalents.utils;

import android.view.View;

import com.eliottdup.gettalents.R;
import com.google.android.material.snackbar.Snackbar;

public class ErrorUtils {

    public static void displayLongError(View view, String error) {
        Snackbar snackbar = Snackbar.make(view, error, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(view.getResources().getColor(R.color.colorError));

        snackbar.show();
    }
}
