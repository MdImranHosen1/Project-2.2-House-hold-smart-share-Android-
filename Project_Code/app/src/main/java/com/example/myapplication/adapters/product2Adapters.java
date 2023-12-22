package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activitices.ViewAllActivity;
import com.example.myapplication.models.product2Model;

import java.util.List;

public class product2Adapters   extends RecyclerView.Adapter<product2Adapters.ViewHolder> {

    Context context;
    List<product2Model> product2ModelList;

    public product2Adapters(Context context, List<product2Model> product2ModelList) {
        this.context = context;
        this.product2ModelList = product2ModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product2_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(product2ModelList.get(position).getImg_url()).into(holder.catImg);
        holder.name.setText(product2ModelList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",product2ModelList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return product2ModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg=itemView.findViewById(R.id.product2_cat_img);
            name=itemView.findViewById(R.id.product2_cat_name);
        }
    }
}
