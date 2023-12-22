package com.example.myapplication.activitices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.ViewAllModel;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DetailedActivity extends AppCompatActivity  {

    TextView quantity;
    int totalQuantity=1;
    int totalPrice=0;
    ImageView detailedImg;
    TextView name,rating,description,district,subDistrict;
    String ownerId;
    Button addToCart,deleteToCart;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    ViewAllModel viewAllModel =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firestore =FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        final  Object object=getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModel)
        {
            viewAllModel=(ViewAllModel) object;
        }
        detailedImg =findViewById(R.id.detailed_img);

        name =findViewById(R.id.detailed_name);
        rating =findViewById(R.id.detailed_rating);
        description=findViewById(R.id.detailed_description);
        district=findViewById(R.id.detailed_district);
        subDistrict=findViewById(R.id.detailed_subDistrict);

        if(viewAllModel!=null)
        {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            name.setText(viewAllModel.getName());
            district.setText(viewAllModel.getDistrict());
            subDistrict.setText(viewAllModel.getSubDistrict());
            ownerId=viewAllModel.getUserId();
        }

        addToCart =findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });
        deleteToCart =findViewById(R.id.delete_to_cart);
        deleteToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedToCart();
            }
        });



//        deleteToCart =findViewById(R.id.delete_to_cart);
//        addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deletedToCart();
//            }
//        });



    }

    private void deletedToCart() {
        firestore.collection("AllProducts").document();

    }

    private void addedToCart() {

        String saveCurrentDate ,saveCurrentTime;
        Calendar calForDate=Calendar.getInstance();

        SimpleDateFormat currentDate =new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

        //Insert in firebase


        final HashMap<String,Object> cartMap =new HashMap<>();
        final HashMap<String,Object> cartMap2 =new HashMap<>();

        String userId1;
        userId1=auth.getInstance().getUid().toString();

        if(userId1.compareTo(ownerId)!=0)
        {
            //order
            cartMap.put("productName",viewAllModel.getName());
            cartMap.put("productPrice",viewAllModel.getPrice());
            cartMap.put("currentDate",saveCurrentDate);
            cartMap.put("currentTime",saveCurrentTime);
            cartMap.put("totalQuantity",totalQuantity);
            cartMap.put("requestId",userId1);

            firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                    .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {

                    Toast.makeText(DetailedActivity.this, "Your request is sent to Owner of this product.", Toast.LENGTH_SHORT).show();
                }
            });
            //request
            cartMap.put("productName",viewAllModel.getName());
            cartMap.put("productPrice",viewAllModel.getPrice());
            cartMap.put("currentDate",saveCurrentDate);
            cartMap.put("currentTime",saveCurrentTime);
            cartMap.put("totalQuantity",totalQuantity);
            cartMap2.put("requestId",userId1);
            cartMap2.put("ownerId",ownerId);
            Toast.makeText(this, "owner"+ownerId.toString(), Toast.LENGTH_SHORT).show();
            firestore.collection("CurrentUser").document(ownerId.toString())
                    .collection("AddToRequest").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {

                    Toast.makeText(DetailedActivity.this, "Your request is sent to Owner of this product.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        else
        {
            Toast.makeText(DetailedActivity.this, "You can't request your own product.", Toast.LENGTH_SHORT).show();
        }
    }
}