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
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myappejemstic.adapters.PaginasAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {




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


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




    }//Fin del onecreate


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