package com.rsin.blogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    public Context context;
    List<DataObject> list;


    public NotesAdapter(Context context, List<DataObject> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.notes_recycleview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.heading.setText(list.get(position).heading.toString());
        holder.description.setText(list.get(position).description.toString());
        holder.imageView.setImageBitmap(list.get(position).bitmap);

        holder.card_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewNotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title",list.get(position).heading);
                intent.putExtra("Description",list.get(position).description);
                intent.putExtra("notes_id",list.get(position).note_id);
                context.startActivity(intent);
                Toast.makeText(context, list.get(position).note_id, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card_item;
        TextView heading ,description;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_item = itemView.findViewById(R.id.card_item);
            heading = itemView.findViewById(R.id.heading_tt);
            imageView = itemView.findViewById(R.id.note_img);
            description = itemView.findViewById(R.id.note_dis);
        }
    }
}
