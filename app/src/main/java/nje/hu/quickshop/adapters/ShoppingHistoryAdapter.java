package nje.hu.quickshop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nje.hu.quickshop.R;

import nje.hu.quickshop.ui.ShoppingItem;

/**
 * Adapter for displaying the user's shopping history in a RecyclerView.
 * Each item shows the name of the purchased product and the purchase date.
 */
public class ShoppingHistoryAdapter extends RecyclerView.Adapter<ShoppingHistoryAdapter.ViewHolder> {

    private List<ShoppingItem> shoppingList;

    public ShoppingHistoryAdapter(List<ShoppingItem> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle, itemDate;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDate = itemView.findViewById(R.id.item_date);
        }
    }

    @Override
    public ShoppingHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingItem item = shoppingList.get(position);
        holder.itemTitle.setText(item.itemName);
        holder.itemDate.setText(item.purchaseDate);
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }
}