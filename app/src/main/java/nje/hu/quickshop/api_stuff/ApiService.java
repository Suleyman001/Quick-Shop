package nje.hu.quickshop.api_stuff;

import nje.hu.quickshop.entities.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("products")
    Call<List<Product>> getProducts();
}