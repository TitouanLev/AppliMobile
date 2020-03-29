package com.example.activity1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Activity3 extends AppCompatActivity {

    private int numimage;
    private Plat plat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        plat = getIntent().getParcelableExtra("Recipie");

        String API_KEY = "34b36018b4b34d719015a96804e28700";
        String URLSteps = "https://api.spoonacular.com/recipes/"+ plat.getId() +"/analyzedInstructions?apiKey=" + API_KEY;
        String URLIngr = "https://api.spoonacular.com/recipes/"+ plat.getId() +"/ingredientWidget.json?apiKey=" + API_KEY;

        numimage = 0;

        TextView person = findViewById(R.id.info_person);
        person.setText(plat.getPersons()+"");
        TextView time = findViewById(R.id.info_time);
        time.setText(plat.getTime() + " minutes");
        TextView title = findViewById(R.id.info_title);
        title.setText(plat.getTitle());

        ImageView image = findViewById(R.id.info_image);
        updateImage(image);

        findViewById(R.id.info_image_suiv).setOnClickListener(v -> {
            numimage++;
            updateImage(image);
        });

        findViewById(R.id.info_image_prec).setOnClickListener(v -> {
            numimage--;
            updateImage(image);
        });

        findViewById(R.id.info_ingredient).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ActivityIngredient.class);
            intent.putExtra("url", URLIngr);
            startActivity(intent);
        });

        setupData(URLSteps);
    }

    private void updateImage(ImageView image) {
            int num = numimage % plat.getImages().size();
            Picasso.get().load("https://spoonacular.com/recipeImages/" + plat.getImages().get(num)).into(image);
    }

    private void setupData(String URL) {

        ArrayList<String> steps = new ArrayList<>();

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jresponse = response.getJSONObject(0);
                            JSONArray array = jresponse.getJSONArray("steps");

                            for (int i=0;i<array.length();i++) {
                                JSONObject stepJson = array.getJSONObject(i);
                                steps.add(stepJson.getString("step"));
                            }
                            StepAdapter stepAdapter = new StepAdapter(getApplicationContext(), R.layout.step_elem, steps);
                            ListView list = findViewById(R.id.info_desc);
                            list.setAdapter(stepAdapter);
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

class StepAdapter extends ArrayAdapter<String> {


    public StepAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String step = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_elem, parent, false);
        }

        TextView title = convertView.findViewById(R.id.step_name);
        TextView info = convertView.findViewById(R.id.step_desc);

        title.setText("Step "+position);
        info.setText(step);

        return convertView;
    }
}