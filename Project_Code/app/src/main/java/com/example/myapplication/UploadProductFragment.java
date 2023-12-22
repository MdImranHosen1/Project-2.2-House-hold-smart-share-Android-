package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.models.NewProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UploadProductFragment extends Fragment {


    AutoCompleteTextView itemDistrict,itemSubDistrict;
    String itemSubDistricts,itemDistricts;

    AutoCompleteTextView itemType1,rating;
    ArrayAdapter<String> arrayAdapterItem;
    EditText name,description,address;
    String itemRatings,itemTypes;
    NewProductModel newProductModel;
    Button uploadButton;
    ImageButton imageButton;
    boolean uploadSuccess=false,imageUpload=false;
    FirebaseFirestore firestore;
    StorageReference mStorageRef;
    FirebaseAuth auth;
    String imageRef;
    public Uri imageUri;
    ImageView imageView;



    String[] item={"Man Cloth","Female Cloth","Electronics","Education","Furniture","Other"};
    String[] itemRating={"1","2","3","4","5"};
    String[] district={"Dhaka","Faridpur","Gazipur","Gopalganj","Kishoreganj","Madaripur","Manikganj","Munshiganj","Narayanganj","Narsingdi","Rajbari","Shariatpur","Tangail"};
    String[] subDistrict={"Baikunthpur","Barauli","Bhorey","Bijaipur","Gopalganj","Hathua","Katiya","Kuchaikote","Manjha","Pach Deuri","Phulwaria","Sidhwalia","Thawe","Uchkagaon"};




    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_products, container, false);

        newProductModel =new NewProductModel();

        firestore=FirebaseFirestore.getInstance();
        name=root.findViewById(R.id.new_product_name);
        description=root.findViewById(R.id.new_product_description);
        uploadButton=root.findViewById(R.id.upload_new_product);
        imageButton=root.findViewById(R.id.imageButton);
        imageView =root.findViewById(R.id.imageView7);
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        auth=FirebaseAuth.getInstance();

        String userId=auth.getInstance().getUid();

        //item list
        itemType1 =root.findViewById(R.id.nav_new_product_item_list);
        arrayAdapterItem=new ArrayAdapter<String >(getContext(),R.layout.item_type_list,item);

        itemType1.setAdapter(arrayAdapterItem);

        itemType1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemTypes=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), "Item:"+item, Toast.LENGTH_SHORT).show();
            }
        });
        //item rating
        rating=root.findViewById(R.id.nav_new_product_item_rating);
        arrayAdapterItem=new ArrayAdapter<String >(getContext(),R.layout.item_type_list,itemRating);

        rating.setAdapter(arrayAdapterItem);

        rating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemRatings=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), "Item:"+item, Toast.LENGTH_SHORT).show();
            }
        });
        //Image choose
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });

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



        //other
        uploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String pName=name.getText().toString();
                String pType=itemTypes;
                String pDescription=description.getText().toString();
                String pDistrict=itemDistrict.getText().toString();
                String pSubDistrict=itemSubDistrict.getText().toString();
                String pRating=itemRatings;


                if(!pName.isEmpty() && !pDescription.isEmpty() && !pType.isEmpty() && !pRating.isEmpty() && !pDistrict.isEmpty() && !pSubDistrict.isEmpty())
                {
                    newProductModel.setName(pName);
                    newProductModel.setType(pType);
                    newProductModel.setDescription(pDescription);
                    newProductModel.setDistrict(pDistrict);
                    newProductModel.setSubDistrict(pSubDistrict);
                    newProductModel.setRating(pRating);
                    uploadSuccess=true;
                    ImageUploader();
                }


                if(uploadSuccess&& imageUpload)
                {
                    Map<String,Object>user=new HashMap<>();
                    user.put("name",pName);
                    user.put("description",pDescription);
                    user.put("type",pType);
                    user.put("district",pDistrict);
                    user.put("subDistrict",pSubDistrict);
                    user.put("rating",pRating);
                    user.put("img_url",imageRef);
                    user.put("userId",userId);



                    firestore.collection("AllProducts")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getContext(), "Successfully Upload", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getContext(), "error id:"+user.toString()+"+"+user, Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getContext(),MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(getContext(), "Some Filled Are Empty Or Uploading Image!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return root;
    }

    private  String getExtension(Uri uri)
    {
        ContentResolver cr=getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void ImageUploader() {


        StorageReference Ref=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imageUri));

        Ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                imageRef=uri.toString();
                                imageUpload=true;
                                //Toast.makeText(getContext(), "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error :"+e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error :"+e, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void Filechooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!=null && data.getData()!=null)
        {
            imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
        else
        {
            imageUpload=false;
            Toast.makeText(getContext(), "Select Product Image", Toast.LENGTH_SHORT).show();
        }
    }


}