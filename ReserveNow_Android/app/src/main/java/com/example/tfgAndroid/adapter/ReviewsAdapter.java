// ReviewsAdapter.java
package com.example.tfgAndroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgAndroid.R;
import com.example.tfgAndroid.model.ReviewDTO;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.Holder> {
    private final List<ReviewDTO> items;

    public ReviewsAdapter(List<ReviewDTO> items) {
        this.items = items;
    }

    /** Sustituye la lista actual por una nueva */
    public void setItems(List<ReviewDTO> nuevos) {
        items.clear();
        items.addAll(nuevos);
        notifyDataSetChanged();
    }

    /** Añade una reseña al principio */
    public void addItem(ReviewDTO review) {
        items.add(0, review);
        notifyItemInserted(0);
    }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int pos) {
        ReviewDTO r = items.get(pos);
        holder.usernameTv.setText(r.getUsername());
        holder.restaurantTv.setText(r.getRestaurantName());
        holder.reviewTextTv.setText(r.getReviewText());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView usernameTv, restaurantTv, reviewTextTv;

        Holder(View item) {
            super(item);
            usernameTv   = item.findViewById(R.id.tvUsername);
            restaurantTv = item.findViewById(R.id.tvRestaurantName);
            reviewTextTv = item.findViewById(R.id.tvReviewText);
        }
    }
}
