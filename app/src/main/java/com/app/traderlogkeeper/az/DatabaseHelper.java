package com.app.traderlogkeeper.az;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    //Initialize all the fields needed for database
    public static final String DATABASE_NAME = "Friends.db";
    public static final String TABLE_NAME = "friends_data";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "First_Nmae";
    public static final String COL_3 = "Last_Name";
    public static final String COL_4 = "Date";
    public static final String COL_5 = "Date";
    public static final String LBR = "(";
    public static final String RBR = ")";
    public static final String COM = ",";

    //Just pass context of the app to make it simpler
    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, 2 );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating table

        db.execSQL( "create table " + TABLE_NAME + LBR + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                COL_2 + " TEXT" + COM + COL_3 + " TEXT" + COM + COL_4 + " INTEGER" +RBR );

        // Another way of writing the CREATE TABLE query
       /* db.execSQL( "create table student_data (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Surname TEXT," +
                "Marks INTEGER, Date TEXT)" );*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Dropping old table
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate( db );

    }

    //Insert data in database
    public boolean instertData(String name, String surname, String date){

        //Get the instance of SQL Database which we have created
        SQLiteDatabase db = getWritableDatabase();

        //To pass all the values in database
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, name );
        contentValues.put( COL_3, surname );
        contentValues.put( COL_4, date );

        long result = db.insert( TABLE_NAME, null, contentValues );

        if(result == -1)
            return false;
        else
            return true;
    }

    //Cursor class is used to move around in the database
    public Cursor getData(){

        //Get the data from database
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery( "select * from " + TABLE_NAME, null );
        return res;
    }

    //Update fields of database using ID (Unique identifier)
    public boolean updateData(String first_name, String last_name, String date){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues(  );
        // When you want to update only name field



        if(!first_name.isEmpty() && !last_name.isEmpty() && !date.isEmpty()){
            contentValues.put( COL_2, first_name );
            contentValues.put( COL_3, last_name );
            contentValues.put( COL_4, date );
        }

        // UPDATE query
        db.update( TABLE_NAME, contentValues, "Date = ?", new String[]{date} );
        return true;
    }

    //Delete data from the databse using ID (Primary Key)
    public Integer deleteData(String id){

        SQLiteDatabase db = getWritableDatabase();
        return db.delete( TABLE_NAME, "ID = ?", new String [] {id} );
    }

    public boolean checkDate(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COL_4 + " = ?";
        Cursor c = db.rawQuery(queryString, new String[]{user});
        boolean result = c.getCount() > 0;
        c.close();
        db.close();
        return result;
    }
}
