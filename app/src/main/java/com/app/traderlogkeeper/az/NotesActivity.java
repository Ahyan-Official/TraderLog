package com.app.traderlogkeeper.az;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = df.format(new Date());
        etTitle.setText(myDB.getNoteTitle(date));;
        etDes.setText(myDB.getNoteDes(date));;




        viewData();
        //updateData();
       // deleteData();


        saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString();
                String des = etDes.getText().toString();

                //Current Date
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String date = df.format(new Date());


                if(myDB.checkDate(date)){
                   // Toast.makeText(NotesActivity.this, "Update", Toast.LENGTH_SHORT).show();
                    //update
                    boolean isUpdated = myDB.updateData( title, des, date );
                    Toast.makeText( NotesActivity.this, "Saved", Toast.LENGTH_SHORT ).show();

                    if (isUpdated == true){
                       // showMessage( "Update", "Note has been successfully updated!" );
                    }
                    else{
                       // showMessage( "Update failed", "Cannot Update your data :(" );

                    }
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }else{
                    //insert
                   // Toast.makeText(NotesActivity.this, "Insert", Toast.LENGTH_SHORT).show();

                    boolean isInserted = myDB.instertData( title, des, date);

                    if(isInserted == true){
                        Toast.makeText( NotesActivity.this, "Note is inserted", Toast.LENGTH_SHORT ).show();
                    }
                    else{
                        Toast.makeText( NotesActivity.this, "Note is not inserted", Toast.LENGTH_SHORT ).show();

                    }
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                };


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

    //For deleting data in the database

    //Method for creating AlertDialog box
    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( message );
        builder.show();
    }


//    public boolean checkAlreadyExist(String email)
//    {
//        String query = SELECT + YOUR_EMAIL_COLUMN + FROM + TABLE_NAME + WHERE + YOUR_EMAIL_COLUMN + " =?";
//        Cursor cursor = db.rawQuery(query, new String[]{email});
//        if (cursor.getCount() > 0)
//        {
//            return false;
//        }
//        else
//            return true;
//    }
//    public boolean checkIfRecordExist(String TABLE_NAME,String COL_2,String chek)
//    {
//        try
//        {
//            SQLiteDatabase db=this.getReadableDatabase();
//            Cursor cursor=db.rawQuery("SELECT "+COL_2+" FROM "+TABLE_NAME+" WHERE "+COL_2+"='"+COL_2+"'",null);
//            if (cursor.moveToFirst())
//            {
//                db.close();
//                Log.d("Record  Already Exists", "Table is:"+TABLE_NAME+" ColumnName:"+COL_2);
//                return true;//record Exists
//
//            }
//            Log.d("New Record  ", "Table is:"+TABLE_NAME+" ColumnName:"+COL_2+" Column Value:"+COL_2);
//            db.close();
//        }
//        catch(Exception errorException)
//        {
//            Log.d("Exception occured", "Exception occured "+errorException);
//            // db.close();
//        }
//        return false;
//    }

}