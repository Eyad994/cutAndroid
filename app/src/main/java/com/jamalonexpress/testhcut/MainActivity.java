package com.jamalonexpress.testhcut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    TextView errorMsg;
    TextView registerLink;
    EditText email, password;
    Button login;
    RelativeLayout relativeLayout;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);

        registerLink = findViewById(R.id.link_signup);
        relativeLayout = findViewById(R.id.relativeLay);
        errorMsg = findViewById(R.id.error_text);
        textViewResult = findViewById(R.id.text_view_result);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        login = findViewById(R.id.btn_login);

        textViewResult.setVisibility(View.GONE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(RetrofitClient.getClient())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();

                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                if (strEmail.matches("") || strPassword.matches("")) {
                    errorMsg.setVisibility(View.VISIBLE);
                    errorMsg.setText("Empty email or password!");
                    return;
                }

                if (isValidEmail(email.getText())) {
                    loginRequest(strEmail, strPassword);
                }else{
                    errorMsg.setText("Incorrect email or password!");
                }

            }
        });

        // loginRequest("testUser@gmail.com", "12345678");
        // getRequest();
        // postRequest();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void loginRequest(String email, String password) {
        Call<ResponseBody> call = jsonPlaceHolderApi.loginPost(email, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (!response.isSuccessful()) {
                    errorMsg.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onNotSuccessful: " + response.code());
                    errorMsg.setText("Incorrect password!");
                    return;
                }

                Log.d(TAG, "onResponse: " + response.code());
                ResponseBody logins = response.body();
                errorMsg.setText(response.message());
//                for (Login login : l ogins) {
//                    String Content = "Name: " + login.getName() + "\n";
//                    Content += "ID: " + login.getId() + "\n";
//                    errorMsg.setVisibility(View.VISIBLE);
//                    errorMsg.setText(Content);
//                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getCause());
                errorMsg.setText(t.getMessage());
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

//    private void postRequest(){
//
//        Call<Login> call = jsonPlaceHolderApi.createPost();
//
//        call.enqueue(new Callback<Login>() {
//            @Override
//            public void onResponse(Call<Login> call, Response<Login> response) {
//                if(!response.isSuccessful()){
//                    textViewResult.setText("Code"+ response.code());
//                    return;
//                }
//
//                Login loginResponse = response.body();
//
//                textViewResult.setText("Code:"+response.code() +"\nLogin Successfully");
//            }
//
//            @Override
//            public void onFailure(Call<Login> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });
//    }

    private void getRequest() {


        Call<List<User>> call = jsonPlaceHolderApi.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<User> users = response.body();

                for (User user : users) {

                    String content = "";
                    content += "ID: " + user.getId() + "\n";
                    content += "Name: " + user.getName() + "\n";
                    content += "Email: " + user.getEmail() + "\n";
                    content += "Phone Number: " + user.getPhoneNumber() + "\n";
                    content += "Date Of Birth: " + user.getDateOfBirth() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
