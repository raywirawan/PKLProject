package com.myproject.pkl.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.myproject.pkl.R;
import com.myproject.pkl.model.Database;
import com.myproject.pkl.model.IdentifiedObject;


public class SettingsFragment extends Fragment {

    private View v;
    private TextInputLayout container_url;
    private TextView btnDelete;
    private EditText et_url;
    private String sharedPrefFile = "com.myproject.pkl.preferences";
    private SharedPreferences sharedPref;
    private Database database;
    private SwitchMaterial swDarkMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Database.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null){
            v = inflater.inflate(R.layout.fragment_settings, container, false);

            sharedPref = getContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

            String url = sharedPref.getString("url", "https://proyekmagangpkl2020.herokuapp.com/predict");
            swDarkMode = v.findViewById(R.id.sw_darkmode);
            btnDelete = v.findViewById(R.id.btn_clear_history);
            container_url = v.findViewById(R.id.container_url);
            et_url = v.findViewById(R.id.et_url);
            et_url.setText(url);

            container_url.setEndIconOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!et_url.isEnabled()) {
                        et_url.setEnabled(!et_url.isEnabled());
                        container_url.setEndIconDrawable(R.drawable.ic_save_24);
                    } else {
                        String newUrl = et_url.getText().toString();
                        if (newUrl.isEmpty())
                            Toast.makeText(getContext(), "Please input URL", Toast.LENGTH_SHORT).show();
                        else {
                            et_url.setEnabled(!et_url.isEnabled());
                            container_url.setEndIconDrawable(R.drawable.ic_edit_24);
                            //save new setting
                            SharedPreferences.Editor preferencesEditor = sharedPref.edit();
                            preferencesEditor.putString("url", newUrl);
                            preferencesEditor.apply();
                            Toast.makeText(getContext(), "URL saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SettingsFragment.DeleteDB().execute();
                }
            });
            swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getContext(), "Theme Changed", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });
        }
        return v;
    }

    private class DeleteDB extends AsyncTask<Void, Void, String> {
        private Exception exception;
        @Override
        protected String doInBackground(Void... voids) {
            try {
                database.identifiedObjectDao().deleteAll();
                return "History Deleted";

            } catch (Exception e) {
                this.exception = e;
                return exception.getMessage();
            }
        }
        protected void onPostExecute(String res) {
            Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
            System.out.println("anjir "+res);
        }
    }
}