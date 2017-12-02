package com.webclicz.schoolapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.EventsModel;
import com.webclicz.schoolapp.R;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class EventsAdapters extends RecyclerView.Adapter<EventsAdapters.MyViewHolder>  {
    private List<EventsModel> eventList;

    public EventsAdapters(List<EventsModel> eventList) {
        this.eventList = eventList;
    }

    @Override
    public EventsAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsAdapters.MyViewHolder holder, int position) {
        EventsModel event = eventList.get(position);
        holder.event_subject.setText(event.getNoticeBoardSubject());
        holder.description.setText(event.getDescription());
        holder.event_date.setText(event.getEventStartDate() + " - " + event.getEventEndDate());
        if (event.getApplicableTo().equalsIgnoreCase("Student")){
            holder.both.setVisibility(LinearLayout.GONE);
            holder.studentOnly.setVisibility(LinearLayout.VISIBLE);
        }else{
            holder.studentOnly.setVisibility(LinearLayout.GONE);
            holder.both.setVisibility(LinearLayout.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_subject, description, event_date;
        public LinearLayout both, studentOnly;
        public MyViewHolder(View itemView) {
            super(itemView);
            event_subject = (TextView) itemView.findViewById(R.id.event_subject);
            description = (TextView) itemView.findViewById(R.id.description);
            event_date = (TextView) itemView.findViewById(R.id.event_date);
            both = (LinearLayout) itemView.findViewById(R.id.both);
            studentOnly = (LinearLayout) itemView.findViewById(R.id.studentOnly);
        }
    }

}
