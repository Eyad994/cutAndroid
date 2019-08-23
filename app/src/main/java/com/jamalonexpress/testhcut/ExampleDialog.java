package com.jamalonexpress.testhcut;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ExampleDialog extends AppCompatActivity {

    private static final String TAG = "ExampleDialog";
    EditText editText;
    DatePickerDialog.OnDateSetListener datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_dialog);

        editText = findViewById(R.id.alert_dialogg);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Example Dialog ");
            }
        });
    }

    public void buttonClicked() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this, android.R.style.Theme_Holo_Dialog_MinWidth,
                datePickerDialog,
                year, month, day
        );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("Register", "onDateSet: " + year + "/" + month + "/" + dayOfMonth);
            }
        };
    }


}
