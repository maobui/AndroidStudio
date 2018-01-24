package com.me.bui.mvvm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.me.bui.mvvm.R;
import com.me.bui.mvvm.model.Language;

import java.util.List;

/**
 * Created by mao.bui on 1/18/2018.
 */

public class LanguageSpinnerAdapter extends ArrayAdapter<Language> {

    public LanguageSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Language> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    @NonNull
    private View getCustomView(final int position, @Nullable final View convertView) {
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            view = inflateView();
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            holder = new ViewHolder(textView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Language language = getItem(position);
        holder.bind(language.getName());

        return view;
    }

    @NonNull
    private View inflateView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.language_item, null);
    }

    private class ViewHolder {

        @NonNull
        private final TextView mText;

        public ViewHolder(@NonNull final TextView text) {
            mText = text;
        }

        public void bind(@NonNull final String text) {
            mText.setText(text);
        }
    }
}
