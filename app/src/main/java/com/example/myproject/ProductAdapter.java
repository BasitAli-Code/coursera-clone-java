package com.example.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductModel> productList;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(ProductModel product);
    }

    public ProductAdapter(List<ProductModel> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);

        holder.title.setText(product.getTitle());
        holder.description.setText(product.getDescription());

        if (product.getImageResId() != 0) {
            holder.image.setImageResource(product.getImageResId());
        } else {
            holder.image.setImageResource(R.drawable.ai); // fallback
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            description = itemView.findViewById(R.id.textDesc);
            image = itemView.findViewById(R.id.courseImage); // new ImageView
        }
    }
}
