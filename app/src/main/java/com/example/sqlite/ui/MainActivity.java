package com.example.sqlite.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.sqlite.R;
import com.example.sqlite.databinding.ActivityMainBinding;
import com.example.sqlite.helper.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ContactMainViewModel contactMainViewModel = obtainViewModel(MainActivity.this);
        contactMainViewModel.getAllContacts().observe(this, contacts -> {
            if (contacts != null) {
                adapter.setListContacts(contacts);
            }
        });

        adapter = new ContactAdapter();
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);
        binding.fabAdd.setOnClickListener(view -> {
            if (view.getId() == R.id.fab_add) {
                Intent intent = new Intent(MainActivity.this, InsertUpdateActivity.class);
            startActivity(intent);
        }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static ContactMainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactMainViewModel.class);
    }
}