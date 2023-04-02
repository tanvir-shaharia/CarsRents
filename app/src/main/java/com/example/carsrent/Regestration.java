package com.example.carsrent;

import static com.example.carsrent.Valid.inputvaliedfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.carsrent.databinding.ActivityRegestrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Regestration extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ProgressDialog progressDialog;
    String user_uid;
    ActivityRegestrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegestrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(Regestration.this);
        progressDialog.setMessage("Please Wait...");
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        binding.donSignup.setOnClickListener(view -> {
            startActivity(new Intent(Regestration.this,Login.class));
            finish();
        });

        binding.signup.setOnClickListener(view -> {


            reference =firebaseDatabase.getReference("user");
            String fullName=binding.fullname.getText().toString();
            String userName=binding.username.getText().toString();
            String phone=binding.Phone.getText().toString();
            String email=binding.email.getText().toString();
            String password=binding.password.getText().toString();
            String re_password=binding.retypePass.getText().toString();


            if (fullName.equals("")){
                inputvaliedfield(Regestration.this,"Name feield can't be Empty");
            }else if (email.equals("")){
                inputvaliedfield(Regestration.this,"Email feield can't be Empty");
            }else if (userName.equals("")){
                inputvaliedfield(Regestration.this,"Email feield can't be Empty");
            }else if (phone.equals("")){
                inputvaliedfield(Regestration.this,"Phone feield can't be Empty");
            }else if (password.equals("")){
                inputvaliedfield(Regestration.this,"Password feield can't be Empty");
            }else if (password.length()<6){
                inputvaliedfield(Regestration.this,"Password must be 6 Charecters!");
            }
            else if (re_password.equals("")){
                inputvaliedfield(Regestration.this,"Please fill re-type Password");
            }else {
                progressDialog.show();

                //user create
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            //store data firebasedatabase
                          //  String user_uid= firebaseUser.getUid();
                            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                            String user_uid=firebaseUser.getUid();
                           // User_Helper user_helper= new User_Helper(fullName,userName,phone,email,password,user_uid);
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("fullName",fullName);
                            map.put("userName",userName);
                            map.put("phone",phone);
                            map.put("email",email);
                            map.put("password",password);
                            map.put("user_uid",user_uid);

                            reference.child(user_uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(Regestration.this, "Regestration Sucessfull", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Regestration.this,Dashbord.class));
                                        finish();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Regestration.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Regestration.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}