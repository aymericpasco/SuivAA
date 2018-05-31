package fr.pasco.aymeric.suivaa.network;

import java.util.List;
import java.util.Observable;

import fr.pasco.aymeric.suivaa.entities.AccessToken;
import fr.pasco.aymeric.suivaa.entities.Doctor;
import fr.pasco.aymeric.suivaa.entities.Visit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    /*@GET("visits")
    Call<VisitResponse> visits();*/

    @GET("visits")
    Call<List<Visit>> getVisits();

    @POST("visits")
    Call<Visit> createVisit(@Body Visit visit);

    @GET("doctors")
    Call<List<Doctor>> getDoctors();

}
