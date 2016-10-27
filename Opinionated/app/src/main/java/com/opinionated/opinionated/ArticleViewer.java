package com.opinionated.opinionated;

import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.LinearLayout.LayoutParams;
import java.lang.Double;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

public class ArticleViewer extends AppCompatActivity {
    private LinearLayout main_layout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ListView drawer_list;
    private ActionBarDrawerToggle drawerToggle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //REQUIRES: savedInstanceState != null
    //EFFECTS: inflates the new view
    //MODIFIES: none
    //RETURNS: none
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_articles);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        main_layout = (LinearLayout) findViewById(R.id.articleviewer_linearlayout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer_list = (ListView) findViewById(R.id.similar_art_list);

        drawerToggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.app_name, R.string.app_name) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer.addDrawerListener(drawerToggle);
        setSupportActionBar(toolbar);


        //get the json string and the id of the pressed button
        String jsonstring = getIntent().getStringExtra("JSON");
        int art_id = getIntent().getIntExtra("ID", 0);

        try {
            //load the jsonarray of articles and find the correct article
            JSONObject main_obj = new JSONObject(jsonstring);
            JSONArray jarray = main_obj.getJSONArray("article");
            JSONObject article = jarray.getJSONObject(art_id);

            //TextView to put text that goes above the ImageView in
            TextView article_text1 = new TextView(this);
            LayoutParams llp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            llp.setMargins(30, 0, 30, 0);
            article_text1.setLayoutParams(llp);
            //Create a second TextView to go below the ImageView
            TextView article_text2 = new TextView(this);
            article_text2.setLayoutParams(llp);

            //Constructs the strings to display the article information
            String title = article.getString("title");
            String body = article.getString("body");
            String author = article.getString("author");
            String time = article.getString("date");
            String top_text = title + "\n\n";
            String bottom_text = "\n\n" + author + ",\n" + time + "\n\n" + body;

            //gets the width and height to set the imageview size properly
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            //adds the image to the screen with a border around it
            String img_url = article.getString("image");
            ImageView art_img = new ImageView(this);
            LayoutParams layout_params = new LayoutParams(width - 60, height / 3);
            layout_params.gravity = Gravity.CENTER;
            art_img.setLayoutParams(layout_params);
            art_img.setBackgroundColor(Color.parseColor("#3D4547"));
            art_img.setPadding(6, 6, 6, 6);
            Picasso.with(this).load(img_url).resize(width - 60, height / 3).centerCrop().into(art_img);


            //add text to the TextViews and set the font color to off-black and sets the font size of the two textviews
            int font_size = 16;
            Double title_size = (font_size * 1.2);
            article_text1.setText(top_text);
            article_text1.setTextColor(Color.parseColor("#3d3d3d"));
            article_text1.setTextSize(title_size.intValue());
            article_text2.setText(bottom_text);
            article_text2.setTextColor(Color.parseColor("#3d3d3d"));
            article_text2.setTextSize(font_size);

            //Add the first TextView, then the ImageView, then finally the second TextView
            main_layout.addView(article_text1);
            main_layout.addView(art_img);
            main_layout.addView(article_text2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ArticleViewer Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.opinionated.opinionated/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ArticleViewer Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.opinionated.opinionated/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
