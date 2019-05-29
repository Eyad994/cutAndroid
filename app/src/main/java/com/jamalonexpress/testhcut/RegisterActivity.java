package com.jamalonexpress.testhcut;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText email, name, password, confirmPassword,dateEditText, phoneNumber, gender;
    DatePickerDialog.OnDateSetListener datePickerDialog;
    Button btnRegister;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.input_email);
        name = findViewById(R.id.input_name);
        password = findViewById(R.id.input_password);
        confirmPassword = findViewById(R.id.input_password_confirmation);
        phoneNumber = findViewById(R.id.phone_number);
        dateEditText = findViewById(R.id.input_date);
        gender = findViewById(R.id.input_gender);
        btnRegister = findViewById(R.id.btn_register);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                 RegisterActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        datePickerDialog,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("Register", "onDateSet: "+ year + "/"+month+"/"+dayOfMonth);
                dateEditText.setText(year + "/"+month+"/"+dayOfMonth);
            }
        };


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEmpty();

                register();
            }
        });

    }


    private void isEmpty(){
        if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(email.getText())
                || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(confirmPassword.getText())
        || TextUtils.isEmpty(phoneNumber.getText())|| TextUtils.isEmpty(dateEditText.getText())
        || TextUtils.isEmpty(gender.getText())){
            Toast.makeText(this, "You should fill all fields!", Toast.LENGTH_SHORT).show();
        }
    }


    private void register(){
        String mName = String.valueOf(name);
        String mEmail = String.valueOf(email);
        String mPassword = String.valueOf(password);
        String mConfirmPass = String.valueOf(confirmPassword);
        String mPhoneNumber = String.valueOf(phoneNumber);
        String mDate = String.valueOf(dateEditText);
        String mGender = String.valueOf(gender);

       // final Register register = new Register(mName,mEmail,mPassword,mConfirmPass,mPhoneNumber,mDate,mGender);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("name","eyad");
        fields.put("email","eyad@gmail.com");
        fields.put("password","12345678");
        fields.put("password_confirmation","12345678");
        fields.put("phone_number","0796714838");
        fields.put("date_of_birth","2019-02-12");
        fields.put("gender","Male");

        Call<ResponseBody> call = jsonPlaceHolderApi.registerPost(fields);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    return;
                }

                ResponseBody registers = response.body();
                Log.d(TAG, "onResponse: "+registers);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }
}
