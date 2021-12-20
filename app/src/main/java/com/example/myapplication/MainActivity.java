package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerLanguages=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.cultures, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        DatePicker simpleDatePickerStart = findViewById(R.id.simpleDatePicker); // initiate a date picker
        simpleDatePickerStart.setSpinnersShown(false); // set false value for the spinner shown function

        DatePicker simpleDatePickerEnd = findViewById(R.id.simpleDatePicker2); // initiate a date picker
        simpleDatePickerEnd.setSpinnersShown(false); // set false value for the spinner shown function
    }
}