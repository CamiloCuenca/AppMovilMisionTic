package com.example.myappejemstic;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myappejemstic.adapters.PaginasAdapter;
import com.example.myappejemstic.pojos.Users;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref_user = database.getReference("users").child(user.getUid());





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PaginasAdapter(this));

        TabLayout tabLayout= findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                {
                    switch (position){

                        case 0:{
                            tab.setText("Users");
                            tab.setIcon(R.drawable.ic_usuarios);
                            break;
                        }
                        case 1:{
                            tab.setText("chats");
                            tab.setIcon(R.drawable.ic_chasts);
                            break;
                        }
                        case 2:{
                            tab.setText("Solicitudes");
                            tab.setIcon(R.drawable.ic_solicitudes);
                            BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                            badgeDrawable.setBackgroundColor(
                                    ContextCompat.getColor(getApplicationContext(),R.color.colorAccent)
                            );
                            badgeDrawable.setVisible(true);
                            badgeDrawable.setNumber(1);
                            break;
                        }
                        case 3:{
                            tab.setText("Mis solicitudes");
                            tab.setIcon(R.drawable.ic_mis_solicitudes
                            );
                            break;
                        }



                    }



                }
            }
        });

        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable=tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);

            }
        });


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userunico();


    }//Fin del onecreate

    private void userunico() {

    ref_user.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if(!snapshot.exists()){
                Users uu = new Users(
                        user.getUid(),
                        user.getDisplayName(),
                        user.getEmail(),
                        user.getPhotoUrl().toString(),
                        "desconectado",
                        "2/12/2021",
                        "2:41",
                        0,
                        0);

                ref_user.setValue(uu);


            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_cerrar:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                                Toast.makeText(HomeActivity.this, "Cerrando sesion..", Toast.LENGTH_SHORT).show();
                                vamosalogin();
                            }
                        });
                
        }
        return super.onOptionsItemSelected(item);
    }

    private void vamosalogin() {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}