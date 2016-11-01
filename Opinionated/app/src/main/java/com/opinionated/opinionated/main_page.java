package com.opinionated.opinionated;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso;
import java.util.HashSet;

public class main_page extends AppCompatActivity {

    HashSet<String> tags = new HashSet<String>();
    //function to read JSON from assets folder
    //This function is from a stackoverflow post found at this link "http://stackoverflow.com/questions/19945411/android-java-how-can-i-parse-a-local-json-file-from-assets-folder-into-a-listvi"
    //Function written by stackoverflow user "GrIsHu", use in accordance with Creative Commons License CC BY-SA 3.0
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
    public void load_linear_layout(HashSet<String> tags)
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
                String curr_tag;

                //This is where the sorting is applied
                JSONArray art_tags=article.getJSONArray("tag");
                for (int i=0; i<art_tags.length(); i+=1)
                {
                    //if the article has a tag that is in the tags set or if the tags
                    //set contains the all tag, then display the article
                    curr_tag=art_tags.getString(i);
                    if (tags.contains(curr_tag) || tags.contains("All"))
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
                        image.setBackgroundColor(Color.parseColor("#3D4547"));
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
        tags.add("All");
        load_linear_layout(tags);
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
        LinearLayout lin_lay=(LinearLayout) findViewById(R.id.main_lin_layout);
        if (item.isChecked())
        {
            item.setChecked(false);
            tags.remove(item.getTitle().toString());
        }
        else
        {
            item.setChecked(true);
            tags.add(item.getTitle().toString());
        }

        lin_lay.removeAllViews();
        load_linear_layout(tags);

        return super.onOptionsItemSelected(item);
    }
}