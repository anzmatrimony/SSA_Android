package com.ssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ssa.R;
import com.ssa.models.ActivityResModel;
import com.ssa.utils.Utils;

import java.util.ArrayList;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.MyViewHolder> {

    private ArrayList<ActivityResModel.KidData> arrKids;
    private Context context;
    private ActivityAdapter activityAdapter;
    private OnItemClickListener listener;

    public ActivitiesAdapter(Context context, ArrayList<ActivityResModel.KidData> arrKids) {
        this.context = context;
        this.arrKids = arrKids;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.row_kid_activity, parent, false);
        v.setTag(new MyViewHolder(v));
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ActivityResModel.KidData kidData = arrKids.get(position);
        if (kidData != null) {
            holder.tvKidName.setText(kidData.getKidName());
            if (kidData.getArrActivities() != null && kidData.getArrActivities().size() > 0) {
                activityAdapter = new ActivityAdapter(kidData.getArrActivities(), kidData.getKiduserID());
                holder.lvActivities.setAdapter(activityAdapter);
                Utils.setListViewHeightBasedOnItems(holder.lvActivities);
            }

            holder.tvKidName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.flag) {
                        holder.flag = false;
                        holder.lvActivities.setVisibility(View.GONE);
                    } else {
                        holder.flag = true;
                        holder.lvActivities.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (arrKids != null && arrKids.size() > 0)
            return arrKids.size();
        else
            return 0;
    }

    public void setClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(Object object, long kidUserID);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvKidName;
        ListView lvActivities;
        boolean flag = false;

        public MyViewHolder(View view) {
            super(view);

            tvKidName = (TextView) view.findViewById(R.id.tv_kid_name);
            lvActivities = (ListView) view.findViewById(R.id.lv_activities);
        }

    }

    public class ActivityAdapter extends BaseAdapter {

        private ArrayList<ArrayList<ActivityResModel.ActivityData>> arrActivities;
        private long kidUserID;

        public ActivityAdapter(ArrayList<ArrayList<ActivityResModel.ActivityData>> arrActivities, long kidUserID) {
            this.arrActivities = arrActivities;
            this.kidUserID = kidUserID;
        }

        @Override
        public int getCount() {
            if (arrActivities != null && arrActivities.size() > 0 && arrActivities.get(0) != null)
                return arrActivities.get(0).size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            return arrActivities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CustomViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_activity, parent, false);
                holder = new CustomViewHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (CustomViewHolder) convertView.getTag();
            }

            if (arrActivities.get(0) != null) {
                final ActivityResModel.ActivityData activityData = arrActivities.get(0).get(position);
                if (activityData != null) {
                    holder.tvActivity.setText(activityData.getActivitysubject());

                    holder.tvActivity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onItemClick(activityData, kidUserID);
                        }
                    });
                }
            }

            return convertView;
        }

        public class CustomViewHolder {
            TextView tvActivity;

            public CustomViewHolder(View convertView) {
                tvActivity = (TextView) convertView.findViewById(R.id.tv_activity);
            }
        }
    }
}
