package com.dexter.basemvpwithoutdagger.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

/**
 * Created by Admin on 06-12-2017.
 */

public class TextUtils {

    public static void setString(TextView textView, String s) {
        if (!android.text.TextUtils.isEmpty(s)) {
            textView.setText(s);
        }
    }

    public static String getCapitalizedString(String s) {
        if (!android.text.TextUtils.isEmpty(s)) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        } else {
            return "";
        }
    }

    public static void setSpannableString(Context context, @ColorRes int color, TextView textView, String span, String s) {
        if (!android.text.TextUtils.isEmpty(s) && !android.text.TextUtils.isEmpty(s)) {
            s = getCapitalizedString(s);
            String toSpan = span + " " + s;
            Spannable spannableString = new SpannableString(toSpan);
            int spanIndex = toSpan.indexOf(span);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), spanIndex, spanIndex + span.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), spanIndex, spanIndex + span.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            textView.setText(spannableString);
        }
    }

    public static String getStringFromStringResId(Context context, @StringRes int stringId) {
        return context.getString(stringId);
    }
}
