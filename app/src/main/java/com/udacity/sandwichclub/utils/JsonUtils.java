package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static String TAG = JsonUtils.class.getSimpleName();

    private final static String JSON_NAME = "name";
    private final static String SANDWICH_NAME = "mainName";
    private final static String ALSO_KNOWN_AS_LIST = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String SANDWICH_DESCRIPTION = "description";
    private final static String IMAGE_URL = "image";
    private final static String INGREDIENTS_LIST = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try {
            // Creating of new JSON object which holds all the JSON data of selected sandwich
            JSONObject mainJsonObject = new JSONObject(json);


            JSONObject name = mainJsonObject.getJSONObject(JSON_NAME);
            String mainName = name.getString(SANDWICH_NAME);

            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS_LIST);
            List<String> alsoKnownAs = convertToListFromJsonArray(JSONArrayAlsoKnownAs);

            String placeOfOrigin = mainJsonObject.optString(PLACE_OF_ORIGIN);

            String description = mainJsonObject.getString(SANDWICH_DESCRIPTION);

            String image = mainJsonObject.getString(IMAGE_URL);

            JSONArray JSONArrayIngredients = mainJsonObject.getJSONArray(INGREDIENTS_LIST);
            List<String> ingredients = convertToListFromJsonArray(JSONArrayIngredients);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> convertToListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
