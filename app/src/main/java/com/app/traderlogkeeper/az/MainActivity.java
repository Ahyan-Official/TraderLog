package com.app.traderlogkeeper.az;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button saveTodayBtn,notesBtn;
    EditText etMoneyMade,etHoursWorked,etMinutesWorked;
    TextView etMonthlyMoney,etMonthlyHours,TotalWin,TotalLoss,winLossRatio,TotalWeeklyMoney,TotalWeeklyHours
            ,yearTotal,yearlyWinPercentage,yearAverage;
    TradeDatabaseHelper myDB;
    CalendarView calendarView;
    String dateSearchAndAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //logger day keeper app

        myDB = new TradeDatabaseHelper( this );
        yearAverage = findViewById(R.id.yearAverage);

        TotalWeeklyMoney = findViewById(R.id.TotalWeeklyMoney);
        TotalWeeklyHours = findViewById(R.id.TotalWeeklyHours);
        yearTotal = findViewById(R.id.yearTotal);
        yearlyWinPercentage = findViewById(R.id.yearlyWinPercentage);

        TotalWin = findViewById(R.id.TotalWin);


        TotalWin = findViewById(R.id.TotalWin);
        TotalLoss = findViewById(R.id.TotalLoss);

        winLossRatio = findViewById(R.id.winLossRatio);


        etMoneyMade = findViewById(R.id.etMoneyMade);
        etHoursWorked = findViewById(R.id.etHoursWorked);
        etMinutesWorked = findViewById(R.id.etMinutesWorked);

        saveTodayBtn = findViewById(R.id.saveTodayBtn);
        notesBtn = findViewById(R.id.notesBtn);

        etMonthlyMoney = findViewById(R.id.etMonthlyMoney);
        etMonthlyHours = findViewById(R.id.etMonthlyHours);
        calendarView = findViewById(R.id.calenderView);



        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateSearchAndAdd = df.format(new Date());
        Log.e("ooo", "onCreate: "+ myDB.getTotalMoney(dateSearchAndAdd));

        if((!myDB.getTotalMoney(dateSearchAndAdd).equalsIgnoreCase("note"))){

            etMoneyMade.setText(myDB.getTotalMoney(dateSearchAndAdd));
            etHoursWorked.setText(myDB.geTotalHours(dateSearchAndAdd));;

        }

        updateValues(dateSearchAndAdd);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                int m = month+1;

                String mm = addZerodata(m);
                String day = addZerodata(dayOfMonth);
                dateSearchAndAdd = year+"-"+mm+"-"+day;

                Toast.makeText(getApplicationContext(),year+"-"+mm+"-"+day , Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub

                if((!myDB.getTotalMoney(dateSearchAndAdd).isEmpty())){
                    etMoneyMade.setText(myDB.getTotalMoney(dateSearchAndAdd));
                    etHoursWorked.setText(myDB.geTotalHours(dateSearchAndAdd));;

                }
                updateValues(dateSearchAndAdd);

            }
        });
        saveTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etMoneyMade.getText().toString().isEmpty() && !etHoursWorked.getText().toString().isEmpty() && !etMinutesWorked.getText().toString().isEmpty()){

                    String moneyMade = etMoneyMade.getText().toString();
                    String hoursWorked = etHoursWorked.getText().toString();
                    String minutesWorked = etMinutesWorked.getText().toString();


                    double minutes = Double.parseDouble(minutesWorked);
                    double hours = Double.parseDouble(hoursWorked);
                    double money = Double.parseDouble(moneyMade);
                    double hoursfromminutes = minutes/60;

                    double totalHours = hours+hoursfromminutes;

                    //Current Date
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//                    String date = df.format(new Date());


                    if(myDB.checkDate(dateSearchAndAdd)){
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        //update

                        if(money<0){
                            boolean isUpdated = myDB.updateData( money, totalHours, dateSearchAndAdd,0,1 );

                            if (isUpdated == true){
                             //   showMessage( "Update", "Your data has been successfully updated!" );
                            }
                            else{
                                //showMessage( "Update failed", "Cannot Update your data :(" );

                            }
                        }else{
                            boolean isUpdated = myDB.updateData( money, totalHours, dateSearchAndAdd,1,0 );

                            if (isUpdated == true){
                              //  showMessage( "Update", "Your data has been successfully updated!" );
                            }
                            else{
                              //  showMessage( "Update failed", "Cannot Update your data :(" );

                            }
                        }

                    }else{
                        //insert
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                        if(money<0){

                            boolean isInserted = myDB.instertData( money, totalHours, dateSearchAndAdd,0,1);

                            if(isInserted == true){
                             //   Toast.makeText( MainActivity.this, "Data is inserted", Toast.LENGTH_SHORT ).show();
                            }
                            else{
                               // Toast.makeText( MainActivity.this, "Data is not inserted", Toast.LENGTH_SHORT ).show();

                            }
                        }else{
                            boolean isInserted = myDB.instertData( money, totalHours, dateSearchAndAdd,1,0);

                            if(isInserted == true){
                              //  Toast.makeText( MainActivity.this, "Data is inserted", Toast.LENGTH_SHORT ).show();
                            }
                            else{
                               // Toast.makeText( MainActivity.this, "Data is not inserted", Toast.LENGTH_SHORT ).show();

                            }
                        }

                    };

                    updateValues( dateSearchAndAdd);


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
    public String addZerodata(int m) {
        if (m < 10) {
            return "0" + (String.valueOf(m));
        } else {
            return String.valueOf(m);
        }
    }

    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( message );
        builder.show();
    }

    public void updateValues(String dateSearchAndAdd){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
       // String date = df.format(new Date());
        //Log.e("ooo1   ", "onCreate: "+ myDB.getTotalMoney("'"+dateSearchAndAdd+"'"));

        //Today Hours and Days
        //float TotalLossesInYear = myDB.CurrentYearlyWinWithFilter("'"+dateSearchAndAdd+"'");
        //Log.e("llo", "updateValues: "+myDB.CurrentWeeklyHours("'"+dateSearchAndAdd+"'") );

        if(myDB.getTotalMoney(dateSearchAndAdd).isEmpty()){
            etMoneyMade.setText("");
            etHoursWorked.setText("");
            etMinutesWorked.setText("");

        }
            etMoneyMade.setText(myDB.getTotalMoney(dateSearchAndAdd));
            etHoursWorked.setText(myDB.geTotalHours(dateSearchAndAdd));;

            //Monthly

            etMonthlyMoney.setText(String.valueOf(myDB.CurrentMonthyMoney("'"+dateSearchAndAdd+"'")));;
            etMonthlyHours.setText(String.valueOf(myDB.CurrentMonthyHours("'"+dateSearchAndAdd+"'")));;

            DecimalFormat d = new DecimalFormat();
            d.setMaximumFractionDigits(2);
            String hoursInTwoDecimal =String.valueOf(d.format(Float.parseFloat(myDB.geTotalHours(dateSearchAndAdd))));
            Log.e("vvvvvvv", "updateValues: "+ hoursInTwoDecimal);

            String s_input = hoursInTwoDecimal;
            if (s_input.contains(".")) {
                String[] split = s_input.split("\\.");
                String whole = split[0];
                etHoursWorked.setText(whole);
             //   Toast.makeText(this, ""+whole, Toast.LENGTH_SHORT).show();

                String fractional = split[1];

                String minutesinDecimal = hoursInTwoDecimal.substring(hoursInTwoDecimal.indexOf("."));//0
                double totalMinutes = Double.parseDouble(minutesinDecimal)*60;

                etMinutesWorked.setText(String.valueOf(totalMinutes));
            }else{
                etMinutesWorked.setText(String.valueOf(0));

            }



            //Get Win Loss
            TotalWin.setText(String.valueOf(myDB.CurrentMonthlyWin("'"+dateSearchAndAdd+"'")));
            TotalLoss.setText(String.valueOf(myDB.CurrentMonthlyLoss("'"+dateSearchAndAdd+"'")));

            float ratio = myDB.CurrentMonthlyWin("'"+dateSearchAndAdd+"'")/myDB.CurrentMonthlyLoss("'"+dateSearchAndAdd+"'");

            winLossRatio.setText(String.valueOf(myDB.CurrentMonthlyWin("'"+dateSearchAndAdd+"'"))+"/"+String.valueOf(myDB.CurrentMonthlyLoss("'"+dateSearchAndAdd+"'")));

            //weekly Done
            TotalWeeklyMoney.setText(String.valueOf(myDB.getWeeklyMoneyMade("'"+dateSearchAndAdd+"'")));
            TotalWeeklyHours.setText(String.valueOf(myDB.CurrentWeeklyHours("'"+dateSearchAndAdd+"'")));



            //Year Total Done

            String s_inputYearHours = String.valueOf(myDB.CurrentYearlyHours("'"+dateSearchAndAdd+"'"));
            if (s_inputYearHours.contains(".")) {
                String[] split = s_inputYearHours.split("\\.");

                String yearHoursTotal = split[0];



                String fractional = split[1].substring(0,1);
                yearHoursTotal  =yearHoursTotal+"."+fractional;

                yearTotal.setText(String.valueOf(myDB.CurrentYearlyMoney("'"+dateSearchAndAdd+"'")+"/"+yearHoursTotal+" Hr"));

            }else{
                yearTotal.setText(String.valueOf(myDB.CurrentYearlyMoney("'"+dateSearchAndAdd+"'")+"/"+myDB.CurrentYearlyHours("'"+dateSearchAndAdd+"'")+" Hr"));

            }

            float TotalWinInYear = myDB.CurrentYearlyWins("'"+dateSearchAndAdd+"'");
            //float TotalLossesInYear = myDB.CurrentYearlyWinWithFilter("'"+dateSearchAndAdd+"'");
            float TotalTradesDoneInYear = myDB.CurrentYearlyWins("'"+dateSearchAndAdd+"'")+myDB.CurrentYearlyLoss("'"+dateSearchAndAdd+"'");
            double percentageWin = (TotalWinInYear/TotalTradesDoneInYear)*100;
            //double percentageWinIN100 = percentageWin*100;

            //YearAverage 2 decimal
        double yearAvg = myDB.CurrentYearlyMoney("'"+dateSearchAndAdd+"'")/myDB.CurrentYearlyHours("'"+dateSearchAndAdd+"'");

        String avgStr = String.valueOf(yearAvg);
            if (avgStr.contains(".")) {
                String[] split = avgStr.split("\\.");

                String yearavg = split[0];



                String fractional = split[1].substring(0,1);
                yearavg  =yearavg+"."+fractional;

                yearAverage.setText("$"+yearavg+ "/Hr");

            }else{
                yearAverage.setText("$"+avgStr+ "/Hr");

            }


            yearlyWinPercentage.setText(String.valueOf(percentageWin)+"%");




    }
}