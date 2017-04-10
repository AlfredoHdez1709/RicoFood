package com.project.geekahr.ricoproyecto.Activity;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.geekahr.ricoproyecto.R;

public class PostActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private EditText mPostDireccion;
    private EditText mPostNumero;
    private EditText mPosthorario;
    private EditText mPostMenu;
    private EditText mPostLatitud;
    private EditText getmPostLongitud;

    private Button mPostSend;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    private DatabaseReference mDataBases;
    private ProgressDialog mPosgress;

    private static final int GALLERY_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDataBases = FirebaseDatabase.getInstance().getReference().child("Blog");

        mSelectImage = (ImageButton) findViewById(R.id.imageSelect);
        mPostTitle = (EditText) findViewById(R.id.titleField);
        mPostDesc = (EditText) findViewById(R.id.descrField);
        mPostDireccion = (EditText) findViewById(R.id.direccionField);
        mPostNumero = (EditText) findViewById(R.id.phoneField);
        mPosthorario = (EditText) findViewById(R.id.HorarioField);
        mPostMenu = (EditText) findViewById(R.id.MenuField);
        mPostLatitud = (EditText) findViewById(R.id.Latitud);
        getmPostLongitud = (EditText) findViewById(R.id.Longitud);
        mPostSend = (Button) findViewById(R.id.send);


        mPosgress = new ProgressDialog(this);

       mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        mPostSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startposting();
            }
        });

    }

    private void startposting() {
        mPosgress.setMessage("Publicando...");
        mPosgress.show();
        final String title_val = mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();
        final String direccion_val = mPostDireccion.getText().toString().trim();
        final String numero_val = mPostNumero.getText().toString().trim();
        final String horario_val = mPosthorario.getText().toString().trim();
        final String menu_val = mPostMenu.getText().toString().trim();
        final String latitud_val = mPostLatitud.getText().toString().trim();
        final String longitud_val = getmPostLongitud.getText().toString().trim();




        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && mImageUri != null)
        {
            StorageReference filepath = mStorage.child("Blog_imagenes").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri dowloadUrl= taskSnapshot.getDownloadUrl();
                    DatabaseReference NewPost = mDataBases.push();
                    NewPost.child("title").setValue(title_val);
                    NewPost.child("desc").setValue(desc_val);
                    NewPost.child("direc").setValue(direccion_val);
                    NewPost.child("phone").setValue(numero_val);
                    NewPost.child("horario").setValue(horario_val);
                    NewPost.child("menu").setValue(menu_val);
                    NewPost.child("latitud").setValue(latitud_val);
                    NewPost.child("longitud").setValue(longitud_val);

                    NewPost.child("image").setValue(dowloadUrl.toString());
                    mPosgress.dismiss();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
             mImageUri = data.getData();
            mSelectImage.setImageURI(mImageUri);
        }

    }
}
