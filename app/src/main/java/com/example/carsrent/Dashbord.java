package com.example.carsrent;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsrent.adapter.PostAdapter;
import com.example.carsrent.databinding.ActivityDashbordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.View;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class Dashbord extends AppCompatActivity {

    Toolbar toolbar;
    List<Post_Model> post_models;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference,reference1;
    ActivityDashbordBinding binding;

    String picture,name,time,car_model,rentper;

    @Override
    protected void onStart() {
        super.onStart();
        binding.post.setOnClickListener(view -> {
            Dexter.withContext(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    ).withListener(new MultiplePermissionsListener() {
                        @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()){

                                Intent intent=new Intent(Dashbord.this,Post.class);
                                intent.putExtra("name",name);
                                startActivity(intent);

                            }else if (report.isAnyPermissionPermanentlyDenied()){
                                Toast.makeText(Dashbord.this, "Please allow all permission", Toast.LENGTH_SHORT).show();;

                            }

                        }
                        @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        }
                    }).check();

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();
        String user_uid=firebaseAuth.getUid();

        reference= FirebaseDatabase.getInstance().getReference("user");
        reference.child(user_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User_Helper user=snapshot.getValue(User_Helper.class);
                if (user != null) {
                    name=user.fullName;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        post_models=new ArrayList<>();
        post_models.add(new Post_Model("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScGqPNodctpVUTrktxxSO9wjHu8GDDT-Bb8A&usqp=CAU","MD Tanvir","12,25Pm","Marcities",""));
        post_models.add(new Post_Model("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScGqPNodctpVUTrktxxSO9wjHu8GDDT-Bb8A&usqp=CAU","MD Tanvir","12,25Pm","Marcities",""));
        post_models.add(new Post_Model("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScGqPNodctpVUTrktxxSO9wjHu8GDDT-Bb8A&usqp=CAU","MD Tanvir","12,25Pm","Marcities",""));

        post_models.add(new Post_Model(picture,name,time,car_model,rentper));
        //in






        PostAdapter adapter =new PostAdapter(Dashbord.this,post_models);
        binding.recycler.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(Dashbord.this,UserPfofile.class));
                break;
            case R.id.logout:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
            firebaseAuth.signOut();
            startActivity(new Intent(Dashbord.this,Login.class));
            Toast.makeText(this, "log out sucessfull", Toast.LENGTH_SHORT).show();
            finish();
    }

    @Override
    public void onBackPressed() {
        showalert();
    }

    public void showalert(){

        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.car);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure to Exit");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog =builder.create();
        dialog.show();
    }
}
