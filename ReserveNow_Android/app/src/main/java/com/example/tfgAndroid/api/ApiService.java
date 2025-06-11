package com.example.tfgAndroid.api;

import com.example.tfgAndroid.model.CreateReviewDTO;
import com.example.tfgAndroid.model.LoginRequest;
import com.example.tfgAndroid.model.ReservationDTO;
import com.example.tfgAndroid.model.ReviewDTO;
import com.example.tfgAndroid.model.TokenResponse;
import com.example.tfgAndroid.model.User;
import com.example.tfgAndroid.model.ReservationRequest;
import com.example.tfgAndroid.model.UserDTO;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/api/v1/auth/register")
    Call<Void> registerUser(@Body User user);

    @POST("/api/v1/auth/login")
    Call<TokenResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("/api/v1/reservations")
    Call<List<ReservationDTO>> getUserReservations();

    @PUT("/api/v1/reservations/{id}")
    Call<ReservationDTO> updateReservation(
            @Path("id") Integer id,
            @Body ReservationDTO reservationDTO
    );

    @POST("/api/v1/reservations")
    Call<ReservationDTO> createReservation(@Body ReservationRequest reservationRequest);

    @DELETE("/api/v1/reservations/{id}")
    Call<Void> cancelReservation(@Path("id") Integer id);

    @GET("/api/v1/user/me")
    Call<UserDTO> getMyUser();

    @DELETE("/api/v1/user/{userId}")
    Call<Void> deleteUser(@Path("userId") int userId);

    @GET("api/reviews")
    Call<List<ReviewDTO>> listAllReviews();

    @POST("api/reviews")
    Call<ReviewDTO> createReview(@Body CreateReviewDTO body);

    @DELETE("api/reviews/{id}")
    Call<Void> deleteReview(@Path("id") Long reviewId);


}

