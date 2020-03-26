package com.example.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ActivitySearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle data = getIntent().getExtras();
//        Plat plat = gson.fromJson(data.getString("JSONresponse"), Plat.class);
        Log.e("test2",data.toString());
        for (int i=0;i<data.getInt("resultsNumber");i++){
            Log.e("test",data.getBundle("Response"+i+1).getInt("id")+"");
            Log.e("test",data.getBundle("Response"+i+1).getString("title")+"");
            Log.e("test",data.getBundle("Response"+i+1).getInt("time")+"");
            Log.e("test",data.getBundle("Response"+i+1).getInt("persons")+"");
            Log.e("test",data.getBundle("Response"+i+1).getString("image")+"");
            Log.e("test",data.getBundle("Response"+i+1).getStringArrayList("images")+"");
        }
    }
}
