package com.bonc.activity.SQL.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bonc.activity.Others.Cards;
import com.bonc.activity.SQL.DatabaseHelper;

import java.util.ArrayList;

public class SQLiteDatabase extends SQLiteOpenHelper {
    private Cards cards;
    public SQLiteDatabase(Context context) {
        super(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION );
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String createCardsTable = "CREATE TABLE  IF NOT EXISTS " + DatabaseHelper.TABLE_NAME + "("+DatabaseHelper.COL_ID+ " INTEGER NOT NULL PRIMARY KEY ," + DatabaseHelper.COL_WORD +" TEXT, " + DatabaseHelper.COL_POINT+" INTEGER)";
        db.execSQL(createCardsTable);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Cards> getCards(){
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int i = 0;
        ArrayList<Cards> cards = new ArrayList<>();
        Cards card;
        while (cursor.moveToNext()){
            card = new Cards(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_WORD)),cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_POINT)),cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
            cards.add(i,card);
            i++;
        }
        card = null;
        db.close();
        db = null;
        query = null;
        i = 0;
        return cards;
    }
}
