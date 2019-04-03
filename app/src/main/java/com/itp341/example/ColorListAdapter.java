package com.itp341.example;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorListAdapter extends ArrayAdapter<Color> {
    public ColorListAdapter(final Context context) {
        super(context, R.layout.list_item_color);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_color, parent, false);
        }

        final Color colorAtPosition = getItem(position);
        final TextView colorText = convertView.findViewById(R.id.colorText);
        colorText.setText(colorAtPosition.toString());
        final View background = convertView.findViewById(R.id.colorBackground);
        background.setBackgroundColor(colorAtPosition.toArgb());
        return convertView;
    }

    public void clearAndDisplay(final ArrayList<Color> newColors) {
        clear();
        addAll(newColors);
        notifyDataSetChanged();
    }
}
