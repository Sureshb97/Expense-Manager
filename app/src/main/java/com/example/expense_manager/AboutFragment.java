package com.example.expense_manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AboutFragment extends PreferenceFragmentCompat {

    Preference version;
    Preference erase_data;
    Preference github;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Preference version=findPreference("version");
        Preference erase_data=findPreference("clear_data");
        Preference github=findPreference("github");

        version.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getContext(), preference.getSummary(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        erase_data.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(getContext());
                alertDialogBuilder.setTitle("Clear data");
                alertDialogBuilder.setMessage("All data will be lost");

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearAll();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialogBuilder.show();
                return false;
            }
        });

        github.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/SureshBahavathi/Expense-Manager"));
                getActivity().startActivity(intent);
                return false;
            }
        });
    }


    public void clearAll()
    {
        class Clear extends AsyncTask<Void,Void,Void>
        {

            @Override
            protected Void doInBackground(Void... voids)
            {
                DatabaseClient.getInstance(getContext()).getExpenseDatabase().expenseDao().clearAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "All data cleared", Toast.LENGTH_SHORT).show();
            }
        }
        Clear clear=new Clear();
        clear.execute();
    }




}