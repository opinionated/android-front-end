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
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

            //create a new textview and put the article title and
            //article body into the textview, then put the textview into a scrollview
            TextView article_text = new TextView(this);
            article_text.setTextColor(Color.parseColor("#000000"));
            String title=article.getString("title");
            String body = article.getString("body");
            String entire_article=title + "\n\n" +body;
            article_text.setText(entire_article);
            main_layout.addView(article_text);
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
