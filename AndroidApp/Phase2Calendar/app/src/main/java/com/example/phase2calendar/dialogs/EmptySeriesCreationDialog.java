package com.example.phase2calendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.phase2calendar.R;

public class EmptySeriesCreationDialog extends AppCompatDialogFragment {

    private EditText editTitle;
    private EditText editDescription;
    private EmptySeriesCreationDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.empty_series_creation_dialog, null);

        builder.setView(view).setTitle("New Empty Series").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();
                listener.createEmptySeries(title, description);
            }
        });

        editTitle = view.findViewById(R.id.edit_title);
        editDescription = view.findViewById(R.id.edit_description);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EmptySeriesCreationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public interface EmptySeriesCreationDialogListener {
        void createEmptySeries(String title, String description);
    }

}
