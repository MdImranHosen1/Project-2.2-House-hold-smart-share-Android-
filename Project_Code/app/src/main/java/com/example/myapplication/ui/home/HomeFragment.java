package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ViewAllAdapter;
import com.example.myapplication.adapters.product1Adapters;
import com.example.myapplication.adapters.product2Adapters;
import com.example.myapplication.adapters.product3Adapters;
import com.example.myapplication.models.ViewAllModel;
import com.example.myapplication.models.product1Model;
import com.example.myapplication.models.product2Model;
import com.example.myapplication.models.product3Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView product1Rec,product2Rec,product3Rec,product4Rec;
    FirebaseFirestore db;



    //////Search view
    EditText search_box;
    List<ViewAllModel>viewAllModelList;
    private  RecyclerView recyclerViewSearch;
    ViewAllAdapter viewAllAdapter;

    //product 1 item
    List<product1Model>product1ModelList;
    product1Adapters product1Adapters;


    //product 2 item
    List<product2Model>product2ModelList;
    product2Adapters product2Adapters;

    // product 3 item
    List<product3Model>product3ModelList;
    product3Adapters product3Adapters;

    //product 4 item
    List<ViewAllModel>viewAllModelLists;
    ViewAllAdapter viewAllAdapters;






    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        db=FirebaseFirestore.getInstance();

        product1Rec =root.findViewById(R.id.pop_rec_id1);
        product2Rec =root.findViewById(R.id.pop_rec_id2);
        product3Rec =root.findViewById(R.id.pop_rec_id3);
        product4Rec =root.findViewById(R.id.pop_rec_id4);


        scrollView =root.findViewById(R.id.scroll_ViewId);
        progressBar =root.findViewById(R.id.home_fragment_progressbar_id);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);


        //product1
        product1Rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        product1ModelList =new ArrayList<>();
        product1Adapters=new product1Adapters(getActivity(),product1ModelList);
        product1Rec.setAdapter(product1Adapters);

        db.collection("product1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                product1Model product1Model=document.toObject(product1Model.class);
                                product1ModelList.add(product1Model);
                                product1Adapters.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        //product 2
        product2Rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        product2ModelList =new ArrayList<>();
        product2Adapters=new product2Adapters(getActivity(),product2ModelList);
        product2Rec.setAdapter(product2Adapters);

        db.collection("product2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                product2Model product2Model=document.toObject(product2Model.class);
                                product2ModelList.add(product2Model);
                                product2Adapters.notifyDataSetChanged();

                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        //product 3
        product3Rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        product3ModelList =new ArrayList<>();
        product3Adapters=new product3Adapters(getActivity(),product3ModelList);
        product3Rec.setAdapter(product3Adapters);

        db.collection("product3")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                product3Model product3Model=document.toObject(product3Model.class);
                                product3ModelList.add(product3Model);
                                product3Adapters.notifyDataSetChanged();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //product 4
        product4Rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewAllModelLists =new ArrayList<>();
        viewAllAdapters=new ViewAllAdapter(getActivity(),viewAllModelLists);
        product4Rec.setAdapter(viewAllAdapters);
        db.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        ViewAllModel product4Models=document.toObject(ViewAllModel.class);
                        viewAllModelLists.add(product4Models);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        /////Search View
        recyclerViewSearch=root.findViewById(R.id.search_rec);
        search_box=root.findViewById(R.id.home_search_barId);
        viewAllModelList=new ArrayList<>();
        viewAllAdapter=new ViewAllAdapter(getContext(),viewAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().isEmpty())
                {
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }
                else
                {
                    searchProduct(editable.toString());
                }
            }
        });
        return root;
    }

    private void searchProduct(String type) {
        if(!type.isEmpty())
        {
            db.collection( "AllProducts").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()&& task.getResult()!=null)
                            {
                                  viewAllModelList.clear();
                                  viewAllAdapter.notifyDataSetChanged();

                                  for(DocumentSnapshot doc:task.getResult().getDocuments())
                                  {
                                      ViewAllModel viewAllModel=doc.toObject(ViewAllModel.class);
                                      viewAllModelList.add(viewAllModel);
                                      viewAllAdapter.notifyDataSetChanged();
                                  }
                            }

                        }
                    });
        }
    }

}