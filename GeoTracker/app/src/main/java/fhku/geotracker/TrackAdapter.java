package fhku.geotracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    public interface OnTrackEditListener {
        public void onTrackEdit(Track track);
    }

    public interface OnTrackMapListener {
        public void onTrackMap(Track track);
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView subtitle;
        public TextView description;
        public Track track;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.track_title);
            subtitle = itemView.findViewById(R.id.track_subtitle);
            description = itemView.findViewById(R.id.track_description);

            itemView
                .findViewById(R.id.track_map)
                .setOnClickListener((View view) -> {
                    if (mapListener != null) {
                        mapListener.onTrackMap(track);
                    }
                });

            itemView
                .findViewById(R.id.track_edit)
                .setOnClickListener((View view) -> {
                    if (editListener != null) {
                        editListener.onTrackEdit(track);
                    }
                });
        }
    }

    protected OnTrackEditListener editListener;
    protected OnTrackMapListener mapListener;
    protected TrackDao dao;
    protected List<Track> list;

    public TrackAdapter(TrackDao dao) {
        this.dao = dao;
        list = dao.getTracks();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.track_view, parent, false);
        return new TrackViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = list.get(position);
        holder.title.setText(track.title);
        holder.subtitle.setText("1h 15min");
        holder.description.setText(track.description);
        holder.track = track;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void setEditListener(OnTrackEditListener editListener) {
        this.editListener = editListener;
    }

    public void setMapListener(OnTrackMapListener mapListener) {
        this.mapListener = mapListener;
    }
}
