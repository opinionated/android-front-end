package com.opinionated.opinionated;

import android.graphics.Typeface;
import android.widget.Button;

/**
 * Created by matthew on 4/1/16.
 */
public class headline_button extends Button
{
    public headline_button(android.content.Context context, android.util.AttributeSet attrs)
    {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Headlinetext.ttf"));
    }
}