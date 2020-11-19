package at.stefanhuber.geotracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.stefanhuber.geotracker.R;
import at.stefanhuber.geotracker.db.TrackDao;
import at.stefanhuber.geotracker.models.Track;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    protected TrackDao dao;
    protected List<Track> tracks;
    protected OnTrackEditListener onTrackEditListener;
    protected OnTrackMapListener onTrackMapListener;

    public TrackAdapter(TrackDao dao) {
        this.dao = dao;
        loadData();
    }

    public interface OnTrackEditListener {
        void onTrackEdit(Track track);
    }

    public interface OnTrackMapListener {
        void onTrackMap(Track track);
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView secondaryTitle;
        public TextView description;
        public Button edit;
        public Button map;
        public Track track;

        public TrackViewHolder(@NonNull View card) {
            super(card);
            title = card.findViewById(R.id.track_card_title);
            secondaryTitle = card.findViewById(R.id.track_card_secondary_title);
            description = card.findViewById(R.id.track_card_description);
            edit = card.findViewById(R.id.track_card_edit);
            map = card.findViewById(R.id.track_card_map);

            edit.setOnClickListener((View view) -> {
                if (onTrackEditListener != null) {
                    onTrackEditListener.onTrackEdit(track);
                }
            });
            map.setOnClickListener((View view) -> {
                if (onTrackMapListener != null) {
                    onTrackMapListener.onTrackMap(track);
                }
            });
        }
    }

    public void loadData() {
        tracks = dao.listTracks();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.track_card, parent, false);

        return new TrackViewHolder(card);
    }


    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.title.setText(track.name);
        holder.secondaryTitle.setText(track.formattedDuration());
        holder.description.setText(track.description);

        holder.track = tracks.get(position);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public void setOnTrackEditListener(OnTrackEditListener listener) {
        onTrackEditListener = listener;
    }

    public void setOnTrackMapListener(OnTrackMapListener listener) {
        onTrackMapListener = listener;
    }

}
