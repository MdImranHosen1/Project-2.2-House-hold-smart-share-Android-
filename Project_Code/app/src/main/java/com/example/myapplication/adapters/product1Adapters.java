package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.myapplication.models.product1Model;

import java.util.List;


public class product1Adapters extends RecyclerView.Adapter<product1Adapters.ViewHolder> {

    private Context context;
    private List<product1Model>product1ModelList;

    public product1Adapters(Context context, List<product1Model> product1ModelList) {
        this.context = context;
        this.product1ModelList = product1ModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product1_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {


        Glide.with(context).load(product1ModelList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(product1ModelList.get(position).getName());
        holder.rating.setText(product1ModelList.get(position).getRating());
        holder.description.setText(product1ModelList.get(position).getDescription());
        holder.discount.setText(product1ModelList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",product1ModelList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  product1ModelList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name,description,rating,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_imgId);
            name = itemView.findViewById(R.id.pop_name_Id);
            description = itemView.findViewById(R.id.pop_des);
            rating = itemView.findViewById(R.id.pop_rating);
            discount = itemView.findViewById(R.id.pop_discount);

        }

    }
}
