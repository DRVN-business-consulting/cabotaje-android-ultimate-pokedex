package dev.jay.ultimatepokedex.api;

import dev.jay.ultimatepokedex.model.dto.request.LoginDto;
import dev.jay.ultimatepokedex.model.dto.request.SignUpDto;
import dev.jay.ultimatepokedex.model.dto.request.UpdateUserDto;
import dev.jay.ultimatepokedex.model.dto.response.RefreshTokenDto;
import dev.jay.ultimatepokedex.model.dto.response.SuccessDto;
import dev.jay.ultimatepokedex.model.dto.response.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserApi {

    @POST("login")
    Call<RefreshTokenDto> login(@Body LoginDto user);

    @POST("signup")
    Call<RefreshTokenDto> signup(@Body SignUpDto user);


    @POST("refresh-token")
    Call<RefreshTokenDto> refreshToken();

    @GET("user/me")
    Call<UserDto> me();

    @PATCH("user/me")
    Call<UserDto> updateMe(@Body UpdateUserDto updateUserDto);

    @POST("logout")
    Call<SuccessDto> logout();

}

