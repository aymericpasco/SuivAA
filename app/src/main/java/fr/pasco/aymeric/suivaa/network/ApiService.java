package fr.pasco.aymeric.suivaa.network;

import fr.pasco.aymeric.suivaa.entities.AccessToken;
import fr.pasco.aymeric.suivaa.entities.VisitResponse;
import retrofit2.Call;
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

    @GET("visits")
    Call<VisitResponse> visits();

}
