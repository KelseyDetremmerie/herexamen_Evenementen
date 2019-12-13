package com.example.herexamenEvenementenKelseyDetremmerie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class EventFragment extends Fragment {
    public static final String EVENT_TITEL = "Titel";
    public static final String EVENT_OMSCHRIJVING = "Omschrijving";
    public static final String EVENT_LOCATIE = "Locatie";
    public static final String EVENT_PRIJS = "Prijs";
    public static final String EVENT_DATUM = "Datum";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference evenementRef = db.collection("Evenementen");
    private EventAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evenementen, container, false);
        setUpRecyclerView(view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Evenementen");

        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int postition) {
                Event event = adapter.getItem(postition);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(EVENT_TITEL, event.getTitel());
                intent.putExtra(EVENT_OMSCHRIJVING, event.getOmschrijving());
                intent.putExtra(EVENT_LOCATIE, event.getLocatie());
                intent.putExtra(EVENT_PRIJS, event.getPrijs());
                intent.putExtra(EVENT_DATUM, event.getDatum());
                startActivity(intent);
            }
        });
        return view;
    }

    private void setUpRecyclerView(View view) {

        Query query = evenementRef.orderBy("Titel");
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>().setQuery(query, Event.class).build();
        adapter = new EventAdapter(options);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
