package com.opinionated.opinionated;

import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by matthew on 3/22/16.
 */
public class orbitron_textview extends TextView
{
    public orbitron_textview(android.content.Context context, android.util.AttributeSet attrs)
    {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Orbitron-Medium.ttf"));
    }
}
