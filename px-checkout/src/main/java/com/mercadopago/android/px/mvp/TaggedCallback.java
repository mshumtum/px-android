package com.mercadopago.android.px.mvp;

import com.mercadopago.android.px.exceptions.MercadoPagoError;
import com.mercadopago.android.px.services.callbacks.Callback;
import com.mercadopago.android.px.services.exceptions.ApiException;

/**
 * All ResourcesProvider implementations' methods containing api calls
 * MUST receive as @param an <code>TaggedCallback</code>.
 * <p>
 * See also {@link ResourcesProvider}
 */

public abstract class TaggedCallback<T> extends Callback<T> {

    private final String tag;

    public TaggedCallback(String tag) {
        this.tag = tag;
    }

    @Override
    public void success(final T t) {
        onSuccess(t);
    }

    @Override
    public void failure(final ApiException apiException) {
        onFailure(new MercadoPagoError(apiException, tag));
    }

    public abstract void onSuccess(final T t);

    public abstract void onFailure(MercadoPagoError error);
}

