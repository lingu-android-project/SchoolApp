package com.webclicz.schoolapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.Assignments;
import com.webclicz.schoolapp.Models.Fees;
import com.webclicz.schoolapp.R;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class FeeAdapters extends RecyclerView.Adapter<FeeAdapters.MyViewHolder>  {
    private List<Fees> feeList;

    public FeeAdapters(List<Fees> feeList) {
        this.feeList = feeList;
    }
//
//    public FeeAdapters(List<Fee> feeList) {
//    }

    @Override
    public FeeAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fee_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeeAdapters.MyViewHolder holder, int position) {
        Fees fee = feeList.get(position);
        holder.feeType.setText(fee.getFeeType());
        holder.academicYear.setText(fee.getAcademicYear());
        holder.amoount.setText(fee.getPaymentPeriod());
        holder.amountPaid.setText("Amount Paid: \n" + fee.getAmountPaid() + "\n" + fee.getPayableBy());
        holder.balance.setText("Balance: \n" + fee.getBalance() + " of " + fee.getAmount());

    }

    @Override
    public int getItemCount() {
        return this.feeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView feeType, academicYear, amoount, amountPaid, balance;
        public MyViewHolder(View itemView) {
            super(itemView);
            feeType = (TextView) itemView.findViewById(R.id.feeType);
            academicYear = (TextView) itemView.findViewById(R.id.academicYear);
            amoount = (TextView) itemView.findViewById(R.id.amoount);
            amountPaid = (TextView) itemView.findViewById(R.id.amountPaid);
            balance = (TextView) itemView.findViewById(R.id.balance);

        }
    }

}
