package com.jamalonexpress.testhcut;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
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
    CheckBox checkBox;
    LinearLayout linearLayout;
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
        checkBox = findViewById(R.id.btn_check);
        linearLayout = findViewById(R.id.serviceProvider);
        
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        

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
                dateEditText.setText(year + "-"+month+"-"+dayOfMonth);
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
        String mName = name.getText().toString();
        String mEmail = email.getText().toString();
        String mPassword = password.getText().toString();
        String mConfirmPass = confirmPassword.getText().toString();
        String mPhoneNumber = phoneNumber.getText().toString();
        String mDate = dateEditText.getText().toString();
        String mGender = gender.getText().toString();

       // final Register register = new Register(mName,mEmail,mPassword,mConfirmPass,mPhoneNumber,mDate,mGender);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        Map<String, String> fields = new HashMap<>();
        final Map<String, String> fields = new HashMap<>();
        fields.put("name",mName);
        fields.put("email",mEmail);
        fields.put("password",mPassword);
        fields.put("password_confirmation",mConfirmPass);
        fields.put("phone_number",mPhoneNumber);
        fields.put("date_of_birth",mDate);
        fields.put("date_of_birth","1994-6-24");
        fields.put("gender",mGender);

        Call<ResponseBody> call = jsonPlaceHolderApi.registerPost(fields);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    return;
                }

                ResponseBody registers = response.body();
                Log.d(TAG, "onResponse: "+fields);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }
}
