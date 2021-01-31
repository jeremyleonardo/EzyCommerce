package com.jeremyleonardo.ezycommerce;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CatalogFragment extends Fragment implements BookListFragment.FragmentListener {

    FragmentManager fragmentManager;

    public CatalogFragment() {
        // Required empty public constructor
    }

    public static CatalogFragment newInstance() {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        fragmentManager = getChildFragmentManager();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }


    @Override
    public void onItemClick(Integer id) {

        Log.v("TESTDEBUG", "OnItemClick at CatalogFragment");

        if(getActivity().findViewById(R.id.fragment_book_detail_container) != null){
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
}