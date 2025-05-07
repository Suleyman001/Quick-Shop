package nje.hu.quickshop.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import nje.hu.quickshop.R;
import nje.hu.quickshop.adapters.ProductAdapter;
import nje.hu.quickshop.entities.Product;
import nje.hu.quickshop.managers.CartManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the product list
        productList = new ArrayList<>();
        // Add some sample products
        productList.add(new Product(1, "Product 1", 10.00, "Description of Product 1"));
        productList.add(new Product(2, "Product 2", 15.00, "Description of Product 2"));
        productList.add(new Product(3, "Product 3", 20.00, "Description of Product 3"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        // Initialize the ProductAdapter and set it to the RecyclerView
        productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);

        return rootView;
    }

    @Override
    public void onAddToCart(Product product) {
        CartManager.getInstance().addToCart(product);  // Adds or increments quantity
        Toast.makeText(getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }

}
