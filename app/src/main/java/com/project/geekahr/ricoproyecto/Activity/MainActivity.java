package com.project.geekahr.ricoproyecto.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.geekahr.ricoproyecto.Blog;
import com.project.geekahr.ricoproyecto.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private long backPressedTime = 0;
    private FloatingActionButton floatingActionButton;
    private CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");


        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        //TextView txtname = (TextView) findViewById(R.id.txtNameUser);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);


        ImageView profilePic = (ImageView) findViewById(R.id.imgProfileUser);

        //String id_user = Profile.getCurrentProfile().getId();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            //String id = u();

            Profile.getCurrentProfile().getId();

            int dimensionPixelSize = getResources().getDimensionPixelSize(com.facebook.R.dimen.com_facebook_profilepictureview_preset_size_large);
            Profile profile = Profile.getCurrentProfile();
            Uri profilePictureUri = ImageRequest.getProfilePictureUri(profile.getId(), dimensionPixelSize , dimensionPixelSize );



            String fbId="100007128337241";


            /*Glide.with(this).load(profilePictureUri)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .crossFade()
                    .into(profilePic);*/

            Picasso.with(this)
                    .load(profilePictureUri)
                    .transform(new CropCircleTransformation())
                    .resize(120, 120)
                    .into(profilePic);






         /*   Picasso.with(this)
                    .load("https://graph.facebook.com/v2.2/" + id + "/picture?height=120&type=normal") //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(120, 120)
                    .into(profilePic);*/



            collapsingToolbarLayout.setTitle("Hola "+name);
            //txtname.setText(name);
            //txtemail.setText(email);
            estaConectado();


        }else{
            goLoginScreen();
        }

        //FragmentManager fm = getFragmentManager();

       // fm.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();

    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, final int position) {
                final String  post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImagen(getApplicationContext(),model.getImage());
                viewHolder.setHorario(model.getHorario());
                viewHolder.setPrice("Precio: "+model.getLatitud() + " MXM");

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, post_key, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, RestSingleActivity.class);
                        i.putExtra("Blog_id",post_key);
                        startActivity(i);
                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class  BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        public void setImagen(Context ctx, String img){
            ImageView post_img = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(img).into(post_img);
        }
        public void setHorario(String horario){
            TextView post_autor = (TextView) mView.findViewById(R.id.post_author);
            post_autor.setText(horario);
        }

        public void setPrice(String latitud){
            TextView post_price = (TextView) mView.findViewById(R.id.post_price);
            post_price.setText(latitud);
        }


    }


    //bo funciona
    //Login
    public void  goLoginScreen() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(intent);
    }



    @Override     public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {
            backPressedTime = t;
            Toast.makeText(this, "Presiona atras nuevamente para salir",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();       // bye//
        }
    }


    protected Boolean estaConectado(){
        if(conectadoWifi()){
            showAlertDialog(MainActivity.this, "Conexion a Internet",
                    "Tu Dispositivo tiene Conexion a Wifi. ¡Puedes Navegar sin Problemas!", true);
            return true;
        }else{
            if(conectadoRedMovil()){
                showAlertDialog(MainActivity.this, "Conexion a Internet",
                        "Tu Dispositivo tiene Conexión,¡Cuidado son tus datos!", true);
                return true;
            }else{
                showAlertDialog(MainActivity.this, "Conexion a Internet",
                        "Tu Dispositivo no tiene Conexion a Internet. ¡Conéctate a una Red!", false);
                return false;
            }
        }
    }


    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
    protected Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.itemlogout) {
            goLoginScreen();

        }

        return super.onOptionsItemSelected(item);
    }


}
