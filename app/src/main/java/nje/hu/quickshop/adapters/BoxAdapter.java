package nje.hu.quickshop.adapters;
//To list the basket items using item_cart.xml.

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import nje.hu.quickshop.R;
import nje.hu.quickshop.entities.CardItem;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder>  {
    private List<CardItem> cartItems;

    public BoxAdapter(List<CardItem> cartItems) {
        this.cartItems = cartItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, priceText, quantityText;
        Button increaseButton, decreaseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_product_name);
            priceText = itemView.findViewById(R.id.text_product_price);
            quantityText = itemView.findViewById(R.id.text_product_quantity);
            increaseButton = itemView.findViewById(R.id.button_increase);
            decreaseButton = itemView.findViewById(R.id.button_decrease);
        }

        public void bind(CardItem item) {
            nameText.setText(item.getProduct().getName());
            String priceTextFormatted = itemView.getContext().getString(
                    R.string.price_format, item.getProduct().getPrice());
            priceText.setText(priceTextFormatted);

            String quantityTextFormatted = itemView.getContext().getString(
                    R.string.quantity_format, item.getQuantity());
            quantityText.setText(quantityTextFormatted);
        }
    }

    @NonNull
    @Override
    public BoxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoxAdapter.ViewHolder holder, int position) {
        CardItem currentItem = cartItems.get(position);
        holder.bind(currentItem);

        holder.increaseButton.setOnClickListener(v -> {
            int currentQty = currentItem.getQuantity();
            currentItem.setQuantity(currentQty + 1);

            //Update the quantity in firebase
            String productId = String.valueOf(currentItem.getProduct().getId());
            FirebaseDatabase.getInstance().getReference("cartItems")
                    .child(productId)
                    .child("quantity")
                    .setValue(currentQty + 1);

            notifyItemChanged(position);
        });

        holder.decreaseButton.setOnClickListener(v -> {
            int currentQty = currentItem.getQuantity();
            if (currentQty > 1) {
                currentItem.setQuantity(currentQty - 1);

                //Update the quantity in firebase
                String productId = String.valueOf(currentItem.getProduct().getId());
                FirebaseDatabase.getInstance().getReference("cartItems")
                        .child(productId)
                        .child("quantity")
                        .setValue(currentQty - 1);

                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

}
