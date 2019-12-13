package com.example.herexamenEvenementenKelseyDetremmerie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesAdapter extends ListAdapter<Event, FavoritesAdapter.FavHolder> {

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getTitel().equals(newItem.getTitel()) && oldItem.getOmschrijving().equals(newItem.getOmschrijving())  && oldItem.getLocatie().equals(newItem.getLocatie())
                    && oldItem.getPrijs().equals(newItem.getPrijs()) && oldItem.getDatum().equals(newItem.getDatum());
        }
    };
    private OnItemClickListenerFavorite listener;

    protected FavoritesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evenement_item, parent, false);
        return new FavHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavHolder holder, int position) {
        Event event = getItem(position);

        holder.textViewTitel.setText(event.getTitel());
        holder.textViewPrijs.setText(event.getPrijs());
        holder.textViewDatum.setText(event.getDatum());
        holder.textViewGemeente.setText(event.getGemeente());
    }

    public Event getFavoriteAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListenerFavorite(OnItemClickListenerFavorite listener) {
        this.listener = listener;
    }

    public interface OnItemClickListenerFavorite {
        void onItemClickFavorite(Event event);
    }

    class FavHolder extends RecyclerView.ViewHolder {
        TextView textViewTitel, textViewPrijs, textViewDatum, textViewGemeente;

        public FavHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitel = itemView.findViewById(R.id.event_item_titel);
            textViewPrijs = itemView.findViewById(R.id.event_item_prijs);
            textViewDatum = itemView.findViewById(R.id.event_item_datum);
            textViewGemeente = itemView.findViewById(R.id.event_item_gemeente);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClickFavorite(getItem(position));
                    }
                }
            });
        }
    }
}
