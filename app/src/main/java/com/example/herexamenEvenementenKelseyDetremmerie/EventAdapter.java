package com.example.herexamenEvenementenKelseyDetremmerie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder> {

    private OnItemClickListener listener;

    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder eventHolder, int i, @NonNull Event event) {
        eventHolder.textViewTitel.setText(event.getTitel());
        eventHolder.textViewGemeente.setText(event.getGemeente());
        eventHolder.textViewPrijs.setText(event.getPrijs());
        eventHolder.textViewDatum.setText(event.getDatum());
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evenement_item, parent, false);
        return new EventHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int postition);
    }

    class EventHolder extends RecyclerView.ViewHolder {
        TextView textViewTitel;
        TextView textViewGemeente;
        TextView textViewPrijs;
        TextView textViewDatum;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitel = itemView.findViewById(R.id.event_item_titel);
            textViewGemeente = itemView.findViewById(R.id.event_item_gemeente);
            textViewPrijs = itemView.findViewById(R.id.event_item_prijs);
            textViewDatum = itemView.findViewById(R.id.event_item_datum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
}
