package com.example.karu_android_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfile extends AppCompatActivity {
    private ImageButton back;
    private ImageButton addProfilePic;
    private EditText fullName;
    private EditText Email;
    private EditText Dob;
    private EditText phnNum;
    private Button save;
    private CircleImageView view_uploadedImage;
    private static final int Pick_image_request = 1;
    private StorageTask mUploadTask;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore root = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("User_Images");

Uri imageUri;
    public static final String Key_name = "name";
    public static final String Key_dob = "dob";
    public static final String Key_phn = "phone";
    public static final String Key_email = "email";
String downloadUrl;


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
           // Picasso.get().load(imageUri).into(view_uploadedImage);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        back = findViewById(R.id.backBTN);


        fullName = (EditText) findViewById(R.id.editFullName);
        Email = (EditText) findViewById(R.id.editEmail);
        phnNum = (EditText) findViewById(R.id.editPhnNum);
        Dob = (EditText) findViewById(R.id.editDOB);
        save = findViewById(R.id.saveBTN);
        view_uploadedImage= findViewById(R.id.image_holder_cardView);
        addProfilePic= findViewById(R.id.addPhoto);
        Bundle bundle = getIntent().getExtras();
        addProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        //Extract the data…
        String intentName = bundle.getString("name_PreIntent");
        String intentPhn = bundle.getString("phn_PreIntent");
        String intentEmail = bundle.getString("email_PreIntent");
        String intentDob = bundle.getString("dob_PreIntent");

        fullName.setText(intentName);
        Email.setText(intentEmail);
        phnNum.setText(intentPhn);
        Dob.setText(intentDob);




        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        String name = fullName.getText().toString();
                                        String email = Email.getText().toString();
                                        String phn = phnNum.getText().toString();
                                        String dob = Dob.getText().toString();


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

                                                                    Map<String, Object> userInfo = new HashMap<>();
                                                                    userInfo.put(Key_name, name);
                                                                    userInfo.put(Key_phn, phn);
                                                                    userInfo.put(Key_dob, dob);
                                                                    userInfo.put(Key_email, email);
                                                                    userInfo.put("userImage", downloadUrl);

                                                                    DocumentReference documentReference = root.collection("Users").document(user.getUid()).collection("basic_info").document("name");

                                                                    documentReference.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        } else {

                                        }
                                    }
                                });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), settingFragment.class);
                finish();
            }
        });
    }
}