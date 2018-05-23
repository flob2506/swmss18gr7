package com.group.tube.ArrayAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.group.tube.Models.Episode;
import com.group.tube.R;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.Utils;

import java.util.ArrayList;


public class EpisodeArrayAdapter extends ArrayAdapter<Episode> {

    private Context context;

    public EpisodeArrayAdapter(Context context, ArrayList<Episode> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.episodes_overview_item, parent, false);

        Episode currentEpisode = this.getItem(position);
        TextView name = listItem.findViewById(R.id.textViewEpisodeOverviewItemTitle);
        name.setText(currentEpisode.getEpisodeTitle());
        TextView date = listItem.findViewById(R.id.textViewEpisodeOverviewItemDate);

        if (currentEpisode.getDate() == null) {
            date.setText("");
        } else {
            date.setText(Utils.formatDate(currentEpisode.getDate()));
        }

        final ImageView imageView = listItem.findViewById(R.id.imageViewThumbnailEpisode);

        NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.downloadDrawable(new AsyncResponse<Drawable>() {
            @Override
            public void processFinish(Drawable response) {
                imageView.setImageDrawable(response);
            }

            @Override
            public void handleProcessException(Exception e) {
                e.printStackTrace();
            }
        }, currentEpisode.getThumbnailURL());

        return listItem;
    }
}