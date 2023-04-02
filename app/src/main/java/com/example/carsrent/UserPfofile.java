package com.example.carsrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carsrent.databinding.ActivityUserPfofileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserPfofile extends AppCompatActivity {
    ActivityUserPfofileBinding binding;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String user_uid;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserPfofileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            user_uid=firebaseUser.getUid();
            phone=firebaseUser.getPhoneNumber();

        }

        reference= FirebaseDatabase.getInstance().getReference("user");
        reference.child(user_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               User_Helper user=snapshot.getValue(User_Helper.class);
                if (user != null) {

                    binding.fullname.setText(user.getFullName());
                    binding.userName.setText(user.getUserName());
                    binding.email.setText(user.getEmail());
                    binding.phone.setText(user.getPhone());
                    binding.NameText.setText(user.getFullName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserPfofile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ProgressDialog progressDialog=new ProgressDialog(UserPfofile.this);
        progressDialog.setMessage("updating info...");

        binding.update.setOnClickListener(view -> {
            progressDialog.show();
            HashMap<String,Object> map = new HashMap<>();
            map.put("fullName",binding.fullname.getText().toString());
            map.put("userName",binding.userName.getText().toString());
            map.put("email",binding.email.getText().toString());
            map.put("phone",binding.phone.getText().toString());

            reference.child(user_uid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        reference.child(user_uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                User_Helper user1=snapshot.getValue(User_Helper.class);
                                if (user1 != null) {

                                    binding.fullname.setText(user1.getFullName());
                                    binding.userName.setText(user1.getUserName());
                                    binding.email.setText(user1.getEmail());
                                    binding.phone.setText(user1.getPhone());
                                    binding.NameText.setText(user1.getFullName());
                                    progressDialog.dismiss();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(UserPfofile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                        Toast.makeText(UserPfofile.this, "update sucessfull", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UserPfofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}