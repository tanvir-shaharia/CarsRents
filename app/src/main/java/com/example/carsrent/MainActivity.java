package com.example.carsrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.carsrent.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    Animation topAnim,bottomAnim;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topAnim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.top_amim);
        bottomAnim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_anim);

        binding.car.setAnimation(topAnim);
        binding.nametext.setAnimation(bottomAnim);
        binding.tagnametext.setAnimation(bottomAnim);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (firebaseUser==null){
                    Intent intent =new Intent(MainActivity.this,Login.class);
                    Pair[] pairs=new Pair[2];
                    pairs[0]=new Pair<View,String>(binding.car,"car_image");
                    pairs[1]=new Pair<View,String>(binding.nametext,"text");
                    ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Apply activity transition
                        startActivity(intent,options.toBundle());
                        finish();
                    } else {
                        // Swap without transition
                        startActivity(intent);
                        finish();
                    }
                }else {
                    startActivity(new Intent(MainActivity.this,Dashbord.class));
                    finish();
                }
            }
        },3000);

    }
}