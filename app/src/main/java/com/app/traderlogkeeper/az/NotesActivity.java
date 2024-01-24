package com.app.traderlogkeeper.az;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {


    Button saveBtn;
    EditText etDes,etTitle;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        myDB = new DatabaseHelper( this );

        saveBtn = findViewById(R.id.saveBtn);
        etTitle = findViewById(R.id.etTitle);
        etDes = findViewById(R.id.etDes);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        viewData();
        //updateData();
        deleteData();


        saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString();
                String des = etDes.getText().toString();

                //Current Date
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String date = df.format(new Date());

                boolean isInserted = myDB.instertData( title, des, date);

                if(isInserted == true){
                    Toast.makeText( NotesActivity.this, "Data is inserted", Toast.LENGTH_SHORT ).show();
                }
                else
                    Toast.makeText( NotesActivity.this, "Data is not inserted", Toast.LENGTH_SHORT ).show();
            }
        } );


    }


    //Adding or inserting data to database
    public void AddData(){

    }

    //For viewing data in database
    public void viewData(){

//        viewData.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Cursor res = myDB.getData();
//
//                if (res.getCount() == 0){
//                    showMessage("Error", "Data not found!");
//                }
//
//                else{
//                    StringBuffer buffer = new StringBuffer();
//                    while (res.moveToNext()){
//                        buffer.append( "Name: " + res.getString( 1 ) + "\n" );
//                        buffer.append( "Surname: " + res.getString( 2 ) + "\n" );
//                        buffer.append( "Insertion/Updation Date:\n" + res.getString( 3 ) + "\n\n" );
//                    }
//
//                    showMessage( "Data", buffer.toString() );
//
//                }
//            }
//        } );
    }

    //For updating existing data in database
    public void updateData(){

        saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString();
                String des = etDes.getText().toString();

                //Current Date
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String date = df.format(new Date());
                //Current Date and Time


                boolean isUpdated = myDB.updateData( title, des, date );

                if (isUpdated == true){
                    showMessage( "Update", "Your data has been successfully updated!" );
                }
                else
                    showMessage( "Update failed", "Cannot Update your data :(" );
            }
        } );
    }

    //For deleting data in the database
    public void deleteData(){

//        deleteData.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                id = edit_id.getText().toString();
//
//                Integer res = myDB.deleteData( id );
//                if(res > 0){
//                    Toast.makeText( getApplicationContext(), "Row effected", Toast.LENGTH_SHORT ).show();
//                }
//                else{
//                    Toast.makeText( getApplicationContext(), "Row not effected", Toast.LENGTH_SHORT ).show();
//                }
//
//            }
//        } );
    }

    //Method for creating AlertDialog box
    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( message );
        builder.show();
    }
}