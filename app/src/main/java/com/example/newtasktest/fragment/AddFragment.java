package com.example.newtasktest.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newtasktest.util.appConstants.My_preference;
import static com.example.newtasktest.util.appConstants.UpdateFlag;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newtasktest.database.DBhandler;
import com.example.newtasktest.R;
import com.example.newtasktest.model.Model;
import com.example.newtasktest.myadapter.MyAdapter;

import java.util.ArrayList;
import java.util.Calendar;


public class AddFragment extends Fragment {

    Spinner spinner;
    EditText edtName;
    RadioGroup radioGroup;
    RadioButton gender, male, female;
    Button btn_submit, btn_clear;
    int id;
    ImageView image;
    TextView tool_text, dob;
    CheckBox chk_read, chk_write;
    int startDate, endDate, age;
    RelativeLayout relativeLayout;
    ArrayList<Model> model, models2;
    ArrayList<String> array;
    MyAdapter adapter;
    View v;

    private DBhandler dBhandler;

    SharedPreferences sh;

    public String[] designation = {"Select Designation", "Software Developer", "Marketing Manager", "Project Head", "Software tester"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_add, container, false);

        edtName = v.findViewById(R.id.edt_name);
        btn_submit = v.findViewById(R.id.btnsubmit);
        btn_clear = v.findViewById(R.id.btnclear);
        radioGroup = v.findViewById(R.id.groupradio);
        chk_read = v.findViewById(R.id.checkBox2);
        chk_write = v.findViewById(R.id.checkBox3);
        image = v.findViewById(R.id.image);
        tool_text = v.findViewById(R.id.header);
        dob = v.findViewById(R.id.txt_setdob);
        relativeLayout = v.findViewById(R.id.relative_add);
        male = v.findViewById(R.id.radia_id1);
        female = v.findViewById(R.id.radia_id2);
        spinner = v.findViewById(R.id.spinner);
        dBhandler = new DBhandler(getContext());

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });

         sh = getActivity().getSharedPreferences(My_preference, MODE_PRIVATE);
        int update = sh.getInt(UpdateFlag, 0);

        if (update == 1) {

            btn_submit.setText("Update");


            Bundle bundle = getArguments();
            String username = bundle.getString("name");
            dBhandler = new DBhandler(getContext());
            models2 = new ArrayList<>();
            models2 = dBhandler.readUser(username);
            Model model1 = models2.get(0);

            //setting adapter to spinner
            ArrayAdapter<String> ad1 = new ArrayAdapter<>(requireContext(), R.layout.spinnerdrop, designation);
            ad1.setDropDownViewResource(R.layout.spinnerdrop);
            spinner.setAdapter(ad1);

            //getting all fields from database
            edtName.setText(model1.getName());
            selectDesignation();
            selectHobby();
            selectGender();
            dob.setText(model1.getAge());


            //datepicker listener
            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int date = c.get(Calendar.DATE);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {

                        dob.setText(dayOfMonth + "/" + month1 + "/" + year1);
                    }, year, month, date);
                    datePickerDialog.show();
                }
            });

            //update button setonclicklistner
            btn_submit.setOnClickListener(view -> {
                if (validate1()) {
                    id = radioGroup.getCheckedRadioButtonId();
                    gender = v.findViewById(id);
                    dBhandler.updateUser(username, edtName.getText().toString(), spinner.getSelectedItem().toString(), getChecked().toString(), gender.getText().toString(), dob.getText().toString());
                    ShowDetailsFragment show = new ShowDetailsFragment();
                    getParentFragmentManager().beginTransaction().replace(R.id.frame, show).addToBackStack(null).commit();
                    int newFlag = 0;
                    TextView text = getActivity().findViewById(R.id.txt_toolbar);
                    text.setText("User Details");
                    SharedPreferences.Editor edit = sh.edit();
                    edit.putInt(UpdateFlag, newFlag);
                }
            });


            //cancel button setonclicklistner
            btn_clear.setOnClickListener(v1 -> {
                ShowDetailsFragment show = new ShowDetailsFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.frame, show).commit();
            });


        } else {

            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int date = c.get(Calendar.DATE);

                    endDate = year;

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
                        startDate = year1;
                        dob.setText(dayOfMonth + "/" + month1 + "/" + year1);
                    }, year, month, date);
                    datePickerDialog.show();
                }
            });


            //Array list model
            model = new ArrayList<>();


            //recyclerview
            RecyclerView recyclerView = v.findViewById(R.id.recyclerview);
            adapter = new MyAdapter(model);
            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            //adapter for spinner
            ArrayAdapter<String> ad = new ArrayAdapter<>(getContext(), R.layout.spinner_item, designation);
            ad.setDropDownViewResource(R.layout.spinnerdrop);
            spinner.setAdapter(ad);


            //btn submit setOnClickListener
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        btn_showMessage(v);
                        adapter.notifyDataSetChanged();
                    }
                }

            });

            //btn clear set on click listener
            btn_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtName.setText("");
                    spinner.setSelection(0);
                    chk_read.setChecked(false);
                    chk_write.setChecked(false);
                    radioGroup.clearCheck();
                }
            });

            recyclerView.setAdapter(adapter);
        }
        return v;

    }
    // Inflate the layout for this fragment


    public boolean validate() {
        boolean bool = false;
        age = endDate - startDate;
        if (edtName.getText().toString().isEmpty()) {
            edtName.setError(getString(R.string.err_nameempty));
        } else if (edtName.length() < 3) {
            edtName.setError(getString(R.string.str_errorname));
        } else {
            if (spinner.getSelectedItem().toString().equals(getString(R.string.str_errDesig))) {
                Toast.makeText(getContext(), R.string.err_selectdes, Toast.LENGTH_SHORT).show();
            } else {
                if (!chk_read.isChecked() && !chk_write.isChecked()) {
                    Toast.makeText(getContext(), R.string.err_slecthobby, Toast.LENGTH_SHORT).show();
                } else {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getContext(), R.string.err_gender, Toast.LENGTH_SHORT).show();
                    } else if (age < 18) {
                        Toast.makeText(getContext(), "Your age should be at least 18", Toast.LENGTH_SHORT).show();
                    } else bool = true;
                }
            }
        }
        return bool;
    }

    private void hideKeyboard(View v) {
        InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void btn_showMessage(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        ImageView btn_cancel = mView.findViewById(R.id.img_cancel);
        ImageView btn_okay = mView.findViewById(R.id.img_accept);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                image.setVisibility(View.VISIBLE);
                tool_text.setVisibility(View.VISIBLE);
                StringBuilder sb = new StringBuilder();
                if (chk_read.isChecked()) {
                    sb.append("Read");
                }
                if (chk_write.isChecked()) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append("Write");
                }
                model.size();
                id = radioGroup.getCheckedRadioButtonId();
                gender = v.findViewById(id);
                model.add(new Model(edtName.getText().toString(), spinner.getSelectedItem().toString(), String.valueOf(sb), gender.getText().toString(), dob.getText().toString()));
                dBhandler.addNewUser(edtName.getText().toString(), spinner.getSelectedItem().toString(), String.valueOf(sb), gender.getText().toString(), dob.getText().toString());

                edtName.setText("");
                spinner.setSelection(0);
                chk_read.setChecked(false);
                chk_write.setChecked(false);
                radioGroup.clearCheck();
                sb.setLength(0);
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }

        });
        alertDialog.show();
    }

    public void selectDesignation() {
        switch (models2.get(0).getDesignation()) {

            case "Software Developer":
                spinner.setSelection(1);
                break;
            case "Marketing Manager":
                spinner.setSelection(2);
                break;
            case "Project Head":
                spinner.setSelection(3);
                break;
            case "Software tester":
                spinner.setSelection(4);
                break;
        }
    }

    public void selectHobby() {
        switch (models2.get(0).getHobby()) {
            case "Read":
                chk_read.setChecked(true);
                break;
            case "Write":
                chk_write.setChecked(true);
                break;
            case "Read,Write":
                chk_read.setChecked(true);
                chk_write.setChecked(true);
                break;
        }
    }

    public void selectGender() {
        switch (models2.get(0).getGender()) {
            case "Male":
                male.setChecked(true);
                break;
            case "Female":
                female.setChecked(true);
                break;
        }
    }

    public StringBuilder getChecked() {
        StringBuilder sb = new StringBuilder();
        if (chk_read.isChecked()) {
            sb.append("Read");
        }
        if (chk_write.isChecked()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append("Write");
        }

        return sb;
    }

    public boolean validate1() {
        boolean bool = false;
        age = endDate - startDate;
        if (edtName.getText().toString().isEmpty()) {
            edtName.setError(getString(R.string.err_nameempty));
        } else if (edtName.length() < 3) {
            edtName.setError(getString(R.string.str_errorname));
        } else {
            if (spinner.getSelectedItem().toString().equals(getString(R.string.str_errDesig))) {
                Toast.makeText(getContext(), R.string.err_selectdes, Toast.LENGTH_SHORT).show();
            } else {
                if (!chk_read.isChecked() && !chk_write.isChecked()) {
                    Toast.makeText(getContext(), R.string.err_slecthobby, Toast.LENGTH_SHORT).show();
                } else {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getContext(), R.string.err_gender, Toast.LENGTH_SHORT).show();
                    } else bool = true;
                }
            }
        }
        return bool;
    }
}