package com.mercadopago.android.px.components;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mercadopago.android.px.R;
import com.mercadopago.android.px.model.Campaign;
import com.mercadopago.android.px.model.Discount;
import com.mercadopago.android.px.util.textformatter.TextFormatter;

import javax.annotation.Nonnull;

public class DiscountDetail extends CompactComponent<DiscountDetail.Props, Void> {

    public static class Props {

        @NonNull
        private final Discount discount;
        @NonNull
        private final Campaign campaign;

        public Props(@NonNull final Discount discount, @NonNull final Campaign campaign) {
            this.discount = discount;
            this.campaign = campaign;
        }
    }

    public DiscountDetail(final Props props) {
        super(props);
    }

    @Override
    public View render(@Nonnull final ViewGroup parent) {
        final View mainContainer = inflate(parent, R.layout.px_view_discount_detail);
        configureSubtitleMessage(mainContainer);
        configureDetailMessage(mainContainer);
        return mainContainer;
    }

    private void configureSubtitleMessage(final View mainContainer) {
        final TextView subtitleMessage = mainContainer.findViewById(R.id.subtitle);
        if (props.campaign.hasMaxCouponAmount()) {
            TextFormatter.withCurrencyId(props.discount.getCurrencyId())
                    .withSpace()
                    .amount(props.campaign.getMaxCouponAmount())
                    .normalDecimals()
                    .into(subtitleMessage)
                    .holder(R.string.px_max_coupon_amount);
        } else {
            subtitleMessage.setVisibility(View.GONE);
        }
    }

    private void configureDetailMessage(final View mainContainer) {
        final TextView detailMessage = mainContainer.findViewById(R.id.detail);
        if (props.campaign.hasMaxCouponAmount()) {

            if (props.campaign.hasEndDate()) {
                detailMessage.setText(mainContainer.getResources().getString(R.string.px_one_shot_discount_detail_with_end_date,
                        props.campaign.getPrettyEndDate()));
            } else {
                detailMessage.setText(R.string.px_one_shot_discount_detail);
            }

        } else {
            detailMessage.setVisibility(View.GONE);
        }
    }
}
