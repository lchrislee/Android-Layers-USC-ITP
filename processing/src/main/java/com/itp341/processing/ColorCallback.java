package com.itp341.processing;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * A callback interface to inform something about the results of the color processing.
 */
public interface ColorCallback {

    /**
     * Receive an updated response to whether the submitted colors were successfully processed. This
     * allows live updates to the user without explicitly waiting for any processing.
     *
     * @param didSucceed True when all submitted colors were processed. False otherwise.
     */
    public void onProcessColorResponse(final boolean didSucceed);

    /**
     * Receive the list of sorted colors after processing. The manner that the colors were sorted is
     * the most recent selection.
     *
     * @param sortedColors The list of sorted colors.
     */
    public void onSortedColors(final ArrayList<Color> sortedColors);
}
