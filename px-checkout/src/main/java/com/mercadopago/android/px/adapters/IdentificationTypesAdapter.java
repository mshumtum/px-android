package com.mercadopago.android.px.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.customviews.MPTextView;
import com.mercadopago.android.px.model.IdentificationType;
import java.util.List;

public class IdentificationTypesAdapter extends BaseAdapter {

    private final List<IdentificationType> mData;

    public IdentificationTypesAdapter(List<IdentificationType> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        try {
            return mData.get(position);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (convertView == null) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.px_row_simple_spinner, parent, false);
        }

        IdentificationType identificationType = mData.get(position);
        MPTextView label = row.findViewById(R.id.mpsdkItemTitle);
        label.setText(identificationType.getName());
        return row;
    }
}
