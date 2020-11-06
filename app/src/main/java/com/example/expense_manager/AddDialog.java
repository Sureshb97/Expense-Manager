package com.example.expense_manager;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class AddDialog extends DialogFragment
{
    Toolbar toolbar;
    static int position;
    ListItem listItem;
    static Context ctx;
    static List<ListItem> list;
    TextInputLayout textInputLayout1,textInputLayout2;
    TextInputEditText textInputEditText1,textInputEditText2;

    public static AddDialog display(FragmentManager fragmentManager, int pos, List<ListItem> lis, Context mctx)
    {
        position=pos;
        AddDialog exampleDialog = new AddDialog();
        list=lis;
        ctx=mctx;
        exampleDialog.show(fragmentManager,"add_dialog");
        return exampleDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, 850);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.add_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        textInputLayout1=view.findViewById(R.id.text_layout);
        textInputLayout2=view.findViewById(R.id.category);
        textInputEditText1=view.findViewById(R.id.text_input1);
        textInputEditText2=view.findViewById(R.id.text_input2);
        toolbar=view.findViewById(R.id.toolbar);

        listItem=list.get(position);

        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setTitle(listItem.getItemName());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                save();
                return false;
            }
        });
    }

    public void save()
    {
        final String money=String.valueOf(textInputEditText1.getText());
        final String comments=String.valueOf(textInputEditText2.getText());

        if(money.isEmpty())
        {
            textInputEditText1.setError("Enter Money");
            textInputEditText1.requestFocus();
            return;
        }

        class Savedata extends AsyncTask<Void,Void,Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {

                Expense expense =new Expense();
                expense.setAmount(Integer.parseInt(money));
                expense.setComments(comments);
                expense.setCategory(listItem.getItemName());
                expense.setDate(new java.sql.Date(System.currentTimeMillis()));

                DatabaseClient.getInstance(getContext()).getExpenseDatabase().expenseDao().insert(expense);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity(),"Money Added", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }
        Savedata savedata=new Savedata();
        savedata.execute();
    }
}