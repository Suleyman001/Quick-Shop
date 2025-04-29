package nje.hu.quickshop.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import nje.hu.quickshop.R;
import nje.hu.quickshop.databinding.ActivityProductDetailBinding;
import nje.hu.quickshop.entities.Product;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Gelen ürün ID'sini al
        int productId = getIntent().getIntExtra("product_id", -1);

        // Ürün detaylarını API'den çek (şimdilik örnek veri kullanalım)
        Product product = new Product(productId, "Örnek Ürün", 599.99, "Bu bir örnek üründür.");

        // Ürün detaylarını ekranda göster
        binding.tvProductName.setText(product.getName());
        binding.tvProductPrice.setText(String.format("%.2f TL", product.getPrice()));
        binding.tvProductDescription.setText(product.getDescription());

        // Sepete Ekle butonuna tıklama olayı
        binding.btnAddToCart.setOnClickListener(v -> {
            // Sepete ekleme mantığını buraya yazacağız (şimdilik boş bırakalım)
        });
    }
}