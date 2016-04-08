package com.njucm.cmdh.app.widgetlibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by Mesogene on 5/1/15.
 */
public class TextRadioButton extends RadioButton {

    public TextRadioButton(Context context) {
        super(context);
    }

    public TextRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        setBackground(getResources().getDrawable(com.beardedhen.androidbootstrap.R.drawable.bbuton_success));
    }
}
