package com.jeremyleonardo.ezycommerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationFragment extends Fragment {

    NavigationListener listener;
    BottomNavigationView navigation;

    public NavigationFragment() {
        // Required empty public constructor
    }

    public interface NavigationListener {
        void onNavigationClick(int index);
    }

    public static NavigationFragment newInstance() {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof NavigationListener) {
            listener = (NavigationListener) getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigation = (BottomNavigationView) getView().findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(getActivity().findViewById(R.id.fragment_book_list) != null)
            navigation.setSelectedItemId(R.id.navigation_catalog);
        else
            navigation.setSelectedItemId(R.id.navigation_cart);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.v("TESTDEBUG", "menuklik");
            switch (item.getItemId()) {
                case R.id.navigation_catalog:
                    if(listener != null) {
                        listener.onNavigationClick(0);
                        navigation.setSelectedItemId(R.id.navigation_cart);
                    }
                    return true;
                case R.id.navigation_cart:
                    Log.v("TESTDEBUG", "cartclick");
                    if(listener != null) {
                        listener.onNavigationClick(1);
                        navigation.setSelectedItemId(R.id.navigation_catalog);
                    }
                    return true;
            }
            return false;
        }

    };
}