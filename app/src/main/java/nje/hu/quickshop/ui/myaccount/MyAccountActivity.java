package nje.hu.quickshop.ui.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nje.hu.quickshop.R;
import nje.hu.quickshop.ui.login.LoginActivity;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        TextView nameTextView = findViewById(R.id.profile_nameTextView);
        TextView emailTextView = findViewById(R.id.profile_emailTextView);

        String name = getIntent().getStringExtra("user_name");
        String email = getIntent().getStringExtra("user_email");

        nameTextView.setText(name != null ? name : "Name not found");
        emailTextView.setText(email != null ? email : "Email not found");


        Button logoutButton = findViewById(R.id.profile_logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Giriş ekranına yönlendir
            Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Geri tuşunu engellemek için
            startActivity(intent);
            finish(); // Bu activity'yi kapat

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        });


        Button updateButton = findViewById(R.id.profile_updateButton);
        updateButton.setOnClickListener(v -> showUpdateDialog());

        Button deleteButton = findViewById(R.id.profile_deleteButton);
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());


    }


    // Shows a pop-up dialog for updating user information
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bilgileri Güncelle");

        // Custom layout için EditText’leri barındıran bir LinearLayout tanımla
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Yeni Ad Soyad");
        layout.addView(nameInput);

        final EditText emailInput = new EditText(this);
        emailInput.setHint("Yeni Email");
        layout.addView(emailInput);

        builder.setView(layout);

        builder.setPositiveButton("Güncelle", (dialog, which) -> {
            String newName = nameInput.getText().toString().trim();
            String newEmail = emailInput.getText().toString().trim();

            if (!newName.isEmpty() && !newEmail.isEmpty()) {
                DatabaseReference userRef = FirebaseDatabase.getInstance()
                        .getReference("users").child(newName);

                // Eski kullanıcıyı silme/güncelleme işlemi basit düzeyde:
                userRef.child("name").setValue(newName);
                userRef.child("email").setValue(newEmail);

                // Ekrandaki TextView’leri güncelle
                TextView nameTextView = findViewById(R.id.profile_nameTextView);
                TextView emailTextView = findViewById(R.id.profile_emailTextView);
                nameTextView.setText(newName);
                emailTextView.setText(newEmail);

                Toast.makeText(this, "Bilgiler güncellendi!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Alanlar boş olamaz!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("İptal", null);
        builder.show();
    }

    // Shows a pop-up dialog for deleting the account
    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Hesabı Sil")
                .setMessage("Bu hesabı kalıcı olarak silmek istediğinizden emin misiniz?")
                .setPositiveButton("Evet", (dialog, which) -> deleteAccount())
                .setNegativeButton("İptal", null)
                .show();
    }

    private void deleteAccount() {
        String userName = getIntent().getStringExtra("user_name");

        if (userName != null && !userName.isEmpty()) {
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("users").child(userName);

            userRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Hesap silindi!", Toast.LENGTH_SHORT).show();

                        // Login sayfasına yönlendir
                        Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hesap silinemedi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Kullanıcı adı alınamadı!", Toast.LENGTH_SHORT).show();
        }
    }



}
