package com.project.geekahr.ricoproyecto.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.geekahr.ricoproyecto.R;
import com.squareup.picasso.Picasso;

public class RestSingleActivity extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private ImageView mBlogSingleImage;
    private TextView mBlogSingleTitle;
    private TextView mBlogSingleDesc;
    private TextView mBlogPrice;
    private TextView mBlogMenu;
    private FloatingActionButton fabcall;
    private CollapsingToolbarLayout collapsin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_single);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mPost_key = getIntent().getExtras().getString("Blog_id");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mBlogSingleDesc = (TextView) findViewById(R.id.SingleBlogDesc);
        mBlogSingleImage = (ImageView) findViewById(R.id.SingleBlogImage);
        mBlogSingleTitle = (TextView) findViewById(R.id.SingleBlogTitle);
        mBlogPrice = (TextView) findViewById(R.id.SingleBlogPrice);
        mBlogMenu = (TextView) findViewById(R.id.SingleBlogMenu);
        fabcall = (FloatingActionButton) findViewById(R.id.btnllamar);
        collapsin = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);



        //Toast.makeText(RestSingleActivity.this, post_key, Toast.LENGTH_LONG).show();
        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();
                String post_price = (String) dataSnapshot.child("latitud").getValue();
                String post_menu = (String) dataSnapshot.child("menu").getValue();
                final String post_num = (String) dataSnapshot.child("phone").getValue();
                String post_autor = (String) dataSnapshot.child("horario").getValue();

                mBlogSingleTitle.setText(post_title);
                mBlogSingleDesc.setText(post_desc);
                mBlogPrice.setText("Precio " + post_price+" MXM");
                mBlogMenu.setText(post_menu);

                fabcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialContactPhone(post_num);
                    }
                });

                collapsin.setTitle(post_autor);


                Picasso.with(RestSingleActivity.this).load(post_image).into(mBlogSingleImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
