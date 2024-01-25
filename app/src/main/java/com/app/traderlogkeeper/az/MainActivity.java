package com.app.traderlogkeeper.az;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button saveTodayBtn,notesBtn;
    EditText etMoneyMade,etHoursWorked,etMinutesWorked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //logger day keeper app

        etMoneyMade = findViewById(R.id.etMoneyMade);
        etHoursWorked = findViewById(R.id.etHoursWorked);
        etMinutesWorked = findViewById(R.id.etMinutesWorked);

        saveTodayBtn = findViewById(R.id.saveTodayBtn);
        notesBtn = findViewById(R.id.notesBtn);

        saveTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etMoneyMade.getText().toString().isEmpty() && !etHoursWorked.getText().toString().isEmpty()){

                    String moneyMade = etMoneyMade.getText().toString();
                    String hoursWorked = etHoursWorked.getText().toString();
                    String minutesWorked = etMinutesWorked.getText().toString();


                }

            }
        });

        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),NotesActivity.class));
            }
        });



    }
}