package com.example.meli;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //@Path allows to search for specific fields inside the URL
    @GET("/payment_methods/{payment_type_id}")
    Call<ArrayList<PaymentMethodModel>> getSpecificPaymentMethods(
            @Query("public_key") String PUBLIC_KEY, @Path("payment_type_id") String paymentTypeId);
}
