package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import android.widget.TextView;
import android.text.TextUtils;

import java.util.List;

import android.graphics.Color;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView name_tv = findViewById(R.id.name_tv);
        name_tv.setText(sandwich.getMainName());

        TextView also_known_tv = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        String alsoKnownAs;
        if (!alsoKnownAsList.isEmpty()) {
            alsoKnownAs = TextUtils.join(", ", alsoKnownAsList);
        } else {
            alsoKnownAs = "alternate names not found";
            also_known_tv.setTextColor(Color.parseColor("#C51162"));
        }
        also_known_tv.setText(alsoKnownAs);

        TextView origin_tv = findViewById(R.id.origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (!placeOfOrigin.isEmpty()) {
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        } else {
            origin_tv.setText("place of origin not found");
            origin_tv.setTextColor(Color.parseColor("#C51162"));
        }

        TextView description_tv = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (!description.isEmpty()) {
            description_tv.setText(sandwich.getDescription());
        } else {
            description_tv.setText("description not found");
            description_tv.setTextColor(Color.parseColor("#C51162"));
        }

        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        String ingredients;
        if (!ingredientsList.isEmpty()) {
            ingredients = TextUtils.join(", ", ingredientsList);
        } else {
            ingredients = "ingredients not found";
            ingredients_tv.setTextColor(Color.parseColor("#C51162"));
        }
        ingredients_tv.setText(ingredients);
    }
}
