package com.ssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssa.R;
import com.ssa.models.Message;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private ArrayList<Message> arrMessages;
    private Context context;
    private String kidId;

    public ChatAdapter(Context context, ArrayList<Message> arrMessages, String kidId) {
        this.context = context;
        this.arrMessages = arrMessages;
        this.kidId = kidId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.row_messages, parent, false);
        v.setTag(new MyViewHolder(v));
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message messageModel = arrMessages.get(position);
        if (messageModel != null) {
            holder.tvMessage.setText(messageModel.getText());
            if (kidId.equalsIgnoreCase(messageModel.getSenderId())) {
                holder.tvMessage.setGravity(Gravity.RIGHT);
            } else {
                holder.tvMessage.setGravity(Gravity.LEFT);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (arrMessages != null && arrMessages.size() > 0)
            return arrMessages.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage;

        public MyViewHolder(View view) {
            super(view);

            tvMessage = (TextView) view.findViewById(R.id.tv_message);
        }
    }
}
