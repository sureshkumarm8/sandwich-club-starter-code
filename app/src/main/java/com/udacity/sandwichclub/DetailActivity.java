package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
    TextView tvPlaceofOrigin, tvDescription, tvAlsoNameas, tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvPlaceofOrigin= findViewById(R.id.origin_tv);
        tvDescription= findViewById(R.id.description_tv);
        tvAlsoNameas= findViewById(R.id.also_known_tv);
        tvIngredients= findViewById(R.id.ingredients_tv);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // Retrieving private data from Sandwich model
        tvPlaceofOrigin.setText(sandwich.getPlaceOfOrigin());
        tvDescription.setText(sandwich.getDescription());
        tvAlsoNameas.setText(getListAsString(sandwich.getAlsoKnownAs()));
        tvIngredients.setText(getListAsString(sandwich.getIngredients()));
    }
    private String getListAsString(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        if (strings != null) {
            for (int i = 0; i < strings.size(); i++) {
                sb.append(strings.get(i));
                if (i < strings.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

}
