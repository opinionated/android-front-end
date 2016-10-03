package com.opinionated.opinionated;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.squareup.picasso.Picasso;

public class ArticleViewer extends AppCompatActivity {

    //REQUIRES: savedInstanceState != null
    //EFFECTS: inflates the new view
    //MODIFIES: none
    //RETURNS: none
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_scrollview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout main_layout= (LinearLayout) findViewById(R.id.articleviewer_linearlayout);

        //get the json string and the id of the pressed button
        String jsonstring = getIntent().getStringExtra("JSON");
        int art_id=getIntent().getIntExtra("ID", 0);

        try {
            //load the jsonarray of articles and find the correct article
            JSONObject main_obj = new JSONObject(jsonstring);
            JSONArray jarray= main_obj.getJSONArray("article");
            JSONObject article = jarray.getJSONObject(art_id);

            //TextView to put text that goes above the ImageView in
            TextView article_text1 = new TextView(this);
            article_text1.setEms(20);
            article_text1.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            llp.setMargins(30, 0, 0, 0);
            article_text1.setLayoutParams(llp);
            //Create a second TextView to go below the ImageView
            TextView article_text2 = new TextView(this);
            article_text2.setEms(20);
            article_text2.setGravity(Gravity.CENTER_VERTICAL);
            article_text2.setLayoutParams(llp);

            //Constructs the strings to display the article information
            String title = article.getString("title");
            String body = article.getString("body");
            String author = article.getString("author");
            String time = article.getString("date");
            String top_text = title +"\n\n";
            String bottom_text = "\n\n"+author+",\n"+time+"\n\n" +body;

            //gets the width and height to set the imageview size properly
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height=size.y;

            //adds the image to the screen
            String img_url = article.getString("image");
            ImageView art_img = new ImageView(this);
            Picasso.with(this).load(img_url).resize(width-60,height/4).centerCrop().into(art_img);

            //add text to the TextViews and set the font color to black
            article_text1.setText(top_text);
            article_text1.setTextColor(Color.parseColor("#000000"));
            article_text2.setText(bottom_text);
            article_text2.setTextColor(Color.parseColor("#000000"));

            //Add the first TextView, then the ImageView, then finally the second TextView
            main_layout.addView(article_text1);
            main_layout.addView(art_img);
            main_layout.addView(article_text2);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
