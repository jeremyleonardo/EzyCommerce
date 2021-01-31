package com.jeremyleonardo.ezycommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements BookListFragment.FragmentListener, NavigationFragment.NavigationListener {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onItemClick(Integer id) {

        if(findViewById(R.id.fragment_book_detail_container) != null){
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setId(id);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_book_detail_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {

        }
    }

    @Override
    public void onNavigationClick(int index) {
        Log.v("TESTDEBUG", "test");
        Intent intent = new Intent(this, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}