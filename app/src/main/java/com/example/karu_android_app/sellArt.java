package com.example.karu_android_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class sellArt extends AppCompatActivity {

    private ImageButton back;
    private EditText title, size, category, description, price;
    private Button addPhoto;
    private Button takePhoto;
    private ImageView view_uploadedImage;
    private Uri imageUri;
    public static final String Key_title = "title";
    public static final String Key_size = "size";
    public static final String Key_category = "category";
    public static final String Key_description = "description";
    public static final String Key_price = "price";
    private static final int Pick_image_request = 1;
    private StorageTask mUploadTask;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore root = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Post Images");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_art);

        back = findViewById(R.id.backBTN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title = findViewById(R.id.post_title);
        size = findViewById(R.id.post_size);
        category = findViewById(R.id.post_category);
        description = findViewById(R.id.post_description);
        price = findViewById(R.id.post_price);
        addPhoto = findViewById(R.id.addPhotoBTN);
        view_uploadedImage = findViewById((R.id.showImage));

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Pick_image_request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Pick_image_request && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(view_uploadedImage);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    String downloadUrl;


    public void publish(View view) {
        String post_title = title.getText().toString();
        int post_size = Integer.parseInt(size.getText().toString());
        String post_category = category.getText().toString();
        String post_description = description.getText().toString();
        double post_price = Double.parseDouble(price.getText().toString());

        //uploading image to fireStorage

        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                            System.out.println(downloadUrl);

                            Map<String, Object> postInfo = new HashMap<>();
                            postInfo.put(Key_title, post_title);
                            postInfo.put(Key_size, post_size);
                            postInfo.put(Key_category, post_category);
                            postInfo.put(Key_description, post_description);
                            postInfo.put(Key_price, post_price);
                            postInfo.put("postAuthor", user.getUid());
                            postInfo.put("imageUrl", downloadUrl);

                            DocumentReference documentReference = root.collection("Posts").document();
                            documentReference.set(postInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                            root.collection("Users").document(user.getUid()).collection("posts").document(post_title).set(postInfo);
                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();

        }


    }

}
