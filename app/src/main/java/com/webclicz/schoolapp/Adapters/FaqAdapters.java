package com.webclicz.schoolapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webclicz.schoolapp.Models.FAQ;
import com.webclicz.schoolapp.Models.Fees;
import com.webclicz.schoolapp.R;

import java.util.List;

/**
 * Created by Linesh on 10/22/2017.
 */

public class FaqAdapters extends RecyclerView.Adapter<FaqAdapters.MyViewHolder>  {
    private List<FAQ> faqList;

    public FaqAdapters(List<FAQ> faqList) {
        this.faqList = faqList;
    }
//
//    public FeeAdapters(List<Fee> feeList) {
//    }

    @Override
    public FaqAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FaqAdapters.MyViewHolder holder, int position) {
        FAQ faq = faqList.get(position);
        holder.question.setText(faq.getFAQ());
        holder.answer.setText(faq.getFAQDetail());
    }

    @Override
    public int getItemCount() {
        return this.faqList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question, answer;
        public MyViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question);
            answer = (TextView) itemView.findViewById(R.id.answer);
        }
    }

}
