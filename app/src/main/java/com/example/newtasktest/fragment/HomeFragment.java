package com.example.newtasktest.fragment;


import static android.content.Context.MODE_PRIVATE;
import static com.example.newtasktest.util.appConstants.Email;
import static com.example.newtasktest.util.appConstants.My_preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.newtasktest.R;

public class HomeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sh = getActivity().getSharedPreferences(My_preference, MODE_PRIVATE);
        String name=sh.getString(Email,"");
        TextView txt_email=v.findViewById(R.id.txt_setemail);
        txt_email.setText(name);

        return v;
    }
}