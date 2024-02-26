package com.example.newtasktest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newtasktest.model.Model;

import java.util.ArrayList;

public class DBhandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "database_name";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user_details";
    private static final String ID = "id";
    private static final String USER_NAME = "username";
    private static final String DESIGNATION = "designation";
    private static final String HOBBY = "hobby";
    private static final String GENDER = "gender";
    private static final String DOB = "dob";

    public DBhandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE " + TABLE_NAME +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "designation TEXT, " +
                "hobby TEXT, " +
                "gender TEXT, " +
                "dob TEXT)";

        db.execSQL(query);

    }

    public void addNewUser(String username, String designation, String hobby, String gender, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_NAME, username);
        values.put(DESIGNATION, designation);
        values.put(HOBBY, hobby);
        values.put(GENDER, gender);
        values.put(DOB, dob);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        } else
            return false;
    }

    public void deleteUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "username=?", new String[]{name});
        db.close();
    }

    public ArrayList<Model> readUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Model> model = new ArrayList<>();

        Cursor cursor;
        if (name.isEmpty()) {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            cursor.moveToFirst();

            do {
                addModelData(model, cursor);
            } while (cursor.moveToNext());

        } else {
            cursor = db.rawQuery("SELECT * FROM user_details WHERE username=?", new String[]{name});
            cursor.moveToFirst();
            addModelData(model, cursor);
        }
        cursor.close();
        return model;
    }

    public ArrayList<Model> searchUser(String name){
        SQLiteDatabase db= this.getReadableDatabase();
        ArrayList<Model> model = new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT * FROM user_details WHERE username =? ",new String[]{name});
       if(cursor!=null && cursor.moveToFirst()) {
           do {
               addModelData(model, cursor);
           } while (cursor.moveToNext());
           cursor.close();
       }
        return model;
    }



    public void updateUser(String original, String name, String designation, String hobby, String gender, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_NAME, name);
        values.put(DESIGNATION, designation);
        values.put(HOBBY, hobby);
        values.put(GENDER, gender);
        values.put(DOB, dob);
        db.update(TABLE_NAME, values, "username=?", new String[]{original});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addModelData(ArrayList<Model> model, Cursor cursor) {
        model.add(new Model(cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)));
    }
}
