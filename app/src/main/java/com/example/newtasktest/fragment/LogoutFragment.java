package com.example.newtasktest.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.newtasktest.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogoutFragment extends Fragment {

    EditText start_date, end_date, start_time, end_time;
    int val_date, val_month, val_year;
    Button btn_submit, btn_submit2;
    int[] startTime, endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logout, container, false);

        //findViewById for every element
        start_date = v.findViewById(R.id.start_date);
        end_date = v.findViewById(R.id.end_date);
        start_time = v.findViewById(R.id.edt_starttime);
        end_time = v.findViewById(R.id.edt_endtime);
        btn_submit = v.findViewById(R.id.btn_submit);
        btn_submit2 = v.findViewById(R.id.btn_submit2);

        //edittext 1 onclicklistener
        start_time.setOnClickListener(v1 -> startTime = calenderTime(start_time));

        //edittext 2 onclicklistener
        end_time.setOnClickListener(v12 -> endTime = calenderTime(end_time));

        //edittext 3 onclicklistener
        start_date.setOnClickListener(v13 -> calenderDate(start_date));

        //edittext 4 onclicklistener
        end_date.setOnClickListener(v14 -> {
            final Calendar c = Calendar.getInstance();
            final Calendar min_date = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int date = c.get(Calendar.DATE);
            int month = c.get(Calendar.MONTH);

            c.set(Calendar.MONTH, month);
            c.set(Calendar.DATE, date);
            c.set(Calendar.YEAR, year);

            min_date.set(Calendar.DATE, val_date);
            min_date.set(Calendar.MONTH, val_month);
            min_date.set(Calendar.YEAR, val_year);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> end_date.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, date);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMinDate(min_date.getTimeInMillis());
        });

        //button1 onclicklistener
        btn_submit2.setOnClickListener(v15 -> {

            if (!(start_date.getText().toString().isEmpty() || end_date.getText().toString().isEmpty())) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1 = sdf.parse(start_date.getText().toString());
                    Date date2 = sdf.parse(end_date.getText().toString());

                    assert date1 != null;
                    if (date1.before(date2)) {
                        Toast.makeText(getContext(), "Valid date", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getContext(), "Invalid date", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else
                Toast.makeText(getContext(), "Date cannot be Empty", Toast.LENGTH_SHORT).show();

        });

        //submit button2 OnClickListener
        btn_submit.setOnClickListener(v16 -> {
            if (!(start_time.getText().toString().isEmpty() || end_time.getText().toString().isEmpty())) {
                if (startTime[0] == endTime[0]) {
                    if (startTime[1] < endTime[1]) {
                        Toast.makeText(getContext(), getString(R.string.str_timevalid), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getContext(), getString(R.string.str_invalid), Toast.LENGTH_SHORT).show();
                } else if (startTime[0] < endTime[0]) {
                    Toast.makeText(getContext(), getString(R.string.str_timevalid), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), getString(R.string.str_invalid), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
        });
        return v;
    }


    //method for datePicker
    public void calenderDate(EditText editText) {
        final Calendar c = Calendar.getInstance();
        final Calendar min_date = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);


        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, date);
        c.set(Calendar.YEAR, year);

        min_date.set(Calendar.DATE, 1);
        min_date.set(Calendar.MONTH, 0);
        min_date.set(Calendar.YEAR, 1990);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            editText.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
            val_date = dayOfMonth;
            val_month = month1;
            val_year = year1;

        }, year, month, date);


        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(min_date.getTimeInMillis());


    }

    //method for timePicker
    public int[] calenderTime(EditText editText) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        final int[] time = new int[2];

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            editText.setText(hourOfDay + ":" + minute);
            time[0] = hourOfDay;
            time[1] = minute;
        }, hour, minutes, true);

        timePickerDialog.show();

        return time;
    }

}