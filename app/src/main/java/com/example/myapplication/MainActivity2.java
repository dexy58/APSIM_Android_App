package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        CalendarView calendarView = findViewById(R.id.calendarView2);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        String selectedDate = dateFormatter.format(new Date(calendarView.getDate()));

        TextView tx = findViewById(R.id.textView);
        tx.setText("Button clicked: " + selectedDate);
    }

    public void onClick(View view) {
        CalendarView calendarView = findViewById(R.id.calendarView2);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        String selectedDate = dateFormatter.format(new Date(calendarView.getDate()));

        TextView tx = findViewById(R.id.textView);
        tx.setText("Button clicked: " + selectedDate);
    }
}