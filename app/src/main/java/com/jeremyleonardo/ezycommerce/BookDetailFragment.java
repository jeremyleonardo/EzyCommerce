package com.jeremyleonardo.ezycommerce;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookDetailFragment extends Fragment {

    private Integer id;

    private TextView tvDetailName;
    private TextView tvDetailDescription;
    private TextView tvDetailPrice;
    private TextView tvDetailRating;

    public BookDetailFragment() {
        // Required empty public constructor
    }

    public static BookDetailFragment newInstance(String param1, String param2) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setId(Integer id) {
        this.id = id;
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

        if(savedInstanceState != null && savedInstanceState.containsKey("bookId")) {
            id = savedInstanceState.getInt("bookId");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View rootView = getView();
        tvDetailName = rootView.findViewById(R.id.tvDetailName);
        tvDetailDescription = rootView.findViewById(R.id.tvDetailDescription);

        Retrofit retrofit = ApiClient.getRetrofit(getString(R.string.api_base_url));
        AwsService service = retrofit.create(AwsService.class);
        Call<BookDetailResponse> call = service.getBookDetail(id, getString(R.string.nim), getString(R.string.nama));

        call.enqueue(new Callback<BookDetailResponse>() {
            @Override
            public void onResponse(Call<BookDetailResponse> call, Response<BookDetailResponse> response) {
                BookDetailResponse bookDetailResponse = response.body();
                Book book = bookDetailResponse.getProducts().get(0);
                tvDetailName.setText(book.getName());
                tvDetailDescription.setText(book.getDescription());
                tvDetailPrice.setText("$" + book.getPrice());
                tvDetailRating.setText(book.getStars());
            }

            @Override
            public void onFailure(Call<BookDetailResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("bookId", id);
    }

}