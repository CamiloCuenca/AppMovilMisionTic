package com.example.myappejemstic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myappejemstic.R;
import com.example.myappejemstic.pojos.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class adapterUsuarios extends RecyclerView.Adapter<adapterUsuarios.viewHolderAdapter> {
    List<Users> usersList;
    Context context;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public adapterUsuarios(List<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios,parent,false);
        viewHolderAdapter holder = new viewHolderAdapter(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapter holder, int position) {

        Users userss = usersList.get(position);
        Glide.with(context).load(userss.getFoto()).into(holder.im_user);
        holder.tv_usuario.setText(userss.getNombre());

        if(userss.getId().equals(user.getUid())){
            holder.cardView.setVisibility(View.GONE);
        }else{
            holder.cardView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class viewHolderAdapter extends RecyclerView.ViewHolder {

        TextView tv_usuario;
        ImageView im_user;
        CardView cardView;



        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            tv_usuario=itemView.findViewById(R.id.tv_user);
            im_user = itemView.findViewById(R.id.img_usuario);
            cardView = itemView.findViewById(R.id.cardview);

        }
    }
}
