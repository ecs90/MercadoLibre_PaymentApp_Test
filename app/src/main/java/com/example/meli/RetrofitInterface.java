package com.example.meli;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //@Path allows to search for specific fields inside the URL
    @GET("payment_methods")
    Call<List<PaymentMethodModel>> getSpecificPaymentMethods(@Query("public_key") String PUBLIC_KEY);

    @GET("payment_methods/card_issuers")
    Call<List<BankModel>> getBanksByPaymentMethod(@Query("public_key") String PUBLIC_KEY,
                                                  @Query("payment_method_id") String payment_id);

    @GET("payment_methods/installments")
    Call<List<InstallmentsModel>> getInstallments(@Query("public_key") String PUBLIC_KEY,
                                                  @Query("amount") String amount,
                                                  @Query("payment_method") String payment_method);
}
