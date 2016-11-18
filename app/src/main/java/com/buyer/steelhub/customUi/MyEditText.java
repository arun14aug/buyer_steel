package com.buyer.steelhub.customUi;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText {

    public MyEditText(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Raleway-Medium.ttf", context);
        setTypeface(customFont);
    }
}
