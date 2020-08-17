package com.example.phase2calendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.phase2calendar.R;


public class MemoCreationDialog extends AppCompatDialogFragment {
    private MemoCreationDialogListener listener;

    private EditText editTitle;
    private EditText editDescription;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.memo_creation_dialog, null);

        builder.setView(view).setTitle("New Memo").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();

                listener.createMemo(title, description);
            }
        });

        editTitle = view.findViewById(R.id.edit_title_memo);
        editDescription = view.findViewById(R.id.edit_description_memo);

        if(getArguments().get("title") != null){
            editTitle.setText(getArguments().get("title").toString());
        }

        if(getArguments().get("description") != null){
            editDescription.setText(getArguments().get("description").toString());
        }



        return builder.create();
    }








    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MemoCreationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public interface MemoCreationDialogListener {
        void createMemo(String title, String description);
    }
}
