package com.example.android.firebaseintro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.firebaseintro.Model.Journal;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.button;
import static android.os.Build.VERSION_CODES.M;

public class PostActivity extends AppCompatActivity {

    // private DatabaseReference mDataref;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private ProgressDialog eProg;
    //  private StorageReference eStorage;

    private EditText eTitle;
    private EditText eDesc;
    //  private ImageButton eImage;
    private Button eSubmit;
    // private Uri eUri;
    //  private static final int GALLERY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        eProg = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Journal Database");
        //  eStorage = FirebaseStorage.getInstance().getReference();
        //mDataref = mDatabase.getReference().child("Journal Database");
        //  mDataref.keepSynced(true);

        eTitle = (EditText) findViewById(R.id.journalTitle);
        eDesc = (EditText) findViewById(R.id.journalEntry);
        // eImage = (ImageButton) findViewById(R.id.postImage);
        eSubmit = (Button) findViewById(R.id.saveJournalEntry);

       /* eImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryItent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryItent.setType("Image/*");
                startActivityForResult(galleryItent, GALLERY_CODE);
            }
        });*/

        eSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //post to database
                startPosting();
            }
        });

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK)
        {
            eUri = data.getData();
            eImage.setImageURI(eUri);
        }
    }*/

    private void startPosting() {

        eProg.setMessage("Saving in Journal");
        eProg.show();

        final String jTitle = eTitle.getText().toString().trim();
        final String jDesc = eDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(jTitle) && !TextUtils.isEmpty(jDesc))// && eUri != null)
        {
            //start uploading
            //StorageReference filePath = eStorage.child("Journal Images").child(eUri.getLastPathSegment());
            // filePath.putFile(eUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //   @Override
            //  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            //     Uri downloadUrl = taskSnapshot.getDownloadUrl();
            DatabaseReference newData = mDatabase.push();


            Map<String, String> dataToSave = new HashMap<>();
            dataToSave.put("title", jTitle);
            dataToSave.put("desc", jDesc);
            //dataToSave.put("image", downloadUrl.toString());
            dataToSave.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            dataToSave.put("userId", mUser.getUid());

            newData.setValue(dataToSave);


            eProg.dismiss();
            startActivity(new Intent(PostActivity.this, HomeActivity.class));
            Toast.makeText(PostActivity.this, "Journal Entry Saved", Toast.LENGTH_LONG).show();
            finish();
        }
    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
