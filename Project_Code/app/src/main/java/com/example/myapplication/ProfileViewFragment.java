package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.activitices.HomeActivity;
import com.example.myapplication.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewFragment extends Fragment {

    TextView name,email,supply,collect,address,number;
    CircleImageView image;
    Button logout;
    FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;

    public ProfileViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_detaile,container,false);

        image=root.findViewById(R.id.nav_profile_details_img);
        name=root.findViewById(R.id.nav_profile_details_name);
        email=root.findViewById(R.id.nav_profile_details_email);
        supply=root.findViewById(R.id.nav_profile_details_supply);
        collect=root.findViewById(R.id.nav_profile_details_collect);
        logout=root.findViewById(R.id.nav_profile_details_logout);
        address=root.findViewById(R.id.nav_profile_details_address);
        number=root.findViewById(R.id.nav_profile_details_number);


        auth =FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();


        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        UserModel userModel=snapshot.getValue(UserModel.class);

                        Glide.with(getContext()).load(userModel.getProfileImg()).into(image);
                        name.setText(userModel.getName());
                        email.setText(userModel.getEmail());
                        address.setText(userModel.getAddress());
                        number.setText(userModel.getPhoneNumber());
//                        holder.description.setText(product1ModelList.get(position).getDescription());
//                        holder.discount.setText(product1ModelList.get(position).getDiscount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent =new Intent(getActivity(),HomeActivity.class);
                ((MainActivity)getActivity()).startActivity(intent);

            }
        });

        return  root;
    }
}