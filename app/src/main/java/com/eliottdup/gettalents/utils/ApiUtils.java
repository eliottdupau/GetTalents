package com.eliottdup.gettalents.utils;

import android.view.View;

public class ApiUtils {

    public static void showLoading(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
