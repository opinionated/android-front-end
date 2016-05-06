package com.opinionated.opinionated;

import android.graphics.Typeface;
import android.widget.Button;

/**
 * Created by matthew on 4/1/16.
 * This is a custom button class that is used to display the headline of the articles
 * This class can be edited to change the styling (i,e, the font, background, etc) of the article buttons
 */
public class headline_button extends Button
{
    public headline_button(android.content.Context context, android.util.AttributeSet attrs)
    {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Headlinetext.ttf"));
    }
}