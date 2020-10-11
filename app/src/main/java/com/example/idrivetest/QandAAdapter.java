package com.example.idrivetest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;


public class QandAAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<QuestionAndAnswerModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    QandAAdapter(Context context, ArrayList<QuestionAndAnswerModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        QuestionAndAnswerModel  question = mData.get(position);
        final MyViewHolder mholder = (MyViewHolder) holder;
        mholder.ques.setText(question.get_question());

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ques;
        TextView ans;

        MyViewHolder(View itemView) {
            super(itemView);
            ques = itemView.findViewById(R.id.question);
            ans = itemView.findViewById(R.id.answer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent =new Intent(context, WebView.class);
            String model= mData.get(getAdapterPosition()).get_answer();
            intent.putExtra("url",model);
          context.startActivity(intent);
          //  if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

