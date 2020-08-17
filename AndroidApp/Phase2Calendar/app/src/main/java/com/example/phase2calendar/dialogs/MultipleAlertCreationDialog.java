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

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class MultipleAlertCreationDialog extends AppCompatDialogFragment {

    private MultipleAlertCreationDialogListener listener;

    private EditText editTitle;
    private EditText editDescription;

    private EditText startDay;
    private EditText startMonth;
    private EditText startYear;
    private EditText startHour;
    private EditText startMinute;

    private EditText frequencyDays;
    private EditText frequencyHours;
    private EditText frequencyMinutes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.multiple_alert_creation_dialog, null);

        builder.setView(view).setTitle("New Event").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();

                try {
                    int numStartDay = Integer.parseInt(startDay.getText().toString());
                    int numStartMonth = Integer.parseInt(startMonth.getText().toString());
                    int numStartYear = Integer.parseInt(startYear.getText().toString());
                    int numStartHour = Integer.parseInt(startHour.getText().toString());
                    int numStartMinute = Integer.parseInt(startMinute.getText().toString());

                    int numFrequencyDays = 0;
                    int numFrequencyHours = 0;
                    int numFrequencyMinutes = 0;

                    if(frequencyDays.getText().toString().length() > 0){
                        numFrequencyDays = Integer.parseInt(frequencyDays.getText().toString());
                    }
                    if(frequencyHours.getText().toString().length() > 0){
                        numFrequencyHours = Integer.parseInt(frequencyHours.getText().toString());
                    }
                    if(frequencyMinutes.getText().toString().length() > 0){
                        numFrequencyMinutes = Integer.parseInt(frequencyMinutes.getText().toString());
                    }

                    LocalDateTime start = LocalDateTime.of(numStartYear, numStartMonth, numStartDay, numStartHour, numStartMinute);

                    listener.createAlerts(title, description, start, numFrequencyDays, numFrequencyHours, numFrequencyMinutes);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "A field has incorrect format", Toast.LENGTH_SHORT).show();
                } catch (DateTimeException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "You have entered an invalid date or time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTitle = view.findViewById(R.id.edit_title);
        editDescription = view.findViewById(R.id.edit_description);

        startDay = view.findViewById(R.id.start_day);
        startMonth = view.findViewById(R.id.start_month);
        startYear = view.findViewById(R.id.start_year);
        startHour = view.findViewById(R.id.start_hour);
        startMinute = view.findViewById(R.id.start_minute);

        frequencyDays = view.findViewById(R.id.frequency_days);
        frequencyHours = view.findViewById(R.id.frequency_hours);
        frequencyMinutes = view.findViewById(R.id.frequency_minutes);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MultipleAlertCreationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public interface MultipleAlertCreationDialogListener {
        void createAlerts(String title, String description, LocalDateTime start, int days, int hours, int minutes);
    }

}
