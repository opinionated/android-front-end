package com.opinionated.opinionated;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import com.squareup.picasso.Picasso;

public class main_page extends AppCompatActivity {

    //function to read JSON from assets folder
    public String loadJSONFromAsset() {
        String json = null;
        try
        {

            InputStream is = getAssets().open("exArt1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        // Find the ScrollView
        ScrollView scrollView = (ScrollView) findViewById(R.id.main_scroll_view);

        // Create a LinearLayout element
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_lin_layout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        try {
            JSONObject main_obj = new JSONObject(loadJSONFromAsset());
            JSONArray jarray= main_obj.getJSONArray("article");
            for (int c=0; c<jarray.length(); c+=1)
            {

                //Load article as JSONObject
                JSONObject article = jarray.getJSONObject(c);

                //create a linearlayout for the article
                LinearLayout art_layout = (LinearLayout)(this.getLayoutInflater().inflate(R.layout.article_layout, null));
                //Get the title and create+add a button
                String title=article.getString("title");
                Button button = (Button)(this.getLayoutInflater().inflate(R.layout.button, null));
                button.setText(title);
                art_layout.addView(button);


                //Load an image from it's URL and place below the button just instantiated

                ImageView image = (ImageView) new ImageView(this);
                String url_string=article.getString("image");
                Picasso.with(this).load(url_string).resize(800,300).centerCrop().into(image);
                art_layout.addView(image);

                linearLayout.addView(art_layout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

// Add the LinearLayout element to the ScrollView
        //scrollView.addView(linearLayout);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.All) {
            return true;
        }

        else if (id==R.id.Business)
        {
            return true;
        }

        else if (id==R.id.Entertainment)
        {
            return true;
        }

        else if (id==R.id.News)
        {
            return true;
        }

        else if (id==R.id.Politics)
        {
            return true;
        }

        else if (id==R.id.Technology)
        {
            return true;
        }

        else if (id==R.id.Entertainment)
        {
            return true;
        }

        else if (id==R.id.Sports)
        {
            return true;
        }

        else if (id==R.id.Opinion)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}