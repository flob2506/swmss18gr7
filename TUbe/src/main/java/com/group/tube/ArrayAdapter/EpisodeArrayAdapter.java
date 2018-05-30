package com.group.tube.ArrayAdapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.group.tube.Dialogs.EpisodeOptionBarDialogFragment;
import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.R;
import com.group.tube.utils.Utils;

import java.util.ArrayList;
import java.util.Set;


public class EpisodeArrayAdapter extends ArrayAdapter<Episode> {

    private Context context;

    public EpisodeArrayAdapter(Context context, ArrayList<Episode> list) {
        super(context, 0 , list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.episodes_overview_item,parent,false);

        final Episode currentEpisode = this.getItem(position);
        TextView name = listItem.findViewById(R.id.textViewEpisodeOverviewItemTitle);
        name.setText(currentEpisode.getEpisodeTitle());
        TextView date = listItem.findViewById(R.id.textViewEpisodeOverviewItemDate);

        listItem.findViewById(R.id.imageViewEpisodeMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(currentEpisode);
            }
        });

        if(currentEpisode.getDate() == null) {
            date.setText("");
        } else {
            date.setText(Utils.formatDate(currentEpisode.getDate()));
        }

        return listItem;
    }

    private void showDialog(Episode episode) {
        EpisodeOptionBarDialogFragment dialog = new EpisodeOptionBarDialogFragment();
        dialog.setEpisode(episode);
        Activity activity = (Activity) context;
        dialog.show(activity.getFragmentManager(), "");
    }
}