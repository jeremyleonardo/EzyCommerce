package com.jeremyleonardo.ezycommerce;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookDetailFragment extends Fragment {

    private Integer id;
    private Book book;

    private TextView tvDetailName;
    private TextView tvDetailDescription;
    private TextView tvDetailPrice;
    private TextView tvDetailRating;
    private ImageView ivDetailThumbnail;
    private Button btnBuy;

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
        tvDetailPrice = rootView.findViewById(R.id.tvDetailPrice);
        tvDetailRating = rootView.findViewById(R.id.tvDetailRating);
        ivDetailThumbnail = rootView.findViewById(R.id.ivDetailThumbnail);
        btnBuy = rootView.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(this::addToCart);

        BooksDatabase booksDatabase = new BooksDatabase(getContext());
        book = booksDatabase.getBook(id);
        tvDetailName.setText(book.getName());
        tvDetailDescription.setText(book.getDescription());
        tvDetailPrice.setText("$" + book.getPrice());
        tvDetailRating.setText(book.getStars());
        Glide.with(getContext())
                .load(book.getImg())
                .into(ivDetailThumbnail);

//        Retrofit retrofit = ApiClient.getRetrofit(getString(R.string.api_base_url));
//        AwsService service = retrofit.create(AwsService.class);
//        Call<BookDetailResponse> call = service.getBookDetail(id, getString(R.string.nim), getString(R.string.nama));
//
//        call.enqueue(new Callback<BookDetailResponse>() {
//            @Override
//            public void onResponse(Call<BookDetailResponse> call, Response<BookDetailResponse> response) {
//                BookDetailResponse bookDetailResponse = response.body();
//                Book book = bookDetailResponse.getProducts().get(0);
//                tvDetailName.setText(book.getName());
//                tvDetailDescription.setText(book.getDescription());
//                tvDetailPrice.setText("$" + book.getPrice());
//                tvDetailRating.setText(book.getStars());
//                Glide.with(getContext())
//                        .load(book.getImg())
//                        .into(ivDetailThumbnail);
//            }
//
//            @Override
//            public void onFailure(Call<BookDetailResponse> call, Throwable t) {
//                call.cancel();
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(id != null)
            outState.putInt("bookId", id.intValue());
        super.onSaveInstanceState(outState);
    }

    public void addToCart(View view) {
        BooksDatabase booksDatabase = new BooksDatabase(getContext());
        if(book.getQty() == 0){
            Toast toast = Toast.makeText(getContext(), "Book added to cart", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getContext(), "Additional book quantity added to the cart", Toast.LENGTH_SHORT);
            toast.show();
        }
        book = booksDatabase.getBook(id);
        booksDatabase.changeQuantity(id, (book.getQty() + 1));

        Intent intent = new Intent(getContext(), CartActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

}