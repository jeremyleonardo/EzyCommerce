package com.jeremyleonardo.ezycommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    Context ctx;
    List<Book> listBooks;

    AdapterCallback callback;

    public interface AdapterCallback{
        void onQuantityModified(Integer bookId, Integer qty);
    }

    public CartItemAdapter(Context ctx, AdapterCallback callback) {
        this.ctx = ctx;
        this.listBooks = new ArrayList<>();
        this.callback = callback;

        BooksDatabase booksDatabase = new BooksDatabase(ctx);
        this.listBooks = booksDatabase.getBooksInCart();
        notifyDataSetChanged();
    }

//    public void setListBooks(List<Book> listBooks) {
//        this.listBooks = listBooks;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_book_in_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = listBooks.get(position);

        Glide.with(ctx)
                .load(book.getImg())
                .into(holder.ivCartItemThumbnail);

        holder.tvCartItemName.setText(book.getName());
        holder.tvCartItemType.setText(book.getType());
        holder.tvCartItemPrice.setText("$" + book.getPrice());
        holder.tvCartItemQty.setText(book.getQty() + " pcs");
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
//            implements View.OnClickListener
    {
        ImageView ivCartItemThumbnail;
        TextView tvCartItemName;
        TextView tvCartItemType;
        TextView tvCartItemPrice;
        TextView tvCartItemQty;
        Button btnMinus;
        Button btnPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCartItemThumbnail = itemView.findViewById(R.id.ivCartItemThumbnail);
            tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            tvCartItemType = itemView.findViewById(R.id.tvCartItemType);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartItemPrice);
            tvCartItemQty = itemView.findViewById(R.id.tvCartItemQty);

            if(itemView.findViewById(R.id.btnMinus) != null){
                btnMinus = itemView.findViewById(R.id.btnMinus);
                btnMinus.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Book book = listBooks.get(position);
                        if(callback != null) {
                            callback.onQuantityModified(book.getId(), book.getQty()-1);
                            book.setQty(book.getQty()-1);
                            if(book.getQty() == 0){
                                listBooks.remove(book);
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
            }
            if(itemView.findViewById(R.id.btnPlus) != null){
                btnPlus = itemView.findViewById(R.id.btnPlus);
                btnPlus.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Book book = listBooks.get(position);
                        if(callback != null) {
                            callback.onQuantityModified(book.getId(), book.getQty()+1);
                            book.setQty(book.getQty()+1);
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    }
}