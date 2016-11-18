package com.buyer.steelhub.customUi;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button {

    public MyButton(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Raleway-Medium.ttf", context);
        setTypeface(customFont);
    }
}