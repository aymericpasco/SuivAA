package fr.pasco.aymeric.suivaa;

import java.io.IOException;
import java.lang.annotation.Annotation;

import fr.pasco.aymeric.suivaa.entities.ApiError;
import fr.pasco.aymeric.suivaa.network.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class Utils {

    public static ApiError converErrors(ResponseBody response) {
        Converter<ResponseBody, ApiError> converter =
                RetrofitBuilder.getRetrofit()
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError apiError = null;

        try {
            apiError = converter.convert(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiError;
    }

}
