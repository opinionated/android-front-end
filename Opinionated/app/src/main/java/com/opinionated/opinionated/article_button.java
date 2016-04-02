package com.opinionated.opinionated;

import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

/**
 * Created by matthew on 3/22/16.
 */
public class article_button extends Button {
    public article_button(android.content.Context context, android.util.AttributeSet attrs, JSONObject article)
    {
        super(context, attrs);
        try
        {
            String title = article.getString("title");
            this.setText(title);
        }

        catch (JSONException e)
        {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }

    }
}
