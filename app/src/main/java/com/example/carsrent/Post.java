package com.example.carsrent;

import static com.example.carsrent.Valid.inputvaliedfield;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carsrent.databinding.ActivityPostBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Post extends AppCompatActivity {

    ActivityPostBinding binding;
    Intent intent;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    Uri picUri;
    String user_uid;
    String name,picUrl,carmodel,rent_permont;
    ProgressDialog progressDialog;


//    private void sendpic() {
//        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        user_uid =firebaseUser.getUid();
//
//        storageReference = storageReference.child(user_uid).child("picture");
//
//        storageReference.putFile(picUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()) {
//                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            picUrl = String.valueOf(uri);
//                        }
//                    });
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                inputvaliedfield(Post.this, e.getMessage().toString());
//            }
//        });
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent=getIntent();
        name=intent.getStringExtra("name");

        progressDialog=new ProgressDialog(Post.this);
        progressDialog.setMessage("share post please wait,,,");
        databaseReference= FirebaseDatabase.getInstance().getReference("post");

        binding.select.setOnClickListener(view -> {
            ImagePicker.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(512)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start(12);

        });
        binding.postBtn.setOnClickListener(view -> {

            Toast.makeText(this, "Post shared", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Post.this,Dashbord.class));
            finish();
        });




    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            picUri = data.getData();
            binding.image.setImageURI(picUri);
        }
    }
}