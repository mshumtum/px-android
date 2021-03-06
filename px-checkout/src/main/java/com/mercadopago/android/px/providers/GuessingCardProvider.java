package com.mercadopago.android.px.providers;

import com.mercadopago.android.px.model.BankDeal;
import com.mercadopago.android.px.model.CardToken;
import com.mercadopago.android.px.model.IdentificationType;
import com.mercadopago.android.px.model.Installment;
import com.mercadopago.android.px.model.Issuer;
import com.mercadopago.android.px.model.PaymentMethod;
import com.mercadopago.android.px.model.Token;
import com.mercadopago.android.px.mvp.ResourcesProvider;
import com.mercadopago.android.px.mvp.TaggedCallback;
import com.mercadopago.android.px.tracker.MPTrackingContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by vaserber on 8/24/17.
 */

public interface GuessingCardProvider extends ResourcesProvider {

    MPTrackingContext getTrackingContext();

    void getPaymentMethodsAsync(final TaggedCallback<List<PaymentMethod>> taggedCallback);

    void createTokenAsync(CardToken cardToken, final TaggedCallback<Token> taggedCallback);

    void getIssuersAsync(String paymentMethodId, String bin, final TaggedCallback<List<Issuer>> taggedCallback);

    void getInstallmentsAsync(String bin, BigDecimal amount, Long issuerId, String paymentMethodId,
        final TaggedCallback<List<Installment>> taggedCallback);

    void getIdentificationTypesAsync(final TaggedCallback<List<IdentificationType>> taggedCallback);

    void getBankDealsAsync(final TaggedCallback<List<BankDeal>> taggedCallback);

    String getMissingInstallmentsForIssuerErrorMessage();

    String getMultipleInstallmentsForIssuerErrorMessage();

    String getMissingPayerCostsErrorMessage();

    String getMissingIdentificationTypesErrorMessage();

    String getMissingPublicKeyErrorMessage();

    String getInvalidIdentificationNumberErrorMessage();

    String getInvalidExpiryDateErrorMessage();

    String getInvalidEmptyNameErrorMessage();

    String getSettingNotFoundForBinErrorMessage();

    String getInvalidFieldErrorMessage();
}
