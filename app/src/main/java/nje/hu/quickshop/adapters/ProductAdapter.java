package nje.hu.quickshop.adapters;
//To list products using item_product.xml.

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import nje.hu.quickshop.R;
import nje.hu.quickshop.entities.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onAddToCart(Product product);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, priceText;
        ImageView imageView;
        Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_product_name);
            priceText = itemView.findViewById(R.id.text_product_price);
            imageView = itemView.findViewById(R.id.image_product);
            addToCartButton = itemView.findViewById(R.id.button_add_to_cart);
        }

        public void bind(Product product, OnProductClickListener listener) {
            nameText.setText(product.getName());
            priceText.setText(String.format("$%.2f", product.getPrice()));

            // Load image (if imageUrl is used)
            // Glide.with(itemView.getContext())
              //      .load(product.getImageUrl())
                //    .placeholder(R.drawable.ic_launcher_foreground)
                  //  .into(imageView);

            addToCartButton.setOnClickListener(v -> listener.onAddToCart(product));
        }
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
        holder.bind(productList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
