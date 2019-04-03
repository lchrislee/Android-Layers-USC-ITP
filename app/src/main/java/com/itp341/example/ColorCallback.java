package com.itp341.calculations;

import android.graphics.Color;

import java.util.ArrayList;

public interface ColorCallback {
    public void onProcessColorResponse(final boolean didSucceed);
    public void onSortedColors(final ArrayList<Color> sortedColors);
}
