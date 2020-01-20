package com.example.pagingtest2.user;

import com.example.pagingtest2.user.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("users")
    Call<UserResponse> getUsers(@Query("page") long page);

}
