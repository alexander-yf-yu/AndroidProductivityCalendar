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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.phase2calendar.R;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class SearchByDateDialog extends AppCompatDialogFragment {

    private EditText searchDay;
    private EditText searchMonth;
    private EditText searchYear;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private SearchByDateDialogListener listener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.search_by_date_dialog, null);

        builder.setView(view).setTitle("Search by Date").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("search", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int numSearchDay = Integer.parseInt(searchDay.getText().toString());
                    int numSearchMonth = Integer.parseInt(searchMonth.getText().toString());
                    int numSearchYear = Integer.parseInt(searchYear.getText().toString());

                    LocalDateTime search = LocalDateTime.of(numSearchYear, numSearchMonth, numSearchDay, 0, 0);

                    int relation = -1;
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = view.findViewById(radioId);
                    if(radioButton.getId() == R.id.before_this_date_button){
                        relation = 0;
                    }
                    else if(radioButton.getId() == R.id.on_this_date_button){
                        relation = 1;
                    } else {
                        relation = 2;
                    }

                    listener.searchByDate(search, relation);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "A field has incorrect format", Toast.LENGTH_SHORT).show();
                } catch (DateTimeException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "You have entered an invalid date or time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchDay = view.findViewById(R.id.search_day);
        searchMonth = view.findViewById(R.id.search_month);
        searchYear = view.findViewById(R.id.search_year);
        radioGroup = view.findViewById(R.id.radioGroup);

        RadioButton radioButtonTemp = view.findViewById(R.id.before_this_date_button);
        radioButtonTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
            }
        });

        radioButtonTemp = view.findViewById(R.id.on_this_date_button);
        radioButtonTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
            }
        });

        radioButtonTemp = view.findViewById(R.id.after_this_date_button);
        radioButtonTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (SearchByDateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(radioId);
    }

    public interface SearchByDateDialogListener {
        void searchByDate(LocalDateTime date, int relation);
    }
}
