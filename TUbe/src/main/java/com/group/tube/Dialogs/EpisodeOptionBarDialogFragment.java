package com.group.tube.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.group.tube.List.FavouriteList;
import com.group.tube.List.WatchLaterList;
import com.group.tube.Models.Episode;
import com.group.tube.R;
import com.group.tube.utils.LocalStorageUtils;
import com.group.tube.utils.Utils;

import java.io.Console;
import java.util.Date;
import java.util.Set;

public class EpisodeOptionBarDialogFragment extends DialogFragment {
    private Context context;
    private Episode episode;
    private View dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = initializeDialogView();
        builder.setView(dialogView);
        // Create the AlertDialog object and return it
        Dialog dialog = builder.create();
        dialog.getWindow().setGravity( Gravity.BOTTOM);

        return dialog;
    }

    private View initializeDialogView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.dialog = inflater.inflate(R.layout.episode_option_bar_dialog, null);

        if (this.episode.getIsInWatchLaterList()) {
            ((TextView)this.dialog.findViewById(R.id.textViewEpisodeWatchLater)).setText("Remove from Watch later list");
        } else {
            ((TextView)this.dialog.findViewById(R.id.textViewEpisodeWatchLater)).setText("Add to Watch later list");
        }

        this.dialog.findViewById(R.id.layoutEpisodeShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareEpisode();
            }
        });
        this.dialog.findViewById(R.id.layoutEpisodeCopy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard();
            }
        });
        this.dialog.findViewById(R.id.watchLaterLinearLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWatchLater();
            }
        });
        return this.dialog;
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Episode Share", getShareContent());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), getResources().getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }

    private String getShareContent() {
        return episode.getSharedContent();
    }

    private void shareEpisode() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getShareContent());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        getDialog().dismiss();
    }

    public void setEpisode(Episode episode, Context context) {
        this.episode = episode;
        this.context = context;
    }

    private void setWatchLater() {
        this.episode.toggleIsInWatchLaterList();
        getDialog().dismiss();

        Set<String> watchLaterList = WatchLaterList.getInstance();
        boolean isMarkedAsWatched = watchLaterList.contains(episode.getId());
        if (!isMarkedAsWatched) {
            watchLaterList.add(episode.getId());
        }
        else {
            watchLaterList.remove(episode.getId());
        }
        LocalStorageUtils.writeWatchLaterListToFile(this.context);
    }
}
