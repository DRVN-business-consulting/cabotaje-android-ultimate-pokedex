package dev.jay.ultimatepokedex;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.jay.ultimatepokedex.adapters.TrainerAdapter;

public class AvatarPickerActivity extends AppCompatActivity {

    RecyclerView rvAvatars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avatar_picker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select an avatar");

        rvAvatars = findViewById(R.id.rvAvatars);
        rvAvatars.setLayoutManager(new GridLayoutManager(this, 2));

        String username = getIntent().getStringExtra("username");
        List<String> images = List.of(
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/311fa9e9-debc-4ca1-8c27-40576f23c4b7/d8pseir-fa97f454-f514-4faa-aec5-f9900e171eb8.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzMxMWZhOWU5LWRlYmMtNGNhMS04YzI3LTQwNTc2ZjIzYzRiN1wvZDhwc2Vpci1mYTk3ZjQ1NC1mNTE0LTRmYWEtYWVjNS1mOTkwMGUxNzFlYjgucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.dcQ7YbPEEmSNQ9uY6Xes31godJ7lemShxS0jYH1SVWw",
            "https://pics.craiyon.com/2023-12-22/F1WVwf78RhWe-_C_PqiOIQ.webp",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYtneh9zcefrBRvEe6O0r4hN3627ScFn_l8w&s",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOX4cq09AjzQ25LJh-g2TdyAGHnn-cgvXBmA&s"
        );
        rvAvatars.setAdapter(new TrainerAdapter(username, images));

    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}