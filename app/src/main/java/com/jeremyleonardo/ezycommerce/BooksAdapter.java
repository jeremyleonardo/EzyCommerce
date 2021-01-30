package com.jeremyleonardo.ezycommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    Context ctx;
    List<Book> listBooks;

    public BooksAdapter(Context ctx) {
        this.ctx = ctx;
        this.listBooks = new ArrayList<>();
    }

    public void setListBooks(List<Book> listBooks) {
        this.listBooks = listBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = listBooks.get(position);

        Glide.with(ctx)
                .load(book.getImg())
                .into(holder.ivThumbnail);

        holder.tvName.setText(book.getName());
        holder.tvRating.setText(book.getStars());
        holder.tvPrice.setText("$" + book.getPrice());
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivThumbnail;
        TextView tvName;
        TextView tvRating;
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Book book = listBooks.get(position);

            String info = "Author: " + book.getAuthor() + ", Price: " + book.getPrice();
            Toast.makeText(ctx, info, Toast.LENGTH_LONG).show();
        }
    }
}