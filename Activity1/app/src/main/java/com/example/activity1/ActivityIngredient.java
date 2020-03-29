package com.example.activity1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Log.e("test", "C'est le test");

        displayIngredients(getIntent().getStringExtra("url"));

        Button returnView = findViewById(R.id.ingredient_retour);
        returnView.setOnClickListener(v -> {
            finish();
        });
    }

    private void displayIngredients(String URL) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("ingredients");

                            for (int i=0;i<array.length();i++) {
                                JSONObject ingrJson = array.getJSONObject(i);
                                JSONObject amountJson = ingrJson.getJSONObject("amount").getJSONObject("metric");
                                String name = ingrJson.getString("name");
                                String amount = amountJson.getInt("value") + amountJson.getString("unit");
                                ingredients.add(new Ingredient(name, amount));
                            }

                            Log.e("test", ingredients.toString());

                            IngrAdapter ingrAdapter = new IngrAdapter(getApplicationContext(), R.layout.step_elem, ingredients);
                            ListView ingrListView = findViewById(R.id.ingredient_list);
                            ingrListView.setAdapter(ingrAdapter);

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
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(objectRequest);
    }
}

class IngrAdapter extends ArrayAdapter<Ingredient> {

    public IngrAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Ingredient ingr = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_elem, parent, false);
        }

        TextView title = convertView.findViewById(R.id.step_name);
        TextView info = convertView.findViewById(R.id.step_desc);

        title.setText(ingr.name);
        info.setText(ingr.quantity);

        return convertView;
    }
}
