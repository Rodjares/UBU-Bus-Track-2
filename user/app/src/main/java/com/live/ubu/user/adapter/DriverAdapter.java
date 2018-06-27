package com.live.ubu.user.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.live.ubu.user.R;
import com.live.ubu.user.model.Driver;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private Context mContext;
    private List<Driver> mDataset;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_driver, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(mDataset.get(position).getName());
        holder.tvPosition.setText(mDataset.get(position).getPosition());
        holder.imageDriver.setImageResource(mDataset.get(position).getImageId());
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPosition;
        private ImageView imageDriver;

        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_driver_name);
            tvPosition = itemView.findViewById(R.id.tv_driver_position);
            imageDriver = itemView.findViewById(R.id.img_driver);

        }

    }


    public DriverAdapter(List<Driver> mData, Context context) {
        this.mDataset = mData;
        this.mContext = context;
    }

}
