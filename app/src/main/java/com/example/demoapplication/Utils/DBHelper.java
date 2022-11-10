package com.example.demoapplication.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demoapplication.Models.SellModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DemoDataBase";
    private static final String TABLE_SELLER = "ItemToSell";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_TYPE = "type";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SELLER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_QUANTITY + " TEXT,"
                + KEY_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELLER);

        onCreate(db);
    }

    public void addSellerList(SellModel sellModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sellModel.getName()); // Contact Name
        values.put(KEY_PRICE, sellModel.getPrice()); // Contact Phone
        values.put(KEY_QUANTITY, sellModel.getQuantity()); // Contact Phone
        values.put(KEY_TYPE, sellModel.getType()); // Contact Phone

        db.insert(TABLE_SELLER, null, values);
        db.close();
    }

    public List<SellModel> getAllSellerList() {
        List<SellModel> contactList = new ArrayList<SellModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_SELLER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SellModel sellModel = new SellModel();
                sellModel.setId(Integer.parseInt(cursor.getString(0)));
                sellModel.setName(cursor.getString(1));
                sellModel.setPrice(cursor.getString(2));
                sellModel.setQuantity(cursor.getString(3));
                sellModel.setType(cursor.getString(4));
                // Adding contact to list
                contactList.add(sellModel);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

}