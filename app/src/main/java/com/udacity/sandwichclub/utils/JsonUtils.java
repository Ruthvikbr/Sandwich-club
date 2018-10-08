package com.udacity.sandwichclub.utils;

import android.net.Network;
import android.net.Uri;
import android.util.Log;
import android.widget.ScrollView;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();


    public static Sandwich parseSandwichJson(String json) {
        JSONObject sandwitchJson;
        Sandwich sandwich = new Sandwich();

        Log.d(TAG,"parseSandwichJson Json = "+json);

        try {
            sandwitchJson = new JSONObject(json);
            JSONObject nameJson = sandwitchJson.getJSONObject("name");
            String mainName = nameJson.getString("mainName");
            JSONArray alsoKnownAsJson = nameJson.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i=0; i<alsoKnownAsJson.length(); i++) {
                String str = alsoKnownAsJson.getString(i);
                alsoKnownAs.add(str);
            }
            String origin = sandwitchJson.getString("placeOfOrigin");
            String imageUrl = sandwitchJson.getString("image");

            JSONArray ingredientsJson = sandwitchJson.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int j=0; j<ingredientsJson.length(); j++) {
                String str = ingredientsJson.getString(j);
                ingredients.add(str);
            }
            String description = sandwitchJson.getString("description");

            
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setIngredients(ingredients);
            sandwich.setPlaceOfOrigin(origin);
            sandwich.setImage(imageUrl);
            sandwich.setDescription(description);

        } catch (JSONException e) {
            Log.e(TAG,"JSON Parsing Error !!!");
            return null;
        }

        return sandwich;
    }
}
