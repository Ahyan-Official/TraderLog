package com.app.traderlogkeeper.az;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TradeDatabaseHelper extends SQLiteOpenHelper {

    //Initialize all the fields needed for database
    public static final String DATABASE_NAME = "Trade.db";
    public static final String TABLE_NAME = "trade";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Total_Money";
    public static final String COL_3 = "Total_Hours";
    public static final String COL_5 = "Win";
    public static final String COL_6 = "Loss";

    public static final String COL_4 = "Date";
    public static final String LBR = "(";
    public static final String RBR = ")";
    public static final String COM = ",";

    //Just pass context of the app to make it simpler
    public TradeDatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, 2 );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating table

        db.execSQL( "create table " + TABLE_NAME + LBR + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                COL_2 + " REAL" + COM + COL_3 + " REAL"+ COM + COL_5 + " REAL"+ COM + COL_6 + " REAL" + COM + COL_4 + " INTEGER" +RBR );

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
    public boolean instertData(Double name, Double surname, String date,int win,int loss){

        //Get the instance of SQL Database which we have created
        SQLiteDatabase db = getWritableDatabase();

        //To pass all the values in database
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, name );
        contentValues.put( COL_3, surname );
        contentValues.put( COL_4, date );

        contentValues.put( COL_5, win );
        contentValues.put( COL_6, loss );

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
    public boolean updateData(Double first_name, Double last_name, String date,int win,int loss){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues(  );
        // When you want to update only name field

        if(!date.isEmpty()){
            contentValues.put( COL_2, first_name );
            contentValues.put( COL_3, last_name );
            contentValues.put( COL_4, date );

            contentValues.put( COL_5, win );
            contentValues.put( COL_6, loss );
        }

        // UPDATE query
        db.update( TABLE_NAME, contentValues, "Date = ?", new String[]{date} );
        return true;
    }

    //Delete data from the database using ID (Primary Key)
    public Integer deleteData(String id){

        SQLiteDatabase db = getWritableDatabase();
        return db.delete( TABLE_NAME, "ID = ?", new String [] {id} );
    }

    public boolean checkDate(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_4 + " = ?";
        Cursor c = db.rawQuery(queryString, new String[]{user});
        Log.e("kkoko", "checkDate: "+ c.getColumnName(1).toString());
        boolean result = c.getCount() > 0;
        c.close();
        db.close();
        return result;
    }

    public String getTotalMoney(String activeuser){
        String rv = "0.00";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select Total_Money from trade where Date=?",new String[]{activeuser});
        if (cursor.moveToFirst()) {
            rv = cursor.getString(cursor.getColumnIndex("Total_Money"));
        }
        return rv;
    }

    public String geTotalHours(String activeuser){
        String rv = "0.00";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select Total_Hours from trade where Date=?",new String[]{activeuser});
        if (cursor.moveToFirst()) {
            rv = cursor.getString(cursor.getColumnIndex("Total_Hours"));
        }
        return rv;
    }

    public float CurrentMonthyHours(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Total_Hours) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))" +"AND  strftime('%m',Date) = "+"strftime('%m',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }

    public float CurrentMonthyMoney(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Total_Money) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))" +"AND  strftime('%m',Date) = "+"strftime('%m',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }

    public float CurrentYearlyMoney(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Total_Money) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }

    public float CurrentYearlyHours(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Total_Hours) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }
    public float CurrentMonthlyWin(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Win) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))" +"AND  strftime('%m',Date) = "+"strftime('%m',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }


    public float CurrentMonthlyLoss(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Loss) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))" +"AND  strftime('%m',Date) = "+"strftime('%m',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;
    }


    public float getWeeklyMoneyMade(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Total_Money) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))" +"AND  strftime('%W',Date) = "+"strftime('%W',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }

    public float CurrentWeeklyHours(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Total_Hours) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))" +"AND  strftime('%W',Date) = "+"strftime('%W',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);
        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }




    public float CurrentYearlyWins(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Win) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);

        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }
    public float CurrentYearlyLoss(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Loss) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);

        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }

    public float CurrentYearlyWinWithFilter(String thisDate){
        float x = 0;
        SQLiteDatabase db = getReadableDatabase();
        //        String getamountdata = "SELECT SUM(inc_amount) AS totalInc FROM "+ TABLE_ENTRY + " WHERE strftime('%Y',entry_date) = strftime('%Y',date('now')) AND  strftime('%m',entry_date) = strftime('%m',date('now'))"";
        String getamountdata = "SELECT SUM(Win) AS totalInc FROM  trade  WHERE strftime('%Y',Date) = "+"strftime('%Y',date("+thisDate+"))";
        Cursor c = db.rawQuery(getamountdata, null);

        if(c.moveToFirst()){
            x = c.getFloat(0);
        }
        return x;

    }
}
