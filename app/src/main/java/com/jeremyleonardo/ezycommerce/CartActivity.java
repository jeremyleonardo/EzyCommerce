package com.jeremyleonardo.ezycommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.AdapterCallback {

    CartItemAdapter cartItemAdapter;
    RecyclerView rvCartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rvCartItem = findViewById(R.id.rvCartItem);
        rvCartItem.setNestedScrollingEnabled(false);
        rvCartItem.setLayoutManager(new LinearLayoutManager(this));
        cartItemAdapter = new CartItemAdapter(this, this);
        rvCartItem.setAdapter(cartItemAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_catalog:
                    finish();
                    return true;
                case R.id.navigation_cart:
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onQuantityModified(Integer bookId, Integer qty) {
        BooksDatabase booksDatabase = new BooksDatabase(this);
        booksDatabase.changeQuantity(bookId, qty);
    }

    public void onCancelClicked(View view){
        finish();
    }

    public void onConfirmClicked(View view){
        BooksDatabase booksDatabase = new BooksDatabase(this);
        List<Book> booksInCart = booksDatabase.getBooksInCart();
        if (booksInCart.size() > 0){
            for (Book book: booksInCart) {
                booksDatabase.changeQuantity(book.getId(), 0);
            }
            Toast toast = Toast.makeText(this, "Purchase processed, Thank You!", Toast.LENGTH_LONG);
            toast.show();
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Cart is empty, please add something to cart first", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}