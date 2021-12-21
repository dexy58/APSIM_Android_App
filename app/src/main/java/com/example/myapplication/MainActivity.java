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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
            /*TextView tx = findViewById(R.id.textView);
            Spinner sp = findViewById(R.id.spinner);
            tx.setText("Button clicked: " + sp.getSelectedItem().toString());
            tx.setText("Button clicked: " + dateFormatter.format(calendarStart.getTime()));*/

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        String outputJSON = new String();
                        try {

                            URL url = new URL("http://10.19.0.105:8080/getPrediction");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json");

                            String input = "{\n" +
                                    "    \"name\": \"corn\",\n" +
                                    "    \"startDate\" : \"25-mar\",\n" +
                                    "    \"endDate\" : \"8-apr\"\n" +
                                    "}";

                            try (OutputStream os = conn.getOutputStream()) {
                                os.write(input.getBytes());
                                os.flush();
                            }

                            String output;
                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    (conn.getInputStream())));
                            System.out.println("Output from Server .... \n");
                            while ((output = br.readLine()) != null) {
                                outputJSON += output;
                            }
                            conn.disconnect();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        openPredictionScreen(outputJSON);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }
    }

    public void openPredictionScreen(String output){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("key", output);
        startActivity(intent);
    }
}