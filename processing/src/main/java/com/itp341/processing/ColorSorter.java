package com.itp341.processing;

import android.graphics.Color;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * A class dedicated to sorting colors.
 *
 * Note that this class only has two explicit Android dependencies. If we remove them or trade for
 * something else, this class could be used in a pure Java project.
 */
public class ColorSorter {

    /**
     * By using a reference to {@link ColorCallback}, this eliminates the risk of a strong cyclical
     * dependency. While we still hold a strong reference, the object on the receiving end could
     * theoretically be anything (GUI, CLI, etc.).
     */
    private ColorCallback callback;

    private ArrayList<Color> allSubmissions = new ArrayList<>();
    private HashSet<Color> uniqueColors = new HashSet<>();
    private boolean isSorted = false;

    public ColorSorter(final ColorCallback inputCallback) {
        callback = inputCallback;
    }

    /**
     * Adds a color for processing. Once a color is added, it is always tracked and cannot be
     * removed.
     *
     * Upon adding a unique color, this will trigger a callback with the updated list after
     * processing all the existing data.
     *
     * Adding duplicate colors will trigger a failure callback.
     *
     * @param selectedColor The color to process.
     */
    public void addColor(final Color selectedColor) {
        if (uniqueColors.contains(selectedColor)) {
            callback.onProcessColorResponse(false);
            return;
        }
        uniqueColors.add(selectedColor);
        allSubmissions.add(selectedColor);
        processColors();
        callback.onProcessColorResponse(true);
    }

    /**
     * Creates a new list of colors from all valid submissions. Then sorts appropriately before
     * triggering the callback.
     *
     * Since this creates a new list every time, there is no need to clear any data. Additionally,
     * we know the list must have all elements, so we can initialize the new list using the previous.
     */
    private void processColors() {
        final ArrayList<Color> outputList = new ArrayList<>(allSubmissions);
        if (isSorted) {
            outputList.sort(colorComparator);
        }
        callback.onSortedColors(outputList);
    }

    /**
     * Register whether future color outputs should be sorted in a particular way.
     *
     * Additionally, this will trigger an immediate processing call and callback with the updated
     * order.
     *
     * @param sorted True when the color processing should sort using our internal methods.
     */
    public void setSorted(final boolean sorted) {
        isSorted = sorted;
        processColors();
    }

    private Comparator<Color> colorComparator = new Comparator<Color>() {
        @Override
        public int compare(final Color o1, final Color o2) {
            final double o1Distance = distance(o1);
            final double o2Distance = distance(o2);
            if (o1Distance < o2Distance) {
                return -1;
            } else if (o1Distance > o2Distance) {
                return 1;
            } else if (o1.red() < o2.red()) {
                return -1;
            } else if (o1.red() > o2.red()) {
                return 1;
            } else if (o1.green() < o2.green()) {
                return -1;
            } else if (o1.green() > o2.green()) {
                return 1;
            } else {
                return Float.compare(o1.blue(), o2.blue());
            }
        }
    };

    private double distance(@NonNull final Color color) {
        return Math.sqrt(Math.pow(color.red(), 2) + Math.pow(color.green(), 2) + Math.pow(color.blue(), 2));
    }
}
