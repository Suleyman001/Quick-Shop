package nje.hu.quickshop.ui.myaccount;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nje.hu.quickshop.R;
import nje.hu.quickshop.adapters.ShoppingHistoryAdapter;
import nje.hu.quickshop.ui.ShoppingItem;
import nje.hu.quickshop.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MyAccountFragment extends Fragment {

    private TextView nameTextView, emailTextView;
    private String name, email;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Normally you'd pass data from Login via SharedPreferences or ViewModel
        name = "John Doe";  // Replace with real user session
        email = "john@example.com";

        nameTextView = view.findViewById(R.id.profile_nameTextView);
        emailTextView = view.findViewById(R.id.profile_emailTextView);

        nameTextView.setText(name != null ? name : "Name not found");
        emailTextView.setText(email != null ? email : "Email not found");

        Button logoutButton = view.findViewById(R.id.profile_logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        Button updateButton = view.findViewById(R.id.profile_updateButton);
        updateButton.setOnClickListener(v -> showUpdateDialog());

        Button deleteButton = view.findViewById(R.id.profile_deleteButton);
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.shopping_history_recyclerView);
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        ShoppingHistoryAdapter adapter = new ShoppingHistoryAdapter(shoppingItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        if (name != null && !name.isEmpty()) {
            DatabaseReference historyRef = FirebaseDatabase.getInstance()
                    .getReference("shopping_history")
                    .child(name);

            historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        ShoppingItem item = itemSnapshot.getValue(ShoppingItem.class);
                        if (item != null) {
                            shoppingItems.add(item);
                            adapter.notifyItemInserted(shoppingItems.size() - 1);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Failed to load history", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bilgileri Güncelle");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameInput = new EditText(getContext());
        nameInput.setHint("Yeni Ad Soyad");
        layout.addView(nameInput);

        final EditText emailInput = new EditText(getContext());
        emailInput.setHint("Yeni Email");
        layout.addView(emailInput);

        builder.setView(layout);

        builder.setPositiveButton("Güncelle", (dialog, which) -> {
            String newName = nameInput.getText().toString().trim();
            String newEmail = emailInput.getText().toString().trim();

            if (!newName.isEmpty() && !newEmail.isEmpty()) {
                DatabaseReference userRef = FirebaseDatabase.getInstance()
                        .getReference("users").child(newName);

                userRef.child("name").setValue(newName);
                userRef.child("email").setValue(newEmail);

                nameTextView.setText(newName);
                emailTextView.setText(newEmail);

                Toast.makeText(getContext(), "Bilgiler güncellendi!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Alanlar boş olamaz!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("İptal", null);
        builder.show();
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Hesabı Sil")
                .setMessage("Bu hesabı kalıcı olarak silmek istediğinizden emin misiniz?")
                .setPositiveButton("Evet", (dialog, which) -> deleteAccount())
                .setNegativeButton("İptal", null)
                .show();
    }

    private void deleteAccount() {
        if (name != null && !name.isEmpty()) {
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("users").child(name);

            userRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Hesap silindi!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Hesap silinemedi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Kullanıcı adı alınamadı!", Toast.LENGTH_SHORT).show();
        }
    }
}
