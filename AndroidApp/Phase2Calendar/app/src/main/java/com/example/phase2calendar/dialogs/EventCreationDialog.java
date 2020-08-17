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
import java.util.Date;

public class EventCreationDialog extends AppCompatDialogFragment {

    private EventCreationDialogListener listener;

    private EditText editTitle;
    private EditText editDescription;

    private EditText startDay;
    private EditText startMonth;
    private EditText startYear;
    private EditText startHour;
    private EditText startMinute;

    private EditText endDay;
    private EditText endMonth;
    private EditText endYear;
    private EditText endHour;
    private EditText endMinute;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.event_creation_dialog, null);

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

                    LocalDateTime start = LocalDateTime.of(numStartYear, numStartMonth, numStartDay, numStartHour, numStartMinute);

                    int numEndDay = Integer.parseInt(endDay.getText().toString());
                    int numEndMonth = Integer.parseInt(endMonth.getText().toString());
                    int numEndYear = Integer.parseInt(endYear.getText().toString());
                    int numEndHour = Integer.parseInt(endHour.getText().toString());
                    int numEndMinute = Integer.parseInt(endMinute.getText().toString());

                    LocalDateTime end = LocalDateTime.of(numEndYear, numEndMonth, numEndDay, numEndHour, numEndMinute);

                    listener.createEvent(title, description, start, end);
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

        endDay = view.findViewById(R.id.end_day);
        endMonth = view.findViewById(R.id.end_month);
        endYear = view.findViewById(R.id.end_year);
        endHour = view.findViewById(R.id.end_hour);
        endMinute = view.findViewById(R.id.end_minute);

        if(getArguments().get("title") != null){
            editTitle.setText(getArguments().get("title").toString());
        }

        if(getArguments().get("description") != null){
            editDescription.setText(getArguments().get("description").toString());
        }

        if(getArguments().get("startTime") != null){
            LocalDateTime start = (LocalDateTime) getArguments().get("startTime");
            startDay.setText(Integer.toString(start.getDayOfMonth()));
            startMonth.setText(Integer.toString(start.getMonth().getValue()));
            startYear.setText(Integer.toString(start.getYear()));
            startHour.setText(Integer.toString(start.getHour()));
            startMinute.setText(Integer.toString(start.getMinute()));
        }

        if(getArguments().get("endTime") != null){
            LocalDateTime end = (LocalDateTime) getArguments().get("endTime");
            endDay.setText(Integer.toString(end.getDayOfMonth()));
            endMonth.setText(Integer.toString(end.getMonth().getValue()));
            endYear.setText(Integer.toString(end.getYear()));
            endHour.setText(Integer.toString(end.getHour()));
            endMinute.setText(Integer.toString(end.getMinute()));
        }

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EventCreationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public interface EventCreationDialogListener {
        void createEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime);
    }
}
