package ru.nsu.template.presentation.breedlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.template.R;
import ru.nsu.template.data.model.Breed;
import ru.nsu.template.presentation.breed.BreedActivity;
import ru.nsu.template.presentation.breed.adapter.BreedsAdapter;
import ru.nsu.template.presentation.breed.listener.OnBreedClickListener;

import static ru.nsu.template.presentation.breed.BreedActivity.BREED_KEY;

public class BreedListActivity extends AppCompatActivity implements OnBreedClickListener {

    private RecyclerView rvBreeds;
    private BreedsAdapter adapter;
    private TextView tvHeader;
    //private BreedListViewModel viewModel;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getIntent().getExtras();

        setContentView(R.layout.activity_breeds);

        rvBreeds = findViewById(R.id.rvBreeds);
        tvHeader = findViewById(R.id.tvHeader);
        adapter = new BreedsAdapter(this);
        pbLoading = findViewById(R.id.pbLoading);

        initList();

        BreedListViewModel viewModel = ViewModelProviders.of(this).get(BreedListViewModel.class);

        viewModel.observeHeaderLiveData().observe(this, s -> tvHeader.setText(s));

        viewModel.observeBreedsLiveData().observe(this, breedsList -> adapter.setItems(breedsList));

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
        Intent intent = new Intent(BreedListActivity.this, BreedActivity.class);
        intent.putExtra(BREED_KEY, breed);

        startActivity(intent);
    }
}
