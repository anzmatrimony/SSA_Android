package com.ssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssa.R;
import com.ssa.models.SchoolModel;

import java.util.ArrayList;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolsAdapter.MyViewHolder> {

    private ArrayList<SchoolModel.Schools> arrSchools;
    private Context context;

    public SchoolsAdapter(Context context, ArrayList<SchoolModel.Schools> arrSchools) {
        this.context = context;
        this.arrSchools = arrSchools;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.row_schools, parent, false);
        v.setTag(new MyViewHolder(v));
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SchoolModel.Schools schools = arrSchools.get(position);
        if(schools!=null){
            holder.tvSchoolName.setText(schools.getSchoolName());
            holder.tvDateTime.setText(schools.getCreatedOn());
        }
    }

    @Override
    public int getItemCount() {
        if (arrSchools != null && arrSchools.size() > 0)
            return arrSchools.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSchoolName, tvDateTime;

        public MyViewHolder(View view) {
            super(view);

            tvSchoolName = (TextView) view.findViewById(R.id.tv_school_name);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
        }

    }
}
