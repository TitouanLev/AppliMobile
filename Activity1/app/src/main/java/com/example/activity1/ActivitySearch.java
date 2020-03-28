package com.example.activity1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivitySearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle data = getIntent().getExtras();
//        Plat plat = gson.fromJson(data.getString("JSONresponse"), Plat.class);
        Log.e("test2",data.toString());
        ArrayList<Plat> plats = new ArrayList<>();
        for (int i=0;i<data.getInt("resultsNumber");i++){
            Plat p = getIntent().getParcelableExtra("Response"+i+1);
            Log.e("test",p.toString());
            plats.add(p);
        }

        ListView platView = findViewById(R.id.search_result);

        platView.setAdapter(new PlatArrAdapter(this, R.layout.search_elem , plats));
    }
}

class PlatArrAdapter extends ArrayAdapter<Plat> {

    public PlatArrAdapter(@NonNull Context context, int resource, @NonNull List<Plat> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Plat plat = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_elem, parent, false);
        }

        TextView title = convertView.findViewById(R.id.searh_elem_name);
        TextView info = convertView.findViewById(R.id.searh_elem_info);

        title.setText(plat.getTitle());
        info.setText(plat.getTime() + " minutes - " + plat.getPersons() + " personnes");

        return convertView;
    }
}
