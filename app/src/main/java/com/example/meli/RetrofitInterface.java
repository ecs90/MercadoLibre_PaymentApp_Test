package com.example.meli;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //@Path allows to search for specific fields inside the URL
    @GET("payment_methods")
    Call<List<PaymentMethodModel>> getSpecificPaymentMethods(@Query("public_key") String PUBLIC_KEY);
}
