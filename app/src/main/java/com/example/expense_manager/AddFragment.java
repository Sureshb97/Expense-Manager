package com.example.expense_manager;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment
{
    Context context;
    RecyclerView recyclerView;
    List<ListItem> itemList;
    FragmentManager fragmentManager;

    public AddFragment(Context context)
    {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_add, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycler_view);

        itemList=new ArrayList<ListItem>();
        itemList.add(new ListItem("Food",R.drawable.food));
        itemList.add(new ListItem("Entertainment",R.drawable.entertainment));
        itemList.add(new ListItem("Housing",R.drawable.housing));
        itemList.add(new ListItem("Transportation",R.drawable.transportation));
        itemList.add(new ListItem("Clothing",R.drawable.clothings));
        itemList.add(new ListItem("Others",R.drawable.others));

        fragmentManager =getFragmentManager();

        AddListAdapter addListAdapter=new AddListAdapter(context,itemList, fragmentManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(addListAdapter);
    }
}