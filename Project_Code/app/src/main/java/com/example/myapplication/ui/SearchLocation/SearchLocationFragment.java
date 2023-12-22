package com.example.myapplication.ui.SearchLocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.NavCat_product1Adapter;
import com.example.myapplication.adapters.ViewAllAdapter;
import com.example.myapplication.models.NavCat_product1Model;
import com.example.myapplication.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
//import com.example.myapplication.databinding.FragmentSlideshowBinding;

public class SearchLocationFragment extends Fragment {

    AutoCompleteTextView itemDistrict,itemSubDistrict;
    ArrayAdapter<String> arrayAdapterItem;
    String itemSubDistricts,itemDistricts;
    Button search;

    FirebaseFirestore db;
    RecyclerView recyclerView1;
    List<ViewAllModel>cat_product1ModelList;
    ViewAllAdapter navCat_product1Adapter;
    ProgressBar progressBar;

    String[] district={"Dhaka","Faridpur","Gazipur","Gopalganj","Kishoreganj","Madaripur","Manikganj","Munshiganj","Narayanganj","Narsingdi","Rajbari","Shariatpur","Tangail"};
    String[] subDistrict={"Baikunthpur","Barauli","Bhorey","Bijaipur","Gopalganj","Hathua","Katiya","Kuchaikote","Manjha","Pach Deuri","Phulwaria","Sidhwalia","Thawe","Uchkagaon"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root =inflater.inflate(R.layout.fragment_category,container,false);

        recyclerView1=root.findViewById(R.id.cat_rec_view);
        recyclerView1.setVisibility(View.GONE);
        db=FirebaseFirestore.getInstance();
        progressBar =root.findViewById(R.id.progressbar);
        search=root.findViewById(R.id.search_button_id);
        progressBar.setVisibility(View.GONE);

        //itemDistrict and subDistrict
        //item district
        itemDistrict =root.findViewById(R.id.search_district_id);
        arrayAdapterItem=new ArrayAdapter<String >(getContext(),R.layout.item_type_list,district);

        itemDistrict.setAdapter(arrayAdapterItem);

        itemDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemDistricts=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), "Item:"+item, Toast.LENGTH_SHORT).show();
            }
        });
        //item subDistrict
        itemSubDistrict=root.findViewById(R.id.search_sub_district_id);
        arrayAdapterItem=new ArrayAdapter<String >(getContext(),R.layout.item_type_list,subDistrict);

        itemSubDistrict.setAdapter(arrayAdapterItem);

        itemSubDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemSubDistricts=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), "Item:"+item, Toast.LENGTH_SHORT).show();
            }
        });
        //end

        //product1
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        cat_product1ModelList =new ArrayList<>();
        navCat_product1Adapter=new ViewAllAdapter(getActivity(),cat_product1ModelList);
        recyclerView1.setAdapter(navCat_product1Adapter);



       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Toast.makeText(getContext(), "dis "+itemSubDistricts, Toast.LENGTH_SHORT).show();
               if(itemSubDistricts!=null && itemDistricts!=null)
               {
                   progressBar.setVisibility(View.VISIBLE);
                   db.collection("AllProducts").whereEqualTo("subDistrict",itemSubDistricts).whereEqualTo("district",itemDistricts)
                           .get()
                           .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                   if(task.isSuccessful())
                                   {
                                       for(QueryDocumentSnapshot document: task.getResult())
                                       {
                                           ViewAllModel navCat_product1Model=document.toObject(ViewAllModel.class);
                                           cat_product1ModelList.add(navCat_product1Model);
                                           navCat_product1Adapter.notifyDataSetChanged();
                                           progressBar.setVisibility(View.GONE);
                                           recyclerView1.setVisibility(View.VISIBLE);
                                       }
                                   }
                                   else
                                   {
                                       Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                                       progressBar.setVisibility(View.GONE);
                                       recyclerView1.setVisibility(View.VISIBLE);

                                   }
                               }
                           });

               }
           }
       });
        return root;
    }

}