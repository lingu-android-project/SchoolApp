package com.webclicz.schoolapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.Assignments;
import com.webclicz.schoolapp.Models.Examinations;
import com.webclicz.schoolapp.R;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class ExaminationAdapters extends RecyclerView.Adapter<ExaminationAdapters.MyViewHolder>  {
    private List<Examinations> assignmentList;

    @Override
    public ExaminationAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.examination_ist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExaminationAdapters.MyViewHolder holder, int position) {
        Examinations assignments = assignmentList.get(position);
        holder.exam_name.setText(assignments.getExamName());
        holder.subject_name.setText(assignments.getSubjectName());
        holder.supervisor.setText(assignments.getSupervisor());
        holder.syllabus.setText(assignments.getSyllabus());
        holder.time.setText(assignments.getStartTime() + " - " + assignments.getEndTime());
    }

    @Override
    public int getItemCount() {
        return this.assignmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView exam_name, subject_name, time, supervisor, syllabus;
        public MyViewHolder(View itemView) {
            super(itemView);
            exam_name = (TextView) itemView.findViewById(R.id.exam_name);
            subject_name = (TextView) itemView.findViewById(R.id.subject_name);
            time = (TextView) itemView.findViewById(R.id.time);
            supervisor = (TextView) itemView.findViewById(R.id.supervisor);
            syllabus = (TextView) itemView.findViewById(R.id.syllabus);

        }
    }

    public ExaminationAdapters(List<Examinations> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
