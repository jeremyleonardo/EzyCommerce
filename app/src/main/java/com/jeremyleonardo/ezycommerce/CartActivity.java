package com.jeremyleonardo.ezycommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.BigDecimal;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.AdapterCallback {

    CartItemAdapter cartItemAdapter;

    RecyclerView rvCartItem;
    TextView tvSubtotal;
    TextView tvShipping;
    TextView tvTaxes;
    TextView tvTotal;

    private BigDecimal subtotal = BigDecimal.valueOf(0);
    private BigDecimal shipping = BigDecimal.valueOf(0);
    private BigDecimal taxes = BigDecimal.valueOf(0);
    private BigDecimal total = BigDecimal.valueOf(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle(getString(R.string.app_name) + " : Cart");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTaxes = findViewById(R.id.tvTaxes);
        tvTotal = findViewById(R.id.tvTotal);

        rvCartItem = findViewById(R.id.rvCartItem);
        rvCartItem.setNestedScrollingEnabled(false);
        rvCartItem.setLayoutManager(new LinearLayoutManager(this));
        cartItemAdapter = new CartItemAdapter(this, this);
        rvCartItem.setAdapter(cartItemAdapter);

        calculateCosts();
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
        this.calculateCosts();
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

    private void calculateCosts(){
        BooksDatabase booksDatabase = new BooksDatabase(this);
        List<Book> booksInCart = booksDatabase.getBooksInCart();
        this.subtotal = BigDecimal.valueOf(0);
        if(booksInCart.size() > 0){
            for (Book book: booksInCart) {
                BigDecimal qty = BigDecimal.valueOf(book.getQty());
                this.subtotal = this.subtotal.add(book.getPrice().multiply(qty));
            }
            this.taxes = this.subtotal.multiply(BigDecimal.valueOf(0.01));
            this.shipping = BigDecimal.valueOf(5);
            this.total = this.subtotal.add(this.taxes.add(this.shipping));
        } else {
            this.subtotal = BigDecimal.valueOf(0);
            this.taxes = BigDecimal.valueOf(0);
            this.shipping = BigDecimal.valueOf(0);
            this.total = BigDecimal.valueOf(0);
        }

        tvSubtotal.setText("$"+this.subtotal.toString());
        tvTotal.setText("$"+this.total.toString());
        tvTaxes.setText("$"+this.taxes.toString());
        tvShipping.setText("$"+this.shipping.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

}