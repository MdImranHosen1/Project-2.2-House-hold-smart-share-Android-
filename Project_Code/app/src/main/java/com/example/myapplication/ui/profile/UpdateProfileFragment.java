package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.activitices.PlacedOrderActivity;
import com.example.myapplication.activitices.loginActivity;
import com.example.myapplication.activitices.nav_create_account_messegeActivity;
import com.example.myapplication.activitices.registrationActivity;
import com.example.myapplication.models.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
//import com.example.myapplication.databinding.FragmentGalleryBinding;

public class UpdateProfileFragment extends Fragment {

    CircleImageView profileImg;
    EditText name, email, number, address;
    Button update;
    UserModel userModel;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();


        profileImg = root.findViewById(R.id.profile_image);
        name = root.findViewById(R.id.profile_nameId);
        email = root.findViewById(R.id.profile_emailId);
        number = root.findViewById(R.id.profile_phoneNumberId);
        address = root.findViewById(R.id.profile_AddressId);
        update = root.findViewById(R.id.update_profileId);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        userModel = snapshot.getValue(UserModel.class);
                        Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
                        String userEmail = userModel.getEmail();
                        String compEmail = "guest@gmail.com";
                        //Toast.makeText(getContext(), "email:"+userEmail, Toast.LENGTH_SHORT).show();
                        if (userEmail.equals(compEmail) == true) {
                            //root=inflater.inflate(R.layout.activity_nav_create_account_messege, container, false);
                            Intent intent = new Intent(getContext(), nav_create_account_messegeActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//        if (guestAccount = true) {
//            //root=inflater.inflate(R.layout.activity_nav_create_account_messege, container, false);
//            Intent intent = new Intent(getContext(), nav_create_account_messegeActivity.class);
//            startActivity(intent);
//
//        }

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });

        return root;
    }

    private void updateUserProfile() {


        //Toast.makeText(getContext(), "Error:"+name.getText().toString(), Toast.LENGTH_SHORT).show();

        String userId = auth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPhoneNumber = number.getText().toString();
        String userAddress = address.getText().toString();


        Map<String, Object> updates = new HashMap<>();
        if (!userName.isEmpty()) {
            updates.put("name", userName.trim());
        }
        if (!userAddress.isEmpty()) {
            updates.put("address", userAddress.trim());
        }
        if (!userEmail.isEmpty()) {
            updates.put("email", userEmail.trim());
        }
        if (!userPhoneNumber.isEmpty()) {
            updates.put("phoneNumber", userPhoneNumber.trim());
        }

        userModel.setName(userName);
        userModel.setEmail(userEmail);
        userModel.setAddress(userAddress);
        userModel.setPhoneNumber(userPhoneNumber);
        //Toast.makeText(getContext(), "Error"+userModel.getName().toString(), Toast.LENGTH_SHORT).show();
        databaseReference.updateChildren(updates);
        Toast.makeText(getContext(), "Update Complete", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null && data.getData()!=null) {
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);

            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());


            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());



                            Toast.makeText(getContext(), "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

}