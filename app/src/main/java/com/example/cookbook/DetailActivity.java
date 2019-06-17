package com.example.cookbook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import static com.example.cookbook.CookBookActivity.EXTRA_DESCRIPTION;
import static com.example.cookbook.CookBookActivity.EXTRA_RECIPE_URL;
import static com.example.cookbook.CookBookActivity.EXTRA_TITLE;
import static com.example.cookbook.CookBookActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL); //prenesi info iz glavnog activitya u ovaj
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        String recipeUrl = intent.getStringExtra(EXTRA_RECIPE_URL);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail);
        TextView textViedDescription = findViewById(R.id.text_view_recipe_detail);

        Picasso.get().load(imageUrl.replace("http", "https")).fit().centerCrop().into(imageView);
        textViewTitle.setText(title);
        textViedDescription.setText(description);
    }

    @Override
    public void onClick(View view) {

    }

    public void browser(View view) {
        openWebpage();
    }

    private void openWebpage() {

        Intent intent = getIntent();
        String recipeUrl = intent.getStringExtra(EXTRA_RECIPE_URL);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        Log.d("DEBUG", "BUTTON PRESSED");
        browserIntent.setData(Uri.parse(recipeUrl));
        startActivity(browserIntent);
    }
}
