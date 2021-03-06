package com.mercadopago.android.px.plugins;

import android.content.Context;
import android.support.annotation.NonNull;
import java.util.Map;

public abstract class PaymentProcessor {

    public static final String PAYMENT_PROCESSOR_KEY = "payment_processor";

    public boolean support(@NonNull final String paymentMethodId,
        @NonNull final Map<String, Object> data) {
        return true;
    }

    @NonNull
    public abstract PluginComponent createPaymentComponent(@NonNull final PluginComponent.Props props,
        @NonNull final Context context);
}
