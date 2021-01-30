package com.jeremyleonardo.ezycommerce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AwsService {

    @GET("book")
    Call<BooksResponse> getBooks(
            @Query(value="nim") String nim,
            @Query(value="nama") String nama
    );

    @GET("book/{id}")
    Call<BookDetailResponse> getBookDetail(
            @Path(value = "id", encoded = true) int id,
            @Query(value = "nim") String nim,
            @Query(value = "nama") String nama
    );
}
