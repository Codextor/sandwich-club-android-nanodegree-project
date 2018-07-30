package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.*;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        //If json received is null, return null object
        if (json == null) {
            return null;
        }
        //Parse the json string received
        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject name = sandwichJSON.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.get(i).toString());
            }

            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");

            String description = sandwichJSON.getString("description");

            String image = sandwichJSON.getString("image");

            JSONArray ingredientsArray = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.get(i).toString());
            }

            //Create a sandwich object with the parsed arguments and return it
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e("Sandwich Club", "unexpected JSON exception", e);
            return null;
        }
    }
}
