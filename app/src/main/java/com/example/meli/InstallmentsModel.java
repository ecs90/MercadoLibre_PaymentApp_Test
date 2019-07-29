package com.example.meli;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstallmentsModel {
    @SerializedName("installments")
    @Expose
    public int installments;

    @SerializedName("installment_rate")
    @Expose
    public int installment_rate;

    @SerializedName("recommended_message")
    @Expose
    public String recommended_message;

    @SerializedName("installment_amount")
    @Expose
    public int installment_amount;

    @SerializedName("total_amount")
    @Expose
    public int total_amount;

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public int getInstallment_rate() {
        return installment_rate;
    }

    public void setInstallment_rate(int installment_rate) {
        this.installment_rate = installment_rate;
    }

    public String getRecommended_message() {
        return recommended_message;
    }

    public void setRecommended_message(String recommended_message) {
        this.recommended_message = recommended_message;
    }

    public int getInstallment_amount() {
        return installment_amount;
    }

    public void setInstallment_amount(int installment_amount) {
        this.installment_amount = installment_amount;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
}
