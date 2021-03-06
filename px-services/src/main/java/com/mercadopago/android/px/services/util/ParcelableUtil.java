package com.mercadopago.android.px.services.util;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.math.BigDecimal;

public class ParcelableUtil {

    @Nullable
    public static BigDecimal getOptionalBigDecimal(final Parcel in) {
        if (in.readByte() == 0) {
            return null;
        } else {
            return new BigDecimal(in.readString());
        }
    }

    @NonNull
    public static BigDecimal getBigDecimal(final Parcel in) {
        return new BigDecimal(in.readString());
    }

    @Nullable
    public static Integer getOptionalInteger(final Parcel in) {
        if (in.readByte() == 0) {
            return null;
        } else {
            return in.readInt();
        }
    }

    public static void write(final Parcel dest, final BigDecimal number) {
        dest.writeString(number.toString());
    }

    public static void writeOptional(final Parcel dest, final BigDecimal number) {
        if (number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(number.toString());
        }
    }

    public static void writeOptional(final Parcel dest, final Integer number) {
        if (number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(number);
        }
    }
}
