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

import com.example.carsrent.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(Login.this);
        progressDialog.setMessage("Cheaking Info...");

        binding.donSignup.setOnClickListener(view -> {
            Intent intent =new Intent(Login.this,Regestration.class);
            Pair[] pairs=new Pair[7];
            pairs[0]=new Pair<View,String>(binding.carPic,"car_image");
            pairs[1]=new Pair<View,String>(binding.welcomeText,"text");
            pairs[2]=new Pair<View,String>(binding.tag,"text_tag");
            pairs[3]=new Pair<View,String>(binding.email,"email_trm");
            pairs[4]=new Pair<View,String>(binding.password,"pass_trm");
            pairs[5]=new Pair<View,String>(binding.loginBtn,"button_trm");
            pairs[6]=new Pair<View,String>(binding.donSignup,"dont_trm");
            ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Apply activity transition
                startActivity(intent,options.toBundle());
                finish();
            } else {
                // Swap without transition
                startActivity(intent);
                finish();
            }
        });
        binding.loginBtn.setOnClickListener(view -> {


            String email=binding.email.getText().toString();
            String password=binding.password.getText().toString();
            if (email.equals("")){
                inputvaliedfield(Login.this,"Email feield can't be Empty");
            }else if (password.equals("")){
                inputvaliedfield(Login.this,"Password feield can't be Empty");
            }else {
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Login Sucess", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,Dashbord.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }


        });
    }
}