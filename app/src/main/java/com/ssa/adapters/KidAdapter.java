package com.ssa.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssa.R;
import com.ssa.models.ChildModel;
import com.ssa.models.KidModel;

import java.util.ArrayList;

public class KidAdapter extends RecyclerView.Adapter<KidAdapter.MyViewHolder> {

    private ArrayList<KidModel.Kids> arrKids;
    private Context context;
    private OnItemClickListener listener;
    private boolean isFromMessages, isParent;
    private DatabaseReference mDatabase, mChildRef;

    public KidAdapter(Context context, ArrayList<KidModel.Kids> arrKids, boolean isFromMessages, boolean isParent) {
        this.context = context;
        this.arrKids = arrKids;
        this.isFromMessages = isFromMessages;
        this.isParent = isParent;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mChildRef = mDatabase.child("Kids");
    }

    public void setClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.row_kids, parent, false);
        v.setTag(new MyViewHolder(v));
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final KidModel.Kids kidModel = arrKids.get(position);
        if (kidModel != null) {
            if (mChildRef != null) {
                mChildRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(kidModel.getKidId())) {
                            ChildModel childModel = new ChildModel();
                            childModel.setKidId(kidModel.getKidId());
                            childModel.setKidName(kidModel.getFirstName());
                            childModel.setMessages("");
                            childModel.setParentId(kidModel.getParentUserRef());
                            childModel.setSchoolUniqueId(kidModel.getSchoolUniqueId());

                            mChildRef.child(kidModel.getKidId()).setValue(childModel);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            if (isParent)
                holder.tvKidName.setText(kidModel.getFirstName());
            else
                holder.tvKidName.setText(kidModel.getKidName());
            if (isFromMessages) {
                holder.tvSchoolName.setVisibility(View.GONE);
                holder.ivKid.setVisibility(View.GONE);
            } else {
                holder.ivKid.setVisibility(View.VISIBLE);
                holder.tvSchoolName.setVisibility(View.VISIBLE);
                holder.tvSchoolName.setText(kidModel.getSchoolName());
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(kidModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrKids != null && arrKids.size() > 0)
            return arrKids.size();
        else
            return 0;
    }

    public interface OnItemClickListener {
        public void onItemClick(Object object);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvKidName, tvSchoolName;
        ImageView ivKid;

        public MyViewHolder(View view) {
            super(view);

            tvKidName = (TextView) view.findViewById(R.id.tv_kid_name);
            tvSchoolName = (TextView) view.findViewById(R.id.tv_school_name);
            ivKid = (ImageView) view.findViewById(R.id.iv_kid);
        }

    }
}
