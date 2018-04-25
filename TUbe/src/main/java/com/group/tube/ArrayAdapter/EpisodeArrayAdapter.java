package com.group.tube.ArrayAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.group.tube.Models.Episodes;
import com.group.tube.R;
import com.group.tube.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class EpisodeArrayAdapter extends ArrayAdapter<Episodes> {

    private Context context;

    public EpisodeArrayAdapter(Context context, ArrayList<Episodes> list) {
        super(context, 0 , list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.episodes_overview_item,parent,false);

        Episodes currentEpisode = this.getItem(position);
        TextView name = listItem.findViewById(R.id.textViewEpisodeOverviewItemTitle);
        name.setText(currentEpisode.getEpisode_title());
        TextView date = listItem.findViewById(R.id.textViewEpisodeOverviewItemDate);

        if(currentEpisode.getDate() == null) {
            date.setText("");
        } else {
            date.setText(Utils.formatDate(currentEpisode.getDate()));
        }

        return listItem;
    }
}