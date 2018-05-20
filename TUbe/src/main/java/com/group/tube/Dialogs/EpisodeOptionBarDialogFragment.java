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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.group.tube.Models.Episode;
import com.group.tube.R;

import java.util.Date;

public class EpisodeOptionBarDialogFragment extends DialogFragment {
    private Episode episode;

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
        View dialog = inflater.inflate(R.layout.episode_option_bar_dialog, null);
        dialog.findViewById(R.id.layoutEpisodeShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareEpisode();
            }
        });
        dialog.findViewById(R.id.layoutEpisodeCopy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard();
            }
        });
        return dialog;
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

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
