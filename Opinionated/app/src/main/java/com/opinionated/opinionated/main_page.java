package com.opinionated.opinionated;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

        //returns the text in the JSON file as a string
        return json;
    }



    //REQUIRES: String Tag != null
    //EFFECTS: parses JSON from assets, creates buttons, and load images of articles onto the screen
    //MODIFIES: none
    //RETURNS: none
    //THROWS: Stacktrace if JSON can't be loaded

    //function to build the linear_layout inside the scrollview
    public void load_linear_layout(String tag)
    {
        // Create a LinearLayout element
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_lin_layout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        try {
            //create a JSON array from the JSON file stored in the Assets folder
            final String jsonstring = loadJSONFromAsset();
            JSONObject main_obj = new JSONObject(jsonstring);
            JSONArray jarray= main_obj.getJSONArray("article");
            for (int c=0; c<jarray.length(); c+=1)
            {
                //Load the current article as JSONObject
                JSONObject article = jarray.getJSONObject(c);
                String curr_tag="";

                //This is where the sorting is applied
                JSONArray art_tags=article.getJSONArray("tag");
                for (int i=0; i<art_tags.length(); i+=1)
                {
                    //if the article has a tag that matches the tag variable or if the tag variable
                    //is set to all, then display the article
                    curr_tag=art_tags.getString(i);
                    if (curr_tag.equals(tag) || tag.equals("all"))
                    {
                        //create a linearlayout for the article
                        final LinearLayout art_layout = (LinearLayout)(this.getLayoutInflater().inflate(R.layout.article_layout, null));
                        //Get the title and create+add a button
                        String title=article.getString("title");
                        Button button = (Button)(this.getLayoutInflater().inflate(R.layout.button, null));
                        button.setText(title);
                        button.setId(c);
                        art_layout.addView(button);

                        //set the on click listener to launch the article viewer activity
                        //the JSON string is passed via the intent to the new activity
                        //as well as the ID of the button so you can tell which button was pressed
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent intent = new Intent(view.getContext(), ArticleViewer.class);
                                intent.putExtra("JSON", jsonstring);
                                intent.putExtra("ID", view.getId());
                                startActivityForResult(intent, 0);
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
                        image.setId(c);
                        //resize the image to be a proportion of the screen
                        Picasso.with(this).load(url_string).resize(width,height/4).centerCrop().into(image);
                        art_layout.addView(image);

                        //set the on click listener to launch the article viewer activity
                        //the JSON string is passed via the intent to the new activity
                        //as well as the ID of the button so you can tell which button was pressed
                        image.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent intent = new Intent(view.getContext(), ArticleViewer.class);
                                intent.putExtra("JSON", jsonstring);
                                intent.putExtra("ID", view.getId());
                                startActivityForResult(intent, 0);
                            }

                        });

                        //add the current article to the button and image to the linearlayout that is hosted inside a scrollview
                        linearLayout.addView(art_layout);
                        break;
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //REQUIRES: this != null
    //EFFECTS: instantiates the view and calls the load_linear_layout method
    //MODIFIES: this
    //RETURNS: none
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




    //REQUIRES: menu != null
    //EFFECTS: inflates the options menu
    //MODIFIES: this
    //RETURNS: true if creation was successful
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }


    //REQUIRES: item != null
    //EFFECTS: changes the tag field based on the button pressed
    //MODIFIES: none
    //RETURNS: true if tag field was changed successfuly
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        LinearLayout lin_lay=(LinearLayout) findViewById(R.id.main_lin_layout);

        //the options below change the tag field based
        // on which button from the menu was selected
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