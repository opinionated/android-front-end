package com.opinionated.opinionated;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
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
import android.view.Display;
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

    String tag = "all";

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
/*
    public void launch_article(View v)
    {
        Intent intent = new Intent(getApplicationContext(), ArticleViewer.class);
        startActivity(intent);
    }*/

    //function to build the linear_layout inside the scrollview
    public void load_linear_layout(String tag)
    {
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
                String curr_tag="";

                JSONArray art_tags=article.getJSONArray("tag");
                for (int i=0; i<art_tags.length(); i+=1)
                {
                    curr_tag=art_tags.getString(i);
                    if (curr_tag.equals(tag) || tag.equals("all"))
                    {
                        //create a linearlayout for the article
                        final LinearLayout art_layout = (LinearLayout)(this.getLayoutInflater().inflate(R.layout.article_layout, null));
                        //Get the title and create+add a button
                        String title=article.getString("title");
                        Button button = (Button)(this.getLayoutInflater().inflate(R.layout.button, null));
                        button.setText(title);
                        art_layout.addView(button);

                        //set the on click listener to launch the article viewer activity
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent myIntent = new Intent(view.getContext(), ArticleViewer.class);
                                TextView art_viewer=(TextView) findViewById(R.id.article_textview);
                                startActivityForResult(myIntent, 0);
                            }

                        });


                        //get the size of the display to make the image fit the display
                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;
                        int height=size.y;


                        //Load an image from it's URL and place below the button just instantiated
                        ImageView image = (ImageView) new ImageView(this);
                        String url_string=article.getString("image");
                        Picasso.with(this).load(url_string).resize(width,height/4).centerCrop().into(image);
                        art_layout.addView(image);

                        linearLayout.addView(art_layout);
                        break;
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        load_linear_layout("all");

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
        LinearLayout lin_lay=(LinearLayout) findViewById(R.id.main_lin_layout);
        //noinspection SimplifiableIfStatement
        if (id == R.id.All) {
            tag="all";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.Business)
        {
            tag="Business";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.Entertainment)
        {
            tag="Entertainment";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.News)
        {
            tag="News";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.Politics)
        {
            tag="Politics";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.Technology)
        {
            tag="Technology";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.Entertainment)
        {
            tag="Entertainment";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.Sports)
        {
            tag="Sports";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }

        else if (id==R.id.Opinion)
        {
            tag="Opinion";
            lin_lay.removeAllViews();
            load_linear_layout(tag);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}