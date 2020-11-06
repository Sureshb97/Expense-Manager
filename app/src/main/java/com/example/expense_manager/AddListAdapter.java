package com.example.expense_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AddListAdapter extends RecyclerView.Adapter<AddListAdapter.ViewHolder>
{
    List<ListItem> list;
    Context ctx;
    ListItem listItem;
    FragmentManager fragmentManager;

    public AddListAdapter(Context context,List<ListItem> item,FragmentManager fragmentManager)
    {
        this.list=item;
        this.ctx=context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        listItem=list.get(position);
        holder.textView.setText(listItem.getItemName());
        holder.imageView.setImageResource(listItem.getItemImageID());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;
        ImageView imageView;
        TextView textView;
        Button addButton,removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.item_image);
            this.materialCardView=itemView.findViewById(R.id.card);
            this.textView=itemView.findViewById(R.id.item_text);
            this.addButton=itemView.findViewById(R.id.add_button);
            this.removeButton=itemView.findViewById(R.id.remove_button);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddDialog.display(fragmentManager,getAdapterPosition(),list,ctx);
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RemoveDialog.display(fragmentManager,getAdapterPosition(),list,ctx);
                }
            });


        }



    }


}
