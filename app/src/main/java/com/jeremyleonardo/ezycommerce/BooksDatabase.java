package com.jeremyleonardo.ezycommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BooksDatabase {

    private DatabaseHelper dbHelper;

    public BooksDatabase(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
    }

    public long insertBook(Book book, int qty) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.FIELD_BOOK_ID, book.getId());
        cv.put(DatabaseHelper.FIELD_BOOK_NAME, book.getName());
        cv.put(DatabaseHelper.FIELD_BOOK_DESCRIPTION, book.getDescription());
        cv.put(DatabaseHelper.FIELD_BOOK_PRICE, book.getPrice().doubleValue());
        cv.put(DatabaseHelper.FIELD_BOOK_AUTHOR, book.getAuthor());
        cv.put(DatabaseHelper.FIELD_BOOK_TYPE, book.getType());
        cv.put(DatabaseHelper.FIELD_BOOK_IMG, book.getImg());
        cv.put(DatabaseHelper.FIELD_BOOK_CATEGORY, book.getCategory());
        cv.put(DatabaseHelper.FIELD_BOOK_IN_CART, book.getInCart());
        cv.put(DatabaseHelper.FIELD_BOOK_QTY, qty);

        long id = db.insert(DatabaseHelper.TABLE_NAME_BOOKS, null, cv);

        db.close();

        return id;
    }

    public ArrayList<Book> getAllBooks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_BOOKS, null, null, null, null, null, null);

        ArrayList<Book> courses = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_NAME));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_DESCRIPTION));
                BigDecimal price = BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_PRICE)));
                String author = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_AUTHOR));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_TYPE));
                String img = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_IMG));
                String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_CATEGORY));
                Integer qty = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOK_QTY));

                courses.add(new Book(id, name, description, price, author, type, img, category, qty, false));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return courses;
    }

    public void changeQuantity(Integer id, int qty) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (qty > 0){
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.FIELD_BOOK_QTY, qty);

            String selection = "id=?";
            String[] selectionArgs = {"" + id};
            db.update(DatabaseHelper.TABLE_NAME_BOOKS, cv, selection, selectionArgs);
            db.close();
        } else {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.FIELD_BOOK_QTY, qty);
            cv.put(DatabaseHelper.FIELD_BOOK_IN_CART, false);

            String selection = "id=?";
            String[] selectionArgs = {"" + id};
            db.update(DatabaseHelper.TABLE_NAME_BOOKS, cv, selection, selectionArgs);
            db.close();
        }
    }
}