package com.example.phase2calendar.adapters;

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

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.GenericViewHolder> {

    private ArrayList<Listable> nameables;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int i);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public TextView contentView;
        public TextView startView;
        public TextView endView;

        public GenericViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.titleView);
            this.contentView = itemView.findViewById(R.id.contentView);
            this.startView = itemView.findViewById(R.id.startView);
            this.endView = itemView.findViewById(R.id.endView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    public GenericAdapter(ArrayList<Listable> nameables) { this.nameables = nameables; }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_item, viewGroup, false);
        GenericViewHolder genericViewHolder = new GenericViewHolder(v, this.listener);
        return genericViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder genericViewHolder, int i) {
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
            genericViewHolder.startView.setText("Start: " + dateFormatConverter.convertLocalDateTime(start));
        }

        if(null == end){
            genericViewHolder.endView.setVisibility(View.GONE);
        } else {
            genericViewHolder.endView.setText("End: " + dateFormatConverter.convertLocalDateTime(end));
        }
    }

    @Override
    public int getItemCount() {
        return nameables.size();
    }
}
