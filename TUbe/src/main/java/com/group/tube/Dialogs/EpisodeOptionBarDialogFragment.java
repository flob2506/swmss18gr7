package com.group.tube.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.group.tube.R;

import java.util.Date;

public class EpisodeOptionBarDialogFragment extends DialogFragment {

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
        return dialog;
    }

}
