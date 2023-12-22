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
import com.example.myapplication.activitices.NavCategoryActivity;
import com.example.myapplication.models.NavCat_product1Model;

import java.util.List;

public class NavCat_product1Adapter extends RecyclerView.Adapter<NavCat_product1Adapter.ViewHolder> {

    Context context;
    List<NavCat_product1Model> list;

    public NavCat_product1Adapter(Context context, List<NavCat_product1Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NavCat_product1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_product1_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavCat_product1Adapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.discount.setText(list.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, NavCategoryActivity.class);
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name ,description,discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.nav_cat_product1_img);
            name=itemView.findViewById(R.id.nav_cat_product1_name);
            description=itemView.findViewById(R.id.nav_cat_product1_description);
            discount=itemView.findViewById(R.id.nav_cat_product1_discount);
        }
    }
}
