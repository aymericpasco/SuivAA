package fr.pasco.aymeric.suivaa.entities;

import com.squareup.moshi.Json;

public class AccessToken {

    @Json(name = "token_type")
    String tokenTpe;

    @Json(name = "expires_in")
    int expiresIn;

    @Json(name = "access_token")
    String accessToken;

    @Json(name = "refresh_token")
    String refreshToken;

    public String getTokenTpe() {
        return tokenTpe;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setTokenTpe(String tokenTpe) {
        this.tokenTpe = tokenTpe;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
