package com.webclicz.schoolapp.Adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.Attendance;
import com.webclicz.schoolapp.R;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class AttendanceSummaryAdapters extends RecyclerView.Adapter<AttendanceSummaryAdapters.MyViewHolder>  {
    private List<Attendance> attendancesList;

    public AttendanceSummaryAdapters(List<Attendance> attendanceList) {
        this.attendancesList = attendanceList;

    }


    @Override
    public AttendanceSummaryAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_summary_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(AttendanceSummaryAdapters.MyViewHolder holder, int position) {
        Attendance attendance = attendancesList.get(position);
        holder.student_name.setText(attendance.getStudentName());
        holder.session_box.setText(attendance.getAttendanceSession());
        String isAbsent = attendance.getIsAbsent();
        if (isAbsent.equalsIgnoreCase("0")){
            holder.present.setVisibility(View.VISIBLE);
        }else{
            holder.absent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.attendancesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView student_name, session_box, present, absent;
        public MyViewHolder(View itemView) {
            super(itemView);
            student_name = (TextView) itemView.findViewById(R.id.student_name);
            session_box = (TextView) itemView.findViewById(R.id.session);
            present = (TextView) itemView.findViewById(R.id.present);
            absent = (TextView) itemView.findViewById(R.id.absent);
        }
    }

//    public AttendanceSummaryAdapters(List<Attendance> assignmentList) {
//        this.attendancesList = assignmentList;
//    }
}