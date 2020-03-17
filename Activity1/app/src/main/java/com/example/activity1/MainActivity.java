package com.example.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditText text = findViewById(R.id.keywords);
        Spinner foods = findViewById(R.id.foodTypes);
        SeekBar results = findViewById(R.id.resultsNumber);
        Button submit = findViewById(R.id.submit);

        
    }
}
