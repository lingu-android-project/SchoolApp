package com.webclicz.schoolapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.Assignments;
import com.webclicz.schoolapp.R;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class AssignmentsAdapters extends RecyclerView.Adapter<AssignmentsAdapters.MyViewHolder>  {
    private List<Assignments> assignmentList;

    @Override
    public AssignmentsAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_ist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssignmentsAdapters.MyViewHolder holder, int position) {
        Assignments assignments = assignmentList.get(position);
        holder.name.setText(assignments.getAssignmentName());
        holder.submissionDate.setText(assignments.getSubmissionDate());
    }

    @Override
    public int getItemCount() {
        return this.assignmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, submissionDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            submissionDate = (TextView) itemView.findViewById(R.id.submissionDate);

        }
    }

    public AssignmentsAdapters(List<Assignments> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
