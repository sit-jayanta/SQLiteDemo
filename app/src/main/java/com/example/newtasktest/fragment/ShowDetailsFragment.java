package com.example.newtasktest.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newtasktest.util.appConstants.My_preference;
import static com.example.newtasktest.util.appConstants.UpdateFlag;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.newtasktest.database.DBhandler;
import com.example.newtasktest.R;
import com.example.newtasktest.model.Model;
import com.example.newtasktest.myadapter.MySecondAdapter;

import java.util.ArrayList;

public class ShowDetailsFragment extends Fragment {


    DBhandler dBhandler;

    ArrayList<Model> models;

    RecyclerView user_details;

    MySecondAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_show_details, container, false);

        user_details = v.findViewById(R.id.showuser);
        ImageView cancel=v.findViewById(R.id.clearbutton);
        TextView text = getActivity().findViewById(R.id.txt_toolbar);
        Button searchBtn = v.findViewById(R.id.search);

        EditText search= v.findViewById(R.id.edt_search);
        models= new ArrayList<>();
        dBhandler= new DBhandler(getContext());
        models=dBhandler.readUser("");
        adapter= new MySecondAdapter(models,getContext());
        LinearLayoutManager linear = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        user_details.setLayoutManager(linear);
        user_details.setAdapter(adapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str= search.getText().toString();
                if(str.length()>0){
                    cancel.setVisibility(View.VISIBLE);
                }else
                    cancel.setVisibility(View.INVISIBLE);

                models=dBhandler.searchUser(str);
                if(models.size()==0){
                    Toast.makeText(getContext(), "No Matching Entries", Toast.LENGTH_SHORT).show();
                    user_details.setVisibility(View.INVISIBLE);
                }else {
                    user_details.setVisibility(View.VISIBLE);
                    adapter = new MySecondAdapter(models, getContext());
                    LinearLayoutManager linear = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    user_details.setLayoutManager(linear);
                    user_details.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });


            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
//                    adapter.setOnclickListener(model -> {
//                        int updateFlag=1;
//                        AddFragment addFragment = new AddFragment();
//                        String name= model.getName();
//                        Bundle bundle= new Bundle();
//                        bundle.putString("name",name);
//                        addFragment.setArguments(bundle);
//                        getParentFragmentManager().beginTransaction().replace(R.id.frame,addFragment).addToBackStack(null).commit();
//                        text.setText(R.string.str_updateuser);
//                        SharedPreferences sh = getActivity().getSharedPreferences(My_preference, MODE_PRIVATE);
//                        SharedPreferences.Editor edit = sh.edit();
//                        edit.putInt(UpdateFlag,updateFlag);
//                        edit.apply();
//
//                    });
//
//                    adapter.setDeleteClickListener(model -> {
//                        dBhandler.deleteUser(model.getName());
//                        adapter.notifyDataSetChanged();
//                    });
                }
            });


        adapter.setOnclickListener(model -> {
            int updateFlag=1;
            AddFragment addFragment = new AddFragment();
            String name= model.getName();
            Bundle bundle= new Bundle();
            bundle.putString("name",name);
            addFragment.setArguments(bundle);
            getParentFragmentManager().beginTransaction().replace(R.id.frame,addFragment).addToBackStack(null).commit();
            text.setText(R.string.str_updateuser);
            SharedPreferences sh = getActivity().getSharedPreferences(My_preference, MODE_PRIVATE);
            SharedPreferences.Editor edit = sh.edit();
            edit.putInt(UpdateFlag,updateFlag);
            edit.apply();

        });

        adapter.setDeleteClickListener(model -> {
            dBhandler.deleteUser(model.getName());
            adapter.notifyDataSetChanged();
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
            }
        });


        return v;
    }



}