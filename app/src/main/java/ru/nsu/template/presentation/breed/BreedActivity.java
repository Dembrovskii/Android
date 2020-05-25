package ru.nsu.template.presentation.breed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import ru.nsu.template.R;
import ru.nsu.template.data.model.Breed;
import ru.nsu.template.presentation.breed.adapter.BreedsAdapter;
import ru.nsu.template.presentation.breed.listener.OnBreedClickListener;
import ru.nsu.template.presentation.breed.subbreed.SubBreedActivity;

import java.util.Objects;

import static ru.nsu.template.presentation.breed.subbreed.SubBreedActivity.SUB_BREED_KEY;

public class BreedActivity extends AppCompatActivity implements OnBreedClickListener {
    public static String BREED_KEY = "breed_key";

    private RecyclerView rvBreeds;
    private ImageView ivAvatar;
    private TextView tvSubBreedHeader;
    private BreedsAdapter adapter;
    private TextView tvHeader;
    private ProgressBar pbLoading;

    private Context context;

    private BreedViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);


        context = this;

        rvBreeds = findViewById(R.id.rvBreeds);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvHeader = findViewById(R.id.tvHeader);
        tvSubBreedHeader = findViewById(R.id.tvSubBreedHeader);
        adapter = new BreedsAdapter(this);
        pbLoading = findViewById(R.id.pbLoading);


        initList();

        Breed breed = (Breed)getIntent().getSerializableExtra(BREED_KEY);

        //viewModel = ViewModelProviders.of(this, new ReposViewModelFactory(breed.getName())).get(BreedsViewModel.class);

        BreedViewModel viewModel = ViewModelProviders.of(this, new BreedViewModelFactory(Objects.requireNonNull(breed).getName())).get(BreedViewModel.class);

        Glide.with(context)
                .load(Uri.parse(breed.getPicture().getUrl()))
                .into(ivAvatar);

        tvHeader.setText(breed.getName());

        viewModel.observeSubBreedLiveData().observe(this, breedList -> {
            if( breedList.isEmpty()) {
                tvSubBreedHeader.setVisibility(View.GONE);
            }else {
                tvSubBreedHeader.setVisibility(View.VISIBLE);
               adapter.setItems(breedList);
            }
        });

        viewModel.observeIsLoadingLiveData().observe(this,
                aBoolean -> pbLoading.setVisibility(aBoolean ? View.VISIBLE : View.GONE)
        );
    }

    private void initList() {
        rvBreeds.setLayoutManager(new LinearLayoutManager(this));
        rvBreeds.setAdapter(adapter);
    }

    @Override
    public void onBreedClick(Breed breed) {
        Intent intent = new Intent(BreedActivity.this, SubBreedActivity.class);
        intent.putExtra(SUB_BREED_KEY, breed);

        startActivity(intent);
    }
}
