package com.mercadopago.components;

import android.support.annotation.NonNull;

import com.mercadopago.lite.util.CurrenciesUtil;
import com.mercadopago.model.Discount;
import com.mercadopago.model.PayerCost;

import com.mercadopago.util.textformatter.TextFormatter;
import java.math.BigDecimal;
import java.util.Locale;

public class TotalAmount extends Component<TotalAmount.TotalAmountProps, Void> {

    static {
        RendererFactory.register(TotalAmount.class, TotalAmountRenderer.class);
    }

    public static class TotalAmountProps {

        public final PayerCost payerCost;
        public final Discount discount;
        public final String currencyId;
        public final BigDecimal amount;

        public TotalAmountProps(final String currencyId,
            final BigDecimal amount,
            final PayerCost payerCost,
            final Discount discount) {
            this.payerCost = payerCost;
            this.discount = discount;
            this.currencyId = currencyId;
            this.amount = amount;
        }
    }

    public TotalAmount(@NonNull final TotalAmountProps props) {
        super(props);
    }

    public String getAmountTitle() {
        String amountTitle;

        if (hasPayerCostWithMultipleInstallments()) {
            String installmentsAmount = CurrenciesUtil
                .getLocalizedAmountWithoutZeroDecimals(props.currencyId, props.payerCost.getInstallmentAmount());
            amountTitle = String.format(Locale.getDefault(),
                "%dx %s",
                props.payerCost.getInstallments(),
                installmentsAmount);
        } else {
            amountTitle = CurrenciesUtil.getLocalizedAmountWithoutZeroDecimals(props.currencyId, getAmount());
        }

        return amountTitle;
    }

    public String getAmountDetail() {
        String amountDetail = "";

        if (hasPayerCostWithMultipleInstallments()) {
            amountDetail = String.format(Locale.getDefault(), "(%s)", CurrenciesUtil
                .getLocalizedAmountWithoutZeroDecimals(props.currencyId, props.payerCost.getTotalAmount()));
        }

        return amountDetail;
    }

    public boolean hasPayerCostWithMultipleInstallments() {
        return props.payerCost != null && props.payerCost.hasMultipleInstallments();
    }

    private BigDecimal getAmount() {
        BigDecimal amount;

        if (props.discount != null) {
            amount = getAmountWithDiscount();
        } else {
            amount = props.amount;
        }

        return amount;
    }

    private BigDecimal getAmountWithDiscount() {
        return props.amount.subtract(props.discount.getCouponAmount());
    }
}
