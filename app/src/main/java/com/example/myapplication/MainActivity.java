package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerLanguages=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.cultures, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        CalendarView calendarViewStart = findViewById(R.id.simpleDatePicker);
        calendarViewStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                calendarStart.set(year, month, day);
            }
        });

        CalendarView calendarViewEnd = findViewById(R.id.simpleDatePicker2);
        calendarViewEnd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                calendarEnd.set(year, month, day);
            }
        });
    }

    public void onClick(View view) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        long difference = calendarEnd.getTime().getTime() - calendarStart.getTime().getTime();
        long seconds = difference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days < 0){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Wrong Date!");
            alertBuilder.setMessage("End Date must be after Start Date.");

            alertBuilder.setNegativeButton("Close!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            alertBuilder.show();
        }
        else if (days > 15){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Wrong Date!");
            alertBuilder.setMessage("Difference between Start and End date cannot be more than 15 days apart!");

            alertBuilder.setNegativeButton("Close!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            alertBuilder.show();
        }
        else{

            TextView tx = findViewById(R.id.textView);

            //Spinner sp = findViewById(R.id.spinner);
            //tx.setText("Button clicked: " + sp.getSelectedItem().toString());

            tx.setText("Button clicked: " + dateFormatter.format(calendarStart.getTime()));
            //openPredictionScreen();
        }
    }

    public void openPredictionScreen(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}