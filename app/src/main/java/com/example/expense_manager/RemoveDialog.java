package com.example.expense_manager;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;

public class RemoveDialog extends DialogFragment
{

    Toolbar toolbar;
    static int position;
    ListItem listItem;
    static Context ctx;
    static List<ListItem> list;
    TextInputLayout textInputLayout1;
    TextInputEditText textInputEditText1;

    public static RemoveDialog display(FragmentManager fragmentManager, int pos, List<ListItem> lis, Context mctx)
        {
            position=pos;
            list=lis;
            ctx=mctx;
            RemoveDialog removeDialog=new RemoveDialog();
            removeDialog.show(fragmentManager,"Remove dialog");
            return  removeDialog;
        }
    @Override
    public void onStart()
        {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null)
            {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, 650);
            }
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
         super.onCreateView(inflater, container, savedInstanceState);
         View view=inflater.inflate(R.layout.remove_dialog,container,false);
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputLayout1=view.findViewById(R.id.text_layout);
        textInputEditText1=view.findViewById(R.id.text_input1);
        toolbar=view.findViewById(R.id.toolbar);

        listItem=list.get(position);

        toolbar.inflateMenu(R.menu.remove_menu);
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
                remove();
                return false;
            }
        });
    }
    public void remove()
    {
        final String money="-"+String.valueOf(textInputEditText1.getText());

        if(money.equals("-"))
        {
            textInputEditText1.setError("Enter Money");
            textInputEditText1.requestFocus();
            return;
        }

        final int checkMoney=Integer.parseInt(money);

        class Removedata extends AsyncTask<Void, Void, Boolean>
        {

            @Override
            protected Boolean doInBackground(Void... voids)
            {
                Expense expense =new Expense();
                expense.setAmount(Integer.parseInt(money));
                expense.setCategory(listItem.getItemName());
                expense.setDate(new java.sql.Date(System.currentTimeMillis()));

                int checkSum= DatabaseClient.getInstance(ctx).getExpenseDatabase().expenseDao().getSumQuery(new java.sql.Date(System.currentTimeMillis()),listItem.getItemName());

                if((checkSum+checkMoney)>=0)
                {
                    DatabaseClient.getInstance(getContext()).getExpenseDatabase().expenseDao().insert(expense);
                    return true;
                }
                else
                {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean isRemoved) {
                super.onPostExecute(isRemoved);
                if(isRemoved)
                {
                    Toast.makeText(getActivity(),"Money Removed", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        }
        Removedata removedata=new Removedata();
        removedata.execute();
    }
}
