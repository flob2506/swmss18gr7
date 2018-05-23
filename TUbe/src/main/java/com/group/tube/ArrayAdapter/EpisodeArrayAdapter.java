package com.group.tube.ArrayAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.group.tube.parser.Parser;
import com.group.tube.utils.Utils;

import java.text.ParseException;
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

        final Episode currentEpisode = this.getItem(position);
        TextView name = listItem.findViewById(R.id.textViewEpisodeOverviewItemTitle);
        name.setText(currentEpisode.getEpisodeTitle());
        TextView date = listItem.findViewById(R.id.textViewEpisodeOverviewItemDate);

        if (currentEpisode.getDate() == null) {
            date.setText("");
        } else {
            date.setText(Utils.formatDate(currentEpisode.getDate()));
        }


        final ImageView imageView = listItem.findViewById(R.id.imageViewThumbnailEpisode);
        // if the thumbnailURL hasn't been set already
        if(currentEpisode.getThumbnailURL() == null) {
            NetworkConnector networkConnector = new NetworkConnector();
            networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
            networkConnector.loadMediaOfEpisode(new AsyncResponse<String>() {
                @Override
                public void processFinish(String response) {
                    Parser parser = new Parser();
                    try {
                        currentEpisode.setThumbnailURL(parser.parseMediaOfEpisode(response));
                        downloadAndSetImage(imageView, currentEpisode.getThumbnailURL());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        currentEpisode.setThumbnailURL("");
                    }
                }

                @Override
                public void handleProcessException(Exception e) {
                    e.printStackTrace();
                }
            }, currentEpisode.getId());
        }

        return listItem;
    }

    private void downloadAndSetImage(final ImageView imageView, String thumbnailURL) {
        if (thumbnailURL != null && !thumbnailURL.equals("")) {
            NetworkConnector networkConnector = new NetworkConnector();
            networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);

            networkConnector.downloadDrawable(new AsyncResponse<Drawable>() {
                @Override
                public void processFinish(Drawable response) {
                    if (response != null) {
                        imageView.setImageDrawable(response);
                    }
                }

                @Override
                public void handleProcessException(Exception e) {
                    e.printStackTrace();
                }
            }, thumbnailURL);
        }
    }
}