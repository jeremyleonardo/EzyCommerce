package com.jeremyleonardo.ezycommerce;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookListFragment extends Fragment implements BooksAdapter.AdapterCallback {

    List<Book> books;

    RecyclerView rvBooks;
    BooksAdapter booksAdapter;

    FragmentListener listener;

    public interface FragmentListener {
        void onItemClick(Integer id);
    }

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getParentFragment() instanceof  FragmentListener) {
            listener = (FragmentListener) getParentFragment();
        }
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

        booksAdapter = new BooksAdapter(getView().getContext(), this);
        rvBooks.setAdapter(booksAdapter);

        Retrofit retrofit = ApiClient.getRetrofit(getString(R.string.api_base_url));
        AwsService service = retrofit.create(AwsService.class);
        Call<BooksResponse> call = service.getBooks(getString(R.string.nim), getString(R.string.nama));
        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                books = response.body().getProducts();
                booksAdapter.setListBooks(books);

                renderAllCategoryButtons();
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public void onItemClicked(Integer id) {
        Log.v("TESTDEBUG", "onItemClicked");
        if(listener != null) {
            listener.onItemClick(id);
        }
    }

//    Dynamically renders category buttons based on books retrieved from API
    public void renderAllCategoryButtons(){
        List<String> bookCategories = new ArrayList<>();
        renderCategoryButton("all");
        for (Book book: books) {
            boolean categoryAlreadyExist = false;
            for(String cat: bookCategories){
                if(cat.equals(book.getCategory())){
                    categoryAlreadyExist = true;
                    break;
                }
            }
            if(!categoryAlreadyExist){
                bookCategories.add(book.getCategory());
                renderCategoryButton(book.getCategory());
            }
        }
    }

    public void renderCategoryButton(String category){
        Button btnCategory = new Button(getParentFragment().getActivity());
        btnCategory.setText(category);

        btnCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (category == "all"){
                    booksAdapter.setListBooks(books);
                } else {
                    List<Book> filteredBooks = new ArrayList<>();
                    for (int i = 0; i < books.size(); i++) {
                        if(books.get(i).getCategory().equals(category)){
                            filteredBooks.add(books.get(i));
                        }
                    }
                    booksAdapter.setListBooks((List<Book>) filteredBooks);
                }
            }
        });

        LinearLayout ll = (LinearLayout)getActivity().findViewById(R.id.llCategories);
        btnCategory.setTextSize(11);
        ll.addView(btnCategory);
    }

}