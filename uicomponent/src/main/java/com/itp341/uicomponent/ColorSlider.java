package com.itp341.uicomponent;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;

/**
 * A custom view dedicated to generating a {@link Color}. This view uses three {@link SeekBar}s
 * representing Red, Green, and Blue color channels.
 */
public class ColorSlider extends LinearLayout {

    private static final float MAX_PROGRESS = 255;
    private static final int INITIAL_PROGRESS = (int) (MAX_PROGRESS / 2);

    private SeekBar redBar, greenBar, blueBar;
    private TextView redText, greenText, blueText;

    private SeekBar.OnSeekBarChangeListener internalSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(final SeekBar seekBar, final int progress,
                                              final boolean fromUser) {
                    if (fromUser) {
                        final TextView textToChange = mapping.get(seekBar);
                        textToChange.setText(String.valueOf(progress));
                    }
                }

                @Override
                public void onStartTrackingTouch(final SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(final SeekBar seekBar) {

                }
            };

    private HashMap<SeekBar, TextView> mapping = new HashMap<>();

    public ColorSlider(final Context context) {
        super(context);
        initialize();
    }

    public ColorSlider(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ColorSlider(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    /**
     * All these constructors are necessary by the OS for the different ways to inflate this view.
     * We don't care too much about the specifics now but more details are available online.
     */
    public ColorSlider(final Context context, final AttributeSet attrs, final int defStyleAttr,
                       final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    /**
     * Regardless of how the view was created, it needs to be filled up with some content. In this
     * case, we use static content. The content is inflated and then attached to this view.
     */
    private void initialize() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_color_slider, this);
    }

    /**
     * Once the view is guaranteed to be initialized and in a stable state, grab references to the
     * static views and attach appropriate listeners on them.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        redBar = findViewById(R.id.skbarRedValue);
        redBar.setMax((int) MAX_PROGRESS);
        redBar.setProgress(INITIAL_PROGRESS, false);
        redBar.setOnSeekBarChangeListener(internalSeekBarListener);
        redText = findViewById(R.id.txtRedValue);
        redText.setText(String.valueOf(redBar.getProgress()));
        mapping.put(redBar, redText);

        greenBar = findViewById(R.id.skbarGreenValue);
        greenBar.setMax((int) MAX_PROGRESS);
        greenBar.setProgress(INITIAL_PROGRESS, false);
        greenBar.setOnSeekBarChangeListener(internalSeekBarListener);
        greenText = findViewById(R.id.txtGreenValue);
        greenText.setText(String.valueOf(redBar.getProgress()));
        mapping.put(greenBar, greenText);

        blueBar = findViewById(R.id.skbarBlueValue);
        blueBar.setMax((int) MAX_PROGRESS);
        blueBar.setProgress(INITIAL_PROGRESS, false);
        blueBar.setOnSeekBarChangeListener(internalSeekBarListener);
        blueText = findViewById(R.id.btnBlueValue);
        blueText.setText(String.valueOf(redBar.getProgress()));
        mapping.put(blueBar, blueText);
    }

    /**
     * Grab the current values from the three Seekbars and generate a Color for consumption.
     *
     * @return The Color generated from the information given in the Seekbars.
     */
    public Color getColor() {
        return Color.valueOf(getAdjustedValue(redBar.getProgress()),
                getAdjustedValue(greenBar.getProgress()),
                getAdjustedValue(blueBar.getProgress()));
    }

    private float getAdjustedValue(final int progress) {
        return progress / MAX_PROGRESS;
    }

}
