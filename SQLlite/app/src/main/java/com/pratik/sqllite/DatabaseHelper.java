package com.pratik.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static java.sql.Types.INTEGER;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "Student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";

    public DatabaseHelper(Context context) {
        super (context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL ("create table " + TABLE_NAME + "("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT,"+COL_3+" TEXT,"+COL_4+" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL ("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate (db);
    }

    public boolean insertData(String name,String surename,String marks){
        SQLiteDatabase db = this.getReadableDatabase ();
        ContentValues contentValues = new ContentValues ();
        contentValues.put (COL_2,name);
        contentValues.put (COL_3,surename);
        contentValues.put (COL_4,marks);
        long result = db.insert (TABLE_NAME,null,contentValues);
        if(result== -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDate()
    {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor res = db.rawQuery ("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String surename,String marks)
    {
        SQLiteDatabase db = this.getReadableDatabase ();
        ContentValues contentValues = new ContentValues ();
        contentValues.put (COL_1,id);
        contentValues.put (COL_2,name);
        contentValues.put (COL_3,surename);
        contentValues.put (COL_4,marks);
        db.update (TABLE_NAME,contentValues,"ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.delete (TABLE_NAME,"ID = ?",new String[] {id});
    }
}
