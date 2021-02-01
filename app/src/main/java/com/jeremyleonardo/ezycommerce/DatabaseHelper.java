package com.jeremyleonardo.ezycommerce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "cart_db";
    public static final int DB_VERSION = 1;

    /* ================================ BOOKS ================================= */
    public static final String TABLE_NAME_BOOKS = "books";
    public static final String FIELD_BOOK_ID = "id";
    public static final String FIELD_BOOK_NAME = "name";
    public static final String FIELD_BOOK_DESCRIPTION = "description";
    public static final String FIELD_BOOK_PRICE = "price";
    public static final String FIELD_BOOK_AUTHOR = "author";
    public static final String FIELD_BOOK_TYPE = "type";
    public static final String FIELD_BOOK_IMG = "img";
    public static final String FIELD_BOOK_IN_CART = "in_cart";
    public static final String FIELD_BOOK_CATEGORY = "category";
    public static final String FIELD_BOOK_QTY = "quantity";

    private static final String createBooks =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BOOKS + " (" +
                    FIELD_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FIELD_BOOK_NAME + " TEXT," +
                    FIELD_BOOK_DESCRIPTION + " TEXT," +
                    FIELD_BOOK_PRICE + " REAL," +
                    FIELD_BOOK_AUTHOR + " TEXT," +
                    FIELD_BOOK_TYPE + " TEXT," +
                    FIELD_BOOK_IMG + " TEXT," +
                    FIELD_BOOK_IN_CART + " INTEGER," +
                    FIELD_BOOK_CATEGORY + " TEXT," +
                    FIELD_BOOK_QTY + " INTEGER )";

    private static final String dropBooks =
            "DROP TABLE IF EXISTS " + TABLE_NAME_BOOKS;
    /* ========================================================================= */

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createBooks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropBooks);
        onCreate(db);
    }
}
