package com.group.tube.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.group.tube.R;

import java.util.Date;

public class CourseSemesterFilterDialogFragment extends DialogFragment {
    private CourseSemesterFilterDialogListener listener;
    private NumberPicker semesterPicker;
    private NumberPicker isWsPicker;
    private boolean chosenIsWs;
    private int chosenSemesterYear;

    public void setChosenIsWs(boolean chosenIsWs) {
        this.chosenIsWs = chosenIsWs;
    }

    public void setChosenSemesterYear(int chosenSemesterYear) {
        this.chosenSemesterYear = chosenSemesterYear;
    }
    String[] data;

    @Override

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (CourseSemesterFilterDialogListener) activity;
    }

    private View initializeDialogView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.semester_filter_dialog, null);
        semesterPicker = dialog.findViewById(R.id.numberPickerSemesterSemesterFilterDialog);
        semesterPicker.setMinValue(2007);
        Date date = new Date();
        semesterPicker.setMaxValue(date.getYear() + 1900);
        semesterPicker.setValue(chosenSemesterYear);
        isWsPicker = dialog.findViewById(R.id.numberPickerIsWsSemesterFilterDialog);
        data = new String[]{getResources().getString(R.string.ws), getResources().getString(R.string.ss)};
        isWsPicker.setMinValue(0);
        isWsPicker.setMaxValue(data.length - 1);
        isWsPicker.setDisplayedValues(data);
        isWsPicker.setValue(chosenIsWs ? 0 : 1);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialog = initializeDialogView();
        semesterPicker = dialog.findViewById(R.id.numberPickerSemesterSemesterFilterDialog);
        isWsPicker = dialog.findViewById(R.id.numberPickerIsWsSemesterFilterDialog);
        builder.setView(dialog)
            .setTitle(R.string.filter_by_semester)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int semesterYear = semesterPicker.getValue();
                String semesterType = data[isWsPicker.getValue()];
                boolean isWs = semesterType.equals(getResources().getString(R.string.ws));
                listener.onDialogPositiveClick(CourseSemesterFilterDialogFragment.this, semesterYear, isWs);
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogNegativeClick(CourseSemesterFilterDialogFragment.this);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface CourseSemesterFilterDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, int semesterYear, boolean isWs);
        void onDialogNegativeClick(DialogFragment dialog);
    }
}
