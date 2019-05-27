package com.jamalonexpress.testhcut;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        loginRequest("testUser@gmail.com", "12345678");
        // getRequest();
        // postRequest();
    }

    private void loginRequest(String email, String password) {
        Call<ResponseBody> call = jsonPlaceHolderApi.loginPost(email, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if (!response.isSuccessful()) {
                    if (response.code() == 400) {
                        textViewResult.setText("Please check your email and/or password");
                        return;
                    }
                    textViewResult.setText("Code: " + response.code() + "\n" + response.message());
                    return;
                }

                try {
                    textViewResult.setText("Code: " + response.code() + "\n" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
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
