package nje.hu.quickshop.ui.login;


import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import nje.hu.quickshop.R;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

        import nje.hu.quickshop.databinding.ActivityLoginBinding;
import nje.hu.quickshop.ui.home.HomeFragment;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Status bar color. (couldn't do it in themes.xml so i just code it)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.my_status_bar_color));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        binding.loginButton.setOnClickListener(v -> {
            String enteredEmail = binding.loginEmailText.getText().toString().trim();
            String enteredPassword = binding.loginPasswordText.getText().toString().trim();

            // Tüm kullanıcıları al ve e-posta & şifre karşılaştır
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean matched = false;

                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String email = userSnapshot.child("email").getValue(String.class);
                        String password = userSnapshot.child("password").getValue(String.class);

                        if (enteredEmail.equals(email) && enteredPassword.equals(password)) {
                            matched = true;
                            break;
                        }
                    }

                    if (matched) {
                        Toast.makeText(LoginActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeFragment.class));
                        finish(); // login ekranını kapat
                    } else {
                        Toast.makeText(LoginActivity.this, "Hatalı e-posta veya şifre!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LoginActivity.this, "Veri alınamadı: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

