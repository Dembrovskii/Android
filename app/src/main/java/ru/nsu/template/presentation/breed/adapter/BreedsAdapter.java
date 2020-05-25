package ru.nsu.template.presentation.breed.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import com.bumptech.glide.Glide;
import ru.nsu.template.R;
import ru.nsu.template.data.model.Breed;
import ru.nsu.template.data.model.Avatar;
import ru.nsu.template.presentation.breed.listener.OnBreedClickListener;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.ViewHolder> {
    private List<Breed> items = Collections.emptyList();
    private OnBreedClickListener listener;

    public BreedsAdapter(OnBreedClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Breed> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Breed breed = items.get(position);
        final Avatar avatar = breed.getPicture();

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(avatar.getUrl()))
                .into(holder.ivAvatar);

        holder.tvName.setText(breed.getName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBreedClick(breed);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_breed, parent, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);/////////////////11111111
        }
    }
}
