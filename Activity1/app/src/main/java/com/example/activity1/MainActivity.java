package com.example.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//API KEY : 34b36018b4b34d719015a96804e28700

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = findViewById(R.id.submit);
        final EditText text = findViewById(R.id.keywords);
        final Spinner foods = findViewById(R.id.foodTypes);
        final SeekBar results = findViewById(R.id.resultsNumber);
        final TextView resultsView = findViewById(R.id.resultNumber_display);

        results.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultsView.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        String[] cuisines = {"African","American","British","Cajun","Caribbean","Chinese","Eastern European","European","French","German","Greek","Indian","Irish","Italian","Japanese","Jewish","Korean","Latin American","Mediterranean","Mexican","Middle Eastern","Nordic","Southern","Spanish","Thai","Vietnamese"};
        List<String> foodsTypes = new ArrayList<>();
        foodsTypes.addAll(Arrays.asList(cuisines));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodsTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foods.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // API request

                String API_KEY = "34b36018b4b34d719015a96804e28700";
                String URL = "https://api.spoonacular.com/recipes/search?query="+text.getText().toString().toLowerCase()+"&cuisine="+foods.getSelectedItem().toString().toLowerCase()+"&number="+results.getProgress()+"&apiKey=" + API_KEY;
                Log.e("test",URL);
                final String JSONresponse = "";

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray array = response.getJSONArray("results");
                                    Intent search = new Intent(getApplicationContext(), ActivitySearch.class);
                                    Log.e("test",array.toString());
                                    for (int i=0;i<array.length();i++) {
                                        JSONObject recipe = array.getJSONObject(i); //recette
                                        int id = recipe.getInt("id");
                                        String title = recipe.getString("title");
                                        int time = recipe.getInt("readyInMinutes");
                                        int persons = recipe.getInt("servings");
                                        String image = recipe.getString("image");
                                        ArrayList<String> images = new ArrayList<>();
                                        for (int j=0;j<recipe.getJSONArray("imageUrls").length();j++){
                                            JSONArray tmp = recipe.getJSONArray("imageUrls");
                                            images.add(j,tmp.getString(j));
                                        }
                                        Plat p = new Plat(id, title, time, persons, image, images);
                                        search.putExtra("Response"+i+1,p);
                                    }
                                    int receivedResults = response.getInt("totalResults");
                                    search.putExtra("resultsNumber", receivedResults);
//                                    search.putExtra("JSONresponse",response.toString());
                                    startActivity(search);
                                    Log.e("test",response.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("API response (err)", error.toString());
                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                );

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                requestQueue.add(objectRequest);
            }
        });
    }
}
