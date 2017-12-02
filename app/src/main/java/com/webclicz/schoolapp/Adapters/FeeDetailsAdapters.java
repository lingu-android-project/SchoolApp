package com.webclicz.schoolapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.FeeDetailsModel;
import com.webclicz.schoolapp.Models.Fees;
import com.webclicz.schoolapp.R;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class FeeDetailsAdapters extends RecyclerView.Adapter<FeeDetailsAdapters.MyViewHolder>  {
    private List<FeeDetailsModel> feeList;

    public FeeDetailsAdapters(List<FeeDetailsModel> feeList) {
        this.feeList = feeList;
    }
//
//    public FeeAdapters(List<Fee> feeList) {
//    }

    @Override
    public FeeDetailsAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fee_details_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeeDetailsAdapters.MyViewHolder holder, int position) {
        FeeDetailsModel fee = feeList.get(position);
        holder.feeType.setText(fee.getFeeType());
        holder.studentDetails.setText(fee.getStudentName() + ", "+ fee.getClassDivision() + "(" + fee.getAcademicYear()+ ")");
        holder.addmissionNumber.setText(fee.getAdmissionNumber());
        holder.paymentPeriod.setText(fee.getPaymentPeriod());
        holder.paidDate.setText(fee.getFeePaidDate());
        holder.amount.setText(fee.getAmount());
    }

    @Override
    public int getItemCount() {
        return this.feeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView feeType, studentDetails, addmissionNumber, paymentPeriod, paidDate, amount;
        public MyViewHolder(View itemView) {
            super(itemView);
            feeType = (TextView) itemView.findViewById(R.id.feeType);
            studentDetails = (TextView) itemView.findViewById(R.id.studentDetails);
            addmissionNumber = (TextView) itemView.findViewById(R.id.addmissionNumber);
            paymentPeriod = (TextView) itemView.findViewById(R.id.paymentPeriod);
            paidDate = (TextView) itemView.findViewById(R.id.paidDate);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }

}
