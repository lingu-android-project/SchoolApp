package com.webclicz.schoolapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.Attendance;
import com.webclicz.schoolapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class MarkAttendanceAdapter extends RecyclerView.Adapter<MarkAttendanceAdapter.MyViewHolder>  {
    private List<Attendance> attendancesList;
    private ArrayList<Attendance> myItems = new ArrayList<>();

    public MarkAttendanceAdapter(List<Attendance> attendanceList) {
        this.attendancesList = attendanceList;

    }

    public MarkAttendanceAdapter(ArrayList<Attendance> getItems, Context context){
        try {
            myItems = getItems;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public MarkAttendanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mark_attendance_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MarkAttendanceAdapter.MyViewHolder holder, int position) {
        final Attendance attendance = attendancesList.get(position);
        holder.student_name.setText(attendance.getStudentName());
        holder.session_box.setText(attendance.getAttendanceSession());

        holder.mark.setSelected(attendance.getSelected());

        holder.mark.setOnCheckedChangeListener(null);
        holder.mark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.absent.setVisibility(View.VISIBLE);
                    attendance.setIsAbsent("1");
                    attendance.setSelected(true);
                }else {
                    holder.absent.setVisibility(View.GONE);
                    attendance.setIsAbsent("0");
                    attendance.setSelected(false);
                }
            }
        });
        holder.mark.setChecked(attendance.getSelected());
    }

    @Override
    public int getItemCount() {
        return this.attendancesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView student_name, session_box, absent;
        public CheckBox mark;
        public MyViewHolder(View itemView) {
            super(itemView);
            student_name = (TextView) itemView.findViewById(R.id.student_name);
            session_box = (TextView) itemView.findViewById(R.id.session);
            mark = (CheckBox) itemView.findViewById(R.id.mark);
            absent = (TextView) itemView.findViewById(R.id.absent);
        }
    }

//    public AttendanceSummaryAdapters(List<Attendance> assignmentList) {
//        this.attendancesList = assignmentList;
//    }
}