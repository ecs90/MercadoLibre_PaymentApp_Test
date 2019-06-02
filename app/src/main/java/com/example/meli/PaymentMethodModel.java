package com.example.meli;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethodModel {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("payment_type_id")
    @Expose
    public String type_id;

    @SerializedName("thumbnail")
    @Expose
    public String URLimage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getURLimage() {
        return URLimage;
    }

    public void setURLimage(String URLimage) {
        this.URLimage = URLimage;
    }
}
