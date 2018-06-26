package com.mercadopago.onetap.components;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mercadopago.R;
import com.mercadopago.components.CompactComponent;
import com.mercadopago.internal.repository.PaymentSettingRepository;
import com.mercadopago.model.Campaign;
import com.mercadopago.model.CardPaymentMetadata;
import com.mercadopago.model.PayerCost;
import com.mercadopago.onetap.OneTap;
import com.mercadopago.util.ViewUtils;
import com.mercadopago.util.textformatter.AmountFormatter;
import com.mercadopago.util.textformatter.CurrencyFormatter;
import com.mercadopago.util.textformatter.TextFormatter;
import com.mercadopago.viewmodel.OneTapModel;
import java.math.BigDecimal;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class Amount extends CompactComponent<Amount.Props, OneTap.Actions> {

    /* default */ static class Props {
        /* default */ @NonNull final PaymentSettingRepository config;
        /* default */ @Nullable final PayerCost payerCost;
        /* default */ final int installment;

        /* default */ Props(@NonNull final PaymentSettingRepository config,
            @Nullable final PayerCost payerCost,
            final int installment) {
            this.config = config;
            this.payerCost = payerCost;
            this.installment = installment;
        }


        /* default */ static Props from(final OneTapModel props,
            final PaymentSettingRepository configuration) {
            final CardPaymentMetadata card = props.getPaymentMethods().getOneTapMetadata().getCard();
            final PayerCost payerCost = card != null ? card.getAutoSelectedInstallment() : null;
            return new Amount.Props(
                configuration,
                payerCost,
                payerCost == null ? PayerCost.NO_INSTALLMENTS : payerCost.getInstallments());
        }

        /* default */ boolean hasDiscount() {
            return config.getDiscount() != null;
        }

        /* default */ boolean shouldShowPercentOff() {
            return hasDiscount() && config.getDiscount().hasPercentOff();
        }

        /* default */ boolean hasMultipleInstallments() {
            return installment > 1;
        }

        /* default */ boolean hasMaxDiscountLabel() {
            final Campaign campaign = config.getCampaign();
            return campaign != null && !BigDecimal.ZERO.equals(campaign.getMaxCouponAmount());
        }

        /* default */ String getCurrencyId() {
            return config.getCheckoutPreference().getSite().getCurrencyId();
        }
    }

    /* default */ Amount(final Props props, final OneTap.Actions callBack) {
        super(props, callBack);
    }

    @Override
    public View render(@Nonnull final ViewGroup parent) {
        final View content = inflate(parent, R.layout.mpsdk_compact_amount);
        final ViewGroup discountLayout = content.findViewById(R.id.discount_detail_layout);
        resolveSmallAmountPlusDiscount(content);
        resolveBigAmount(content);
        resolveOffAmount(discountLayout);
        resolveMaxDiscount(discountLayout);
        resolveArrow(content);
        return content;
    }

    private void resolveArrow(@NonNull final View content) {
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                getActions().onAmountShowMore();
            }
        });
    }

    private void resolveOffAmount(@NonNull final ViewGroup discountLayout) {

        final TextView discountMessage = discountLayout.findViewById(R.id.discount_message);
        ViewUtils.showOrGone(discountLayout, props.hasDiscount());

        if (props.shouldShowPercentOff()) {
            TextFormatter.withCurrencyId(props.getCurrencyId())
                .noSpace().noSymbol()
                .amount(props.config.getDiscount().getPercentOff())
                .normalDecimals()
                .into(discountMessage)
                .holder(R.string.mpsdk_discount_percent_off_percent);
        } else if (props.hasDiscount()) {
            TextFormatter.withCurrencyId(props.getCurrencyId())
                .withSpace()
                .amount(props.config.getDiscount().getAmountOff())
                .normalDecimals()
                .into(discountMessage)
                .holder(R.string.mpsdk_discount_percent_off_amount);
        }

        ViewUtils.showOrGone(discountMessage, props.hasDiscount());
    }

    private void resolveMaxDiscount(@NonNull final ViewGroup discountLayout) {
        final TextView discountMaxLabel = discountLayout.findViewById(R.id.discount_max_label);
        ViewUtils.showOrGone(discountMaxLabel, props.hasDiscount() && props.hasMaxDiscountLabel());
    }

    private void resolveSmallAmountPlusDiscount(final View content) {
        final TextView amount = content.findViewById(R.id.amount);
        TextFormatter.withCurrencyId(props.getCurrencyId())
            .withSpace()
            .amount(props.config.getCheckoutPreference().getTotalAmount())
            .normalDecimals()
            .into(amount)
            .strike()
            .visible(props.hasDiscount() && !props.hasMultipleInstallments());
    }

    private void resolveBigAmount(final View content) {
        // amount with discount included.
        final TextView amountWithDiscount = content.findViewById(R.id.amount_with_discount);

        final CurrencyFormatter currencyFormatter = TextFormatter.withCurrencyId(props.getCurrencyId())
            .withSpace();

        final AmountFormatter amountFormatter = props.hasMultipleInstallments() ?
            currencyFormatter.amount(props.payerCost.getInstallmentAmount()) :
            currencyFormatter.amount(resolveAmountWithDiscount(props.config.getCheckoutPreference().getTotalAmount()));

        final CharSequence charSequence = amountFormatter
            .smallDecimals()
            .into(amountWithDiscount)
            .toSpannable();

        if (props.hasMultipleInstallments()) {
            final String x = amountWithDiscount.getContext().getString(R.string.mpsdk_installments_by);
            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(props.payerCost.getInstallments().toString())
                .append(x)
                .append(" ")
                .append(charSequence);
            amountWithDiscount.setText(spannableStringBuilder);
        } else {
            amountWithDiscount.setText(charSequence);
        }
    }

    private BigDecimal resolveAmountWithDiscount(@NonNull final BigDecimal amount) {
        return !props.hasDiscount() ? amount : amount.subtract(props.config.getDiscount().getCouponAmount());
    }
}
