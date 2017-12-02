package com.webclicz.schoolapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webclicz.schoolapp.MainActivity;
import com.webclicz.schoolapp.Models.Fees;
import com.webclicz.schoolapp.Models.StudentList;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.CircleTransform;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder>  {
    private List<StudentList> stuList;
    private Context context;

    public StudentListAdapter(List<StudentList> stuList, Context applicationContext) {
        this.stuList = stuList;
        this.context = applicationContext;
    }
//
//    public FeeAdapters(List<Fee> stuList) {
//    }

    @Override
    public StudentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentListAdapter.MyViewHolder holder, int position) {
        StudentList stuLists = stuList.get(position);
        holder.multiStuName.setText(stuLists.getStudentName());
        //holder.multiStuImage.setImageResource(Integer.parseInt(stuLists.getPhotoFilePath()));
        Picasso.with(this.context)
                .load(stuLists.getPhotoFilePath())
                .resize(140, 140)
                .centerCrop()
                .transform(new CircleTransform())
                .into(holder.multiStuImage);
    }

    @Override
    public int getItemCount() {
        return this.stuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView multiStuName;
        ImageView multiStuImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            multiStuImage = (ImageView) itemView.findViewById(R.id.multiStuImage);
            multiStuName = (TextView) itemView.findViewById(R.id.multiStuName);
        }
    }

}
