package com.mercadopago.android.px.utils;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.model.Site;
import com.mercadopago.android.px.model.Sites;
import com.mercadopago.android.px.preferences.FlowPreference;
import com.mercadopago.android.px.preferences.ServicePreference;
import com.mercadopago.android.px.util.TextUtils;
import java.util.List;

public class CheckoutConfiguration {
    private String startFor;
    private String prefId;
    private String publicKey;
    private List<Item> items;
    private String payerEmail;
    private String siteId;
    private FlowPreference flowPreference;
    private ServicePreference servicePreference;

    @SerializedName(value = "timer")
    private Integer time;

    public String getStartFor() {
        return startFor;
    }

    public String getPrefId() {
        return prefId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public boolean paymentRequired() {
        return "payment".equals(startFor);
    }

    public boolean paymentDataRequired() {
        return "payment_data".equals(startFor);
    }

    public List<Item> getItems() {
        return items;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public Site getSite() {
        return TextUtils.isEmpty(siteId) ? null : Sites.getById(siteId);
    }

    public Integer getTime() {
        return time;
    }

    public FlowPreference getFlowPreference() {
        return flowPreference;
    }

    public ServicePreference getServicePreference() {
        return servicePreference;
    }

    public void setServicePreference(ServicePreference servicePreference) {
        this.servicePreference = servicePreference;
    }
}
