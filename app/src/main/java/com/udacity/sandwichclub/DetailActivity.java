package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView mSandwichIv;
    TextView mDescriptionTv, mAlsoKnownAsTv,mOriginTv,mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDescriptionTv =  findViewById(R.id.description_tv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        mOriginTv =  findViewById(R.id.origin_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mSandwichIv =  findViewById(R.id.image_iv);


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
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnown = sandwich.getAlsoKnownAs();
        for (int i=0; i< alsoKnown.size(); i++) {
            if (i > 0) mAlsoKnownAsTv.append(", ");
            mAlsoKnownAsTv.append(alsoKnown.get(i));
        }

        List<String> ingredients = sandwich.getIngredients();
        for (int j=0; j< ingredients.size(); j++) {
            mIngredientsTv.append(ingredients.get(j)+", ");
        }
        mOriginTv.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTv.setText(sandwich.getDescription());

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mSandwichIv);
    }
}