package com.group.tube.ArrayAdapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.group.tube.Dialogs.EpisodeOptionBarDialogFragment;
import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.R;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.parser.Parser;
import com.group.tube.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Set;

import static java.lang.StrictMath.max;


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

        if(currentEpisode.getCourseTitle() != null) {
            String nameWithCourse = currentEpisode.getEpisodeTitle() + " (" + currentEpisode.getCourseTitle() + ")";
            name.setText(nameWithCourse);
        } else {
            name.setText(currentEpisode.getEpisodeTitle());
        }
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


        final ImageView imageView = listItem.findViewById(R.id.imageViewThumbnailEpisode);
        // if the thumbnailURL hasn't been set already
        if (currentEpisode.getThumbnailURL() == null) {
            NetworkConnector networkConnector = new NetworkConnector();
            networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
            networkConnector.loadMediaOfEpisode(new AsyncResponse<String>() {
                @Override
                public void processFinish(String response) {
                    Parser parser = new Parser();
                    try {
                        currentEpisode.setThumbnailURL(parser.parseMediaOfEpisode(response));
                        downloadAndSetImage(imageView, currentEpisode.getThumbnailURL());
                        //downloadAndSetImage(imageView, currentEpisode);
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
        } /*else if(currentEpisode.getThumbnailDrawable() != null){
            imageView.setImageDrawable(currentEpisode.getThumbnailDrawable());
        } else {
            imageView.setImageDrawable(getContext().getResources().getDrawable(android.R.drawable.presence_video_busy));
        }*/

        final TextView time = listItem.findViewById(R.id.time_field);
        if(currentEpisode.getTime() == null) {
            NetworkConnector networkConnector = new NetworkConnector();
            networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
            networkConnector.loadTimeOfEpisode(new AsyncResponse<String>() {
                @Override
                public void processFinish(String response) {
                    Parser parser = new Parser();
                    try {
                        currentEpisode.setTime(parser.parseTimeOfEpisode(response));
                        time.setText(currentEpisode.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        currentEpisode.setTime("00:00:00");
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

    @Override
    public int getViewTypeCount() {
        return max(1, getCount());
    }

    @Override
    public int getItemViewType(int position) {
        return max(1, position);
    }

    /*private void downloadAndSetImage(final ImageView imageView, final Episode currentEpisode) {
        if (currentEpisode.getThumbnailURL() != null && !currentEpisode.getThumbnailURL().equals("")) {*/
    private void downloadAndSetImage(final ImageView imageView, String thumbnailURL) {

        if (thumbnailURL != null && !thumbnailURL.equals("")) {

            NetworkConnector networkConnector = new NetworkConnector();
            networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);

            networkConnector.downloadDrawable(new AsyncResponse<Drawable>() {
                @Override
                public void processFinish(Drawable response) {
                    if (response != null) {
                        imageView.setImageDrawable(response);
                        //currentEpisode.setThumbnailDrawable(response);
                    }
                }

                @Override
                public void handleProcessException(Exception e) {
                    e.printStackTrace();
                }
            }, thumbnailURL);//currentEpisode.getThumbnailURL());
        }
    }
  
    private void showDialog(Episode episode)
    {
        EpisodeOptionBarDialogFragment dialog = new EpisodeOptionBarDialogFragment();
        dialog.setEpisode(episode, context);
        Activity activity = (Activity) context;
        dialog.show(activity.getFragmentManager(), "");
    }
}