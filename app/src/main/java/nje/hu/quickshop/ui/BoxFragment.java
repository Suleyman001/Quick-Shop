package nje.hu.quickshop.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nje.hu.quickshop.R;
import nje.hu.quickshop.adapters.BoxAdapter;
import nje.hu.quickshop.entities.CardItem;
import nje.hu.quickshop.managers.CartManager;

import nje.hu.quickshop.entities.Product;
public class BoxFragment extends Fragment {
    private RecyclerView recyclerView;
    private BoxAdapter boxAdapter;
    private List<CardItem> cartItems;

    public BoxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_box, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_cart_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Just for testing  data - replace with actual cart retrieval logic
        cartItems = new ArrayList<>();
        Product sampleProduct = new Product(1, "Sample Product", 9.99, "Description");
        cartItems.add(new CardItem(sampleProduct, 2));
        cartItems.add(new CardItem(sampleProduct, 1));

        boxAdapter = new BoxAdapter(cartItems);
        recyclerView.setAdapter(boxAdapter);
    }





}
