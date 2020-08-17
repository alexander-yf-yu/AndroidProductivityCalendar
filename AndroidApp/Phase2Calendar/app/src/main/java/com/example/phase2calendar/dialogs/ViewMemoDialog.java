package com.example.phase2calendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.phase2calendar.R;

public class ViewMemoDialog extends AppCompatDialogFragment {

    private TextView titleView;
    private TextView descriptionView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_memo_dialog, null);

        builder.setView(view).setTitle("").setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        titleView = view.findViewById(R.id.title_view);
        descriptionView = view.findViewById(R.id.description_view);

        titleView.setText(getArguments().getString("title"));
        descriptionView.setText(getArguments().getString("description"));

        return builder.create();
    }

}
