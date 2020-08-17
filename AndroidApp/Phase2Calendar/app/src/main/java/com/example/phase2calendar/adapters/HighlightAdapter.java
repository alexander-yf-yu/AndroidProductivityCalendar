package com.example.phase2calendar.adapters;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.R;
import com.example.phase2calendar.logic.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.HighlightViewHolder> {

    private ArrayList<Listable> nameables;
    private Listable selectedItem;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public class HighlightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleView;
        public TextView contentView;
        public TextView startView;
        public TextView endView;

        public HighlightViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.titleView = itemView.findViewById(R.id.titleView);
            this.contentView = itemView.findViewById(R.id.contentView);
            this.startView = itemView.findViewById(R.id.startView);
            this.endView = itemView.findViewById(R.id.endView);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            if(selectedPosition != getAdapterPosition()) {
                selectedPosition = getAdapterPosition();
                notifyDataSetChanged();
                selectedItem = nameables.get(selectedPosition);
            } else {
                selectedPosition = RecyclerView.NO_POSITION;
                selectedItem = null;
                notifyDataSetChanged();
            }
        }
    }

    public HighlightAdapter(ArrayList<Listable> nameables) {
        this.nameables = nameables;
        this.selectedItem = null;
    }

    @NonNull
    @Override
    public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_item, viewGroup, false);
        HighlightViewHolder genericViewHolder = new HighlightViewHolder(v);
        return genericViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HighlightViewHolder genericViewHolder, int i) {
        Listable currentItem = nameables.get(i);
        String name = currentItem.getName();
        String description = currentItem.getDescription();
        LocalDateTime start = currentItem.getStartTime();
        LocalDateTime end = currentItem.getEndTime();

        DateFormatConverter dateFormatConverter = new DateFormatConverter();

        if(null == name){
            genericViewHolder.titleView.setVisibility(View.GONE);
        } else {
            genericViewHolder.titleView.setText(name);
        }

        if(null == description){
            genericViewHolder.contentView.setVisibility(View.GONE);
        } else {
            genericViewHolder.contentView.setText(description);
        }

        if(null == start){
            genericViewHolder.startView.setVisibility(View.GONE);
        } else {
            genericViewHolder.startView.setText(dateFormatConverter.convertLocalDateTime(start));
        }

        if(null == end){
            genericViewHolder.endView.setVisibility(View.GONE);
        } else {
            genericViewHolder.endView.setText(dateFormatConverter.convertLocalDateTime(end));
        }

        if(selectedPosition == i){
            genericViewHolder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            genericViewHolder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return nameables.size();
    }

    public Listable getSelectedItem(){ return selectedItem; }
}
