package ru.nsu.template.presentation.breed.subbreed;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.util.Objects;

import ru.nsu.template.R;
import ru.nsu.template.data.model.Breed;

public class SubBreedActivity extends AppCompatActivity{
    public static String SUB_BREED_KEY = "SUB_BREED_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_breed);
        TextView tvHeader = findViewById(R.id.tvHeader);
        ImageView ivAvatar = findViewById(R.id.ivAvatar);

        Breed breed = (Breed) getIntent().getSerializableExtra(SUB_BREED_KEY);

        Glide.with(ivAvatar.getContext())
                .load(Uri.parse(Objects.requireNonNull(breed).getPicture().getUrl()))
                .into(ivAvatar);


        tvHeader.setText(breed.getName());


    }
}
