package com.example.myapplication;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.myapplication.adapters.MyCartAdapter;
import com.example.myapplication.adapters.ViewAllAdapter;
import com.example.myapplication.models.MyCartModel;
import com.example.myapplication.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyUploadedFragment extends Fragment {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;
    Toolbar toolbar1;
    ProgressBar progressBar;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_order, container, false);

        toolbar1 = root.findViewById(R.id.toolbar);

        progressBar = root.findViewById(R.id.progressbar_order);
        progressBar.setVisibility(View.VISIBLE);

        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.view_all_rec_order);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String userId;
        userId=auth.getInstance().getUid();

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getActivity(), viewAllModelList);
        recyclerView.setAdapter(viewAllAdapter);

        firestore.collection("AllProducts").whereEqualTo("userId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                    viewAllModelList.add(viewAllModel);
                    viewAllAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);


                }
            }
        });


        return root;
    }
}