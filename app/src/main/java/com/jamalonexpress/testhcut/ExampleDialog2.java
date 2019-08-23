package com.jamalonexpress.testhcut;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;
public class ExampleDialog2 extends AppCompatDialogFragment {

    private static final String TAG = "ExampleDialog2";
    private DatePickerDialog.OnDateSetListener datePickerDialog;
    private String apiTitle;
    private EditText editText;
    private ExampleDialogListener listener;
    int mHour;
    int mMinute;
    int mDay;
    int mMonth;
    int mYear;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_example_dialog, null);

        editText = view.findViewById(R.id.alert_dialogg);

        builder.setView(view)
                .setIcon(R.drawable.twitter)
                .setTitle(apiTitle)
                .setNegativeButton("cancel", null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         String date = editText.getText().toString();
                         listener.applyInputs(date);
                    }
                });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog(v);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public void setApiTitle(String apiTitle) {
        this.apiTitle = apiTitle;
    }

    public interface ExampleDialogListener {
        void applyInputs(String date);
    }

    private void DatePickerDialog(View view) {

        final Calendar c = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mDay = dayOfMonth;
                        mMonth = monthOfYear + 1;
                        mYear = year;
//                        editText.setText(dayOfMonth + "/"
//                                + (monthOfYear + 1) + "/" + year);
                        timePicker();

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        dialog.show();


//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog dialog = new DatePickerDialog(
//                view.getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,
//                datePickerDialog,
//                year, month, day
//        );
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                editText.setText(year + "/" + month + "/" + dayOfMonth);
//            }
//        };
    }

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;


                        editText.setText(mDay + "/"
                                + (mMonth) + "/" + mYear+ " - "+ mHour +":"+mMinute);
                        // editTextPassword.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
