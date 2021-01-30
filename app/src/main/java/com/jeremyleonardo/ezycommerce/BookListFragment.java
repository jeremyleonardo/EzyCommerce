package com.jeremyleonardo.ezycommerce;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookListFragment extends Fragment {

    RecyclerView rvBooks;
    BooksAdapter booksAdapter;

    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
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
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvBooks = getView().findViewById(R.id.rvBooks);
        rvBooks.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        booksAdapter = new BooksAdapter(getView().getContext());
        rvBooks.setAdapter(booksAdapter);

        Retrofit retrofit = ApiClient.getRetrofit(getString(R.string.api_base_url));
        AwsService service = retrofit.create(AwsService.class);
        Call<BooksResponse> call = service.getBooks(getString(R.string.nim), getString(R.string.nama));

        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                List<Book> listBooks = response.body().getProducts();
                booksAdapter.setListBooks(listBooks);
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

}