package com.myproject.pkl.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.myproject.pkl.R;


public class SettingsFragment extends Fragment {

    private View v;
    private TextInputLayout container_url;
    private EditText et_url;
    private String sharedPrefFile = "com.myproject.pkl.preferences";
    private SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null){
            v = inflater.inflate(R.layout.fragment_settings, container, false);

            sharedPref = getContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
            String url = sharedPref.getString("url", "https://proyekmagangpkl2020.herokuapp.com/predict");

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
        }
        return v;
    }
}