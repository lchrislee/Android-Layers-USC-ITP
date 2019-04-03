package com.itp341.example;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

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

    private Button submitButton;
    private Button inOrderButton;
    private Button sortedButton;
    private ListView colorList;
    private ArrayAdapter<Color> colorListAdapter;

    private ArrayList<Color> allSubmissions = new ArrayList<>();
    private HashSet<Color> uniqueColors = new HashSet<>();
    private boolean isSorted = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        colorList = findViewById(R.id.listColorList);
        colorListAdapter = new ColorListAdapter(this);
        colorList.setAdapter(colorListAdapter);
        submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Color selectedColor = getColor();
                if (uniqueColors.contains(selectedColor)) {
                    Toast.makeText(MainActivity.this, "You've already used this color", Toast.LENGTH_SHORT).show();
                    return;
                }

                uniqueColors.add(selectedColor);
                allSubmissions.add(selectedColor);
                processColors();
            }
        });
        inOrderButton = findViewById(R.id.btnInOrder);
        inOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                isSorted = false;
                processColors();
            }
        });
        sortedButton = findViewById(R.id.btnSorted);
        sortedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                isSorted = true;
                processColors();
            }
        });
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

    private void processColors() {
        colorListAdapter.clear();
        colorListAdapter.addAll(allSubmissions);
        if (isSorted) {
            colorListAdapter.sort(colorComparator);
        }
        colorListAdapter.notifyDataSetChanged();
    }

    private double distance(@NonNull final Color color) {
        return Math.sqrt(Math.pow(color.red(), 2) + Math.pow(color.green(), 2) + Math.pow(color.blue(), 2));
    }

    public Color getColor() {
        return Color.valueOf(getAdjustedValue(redBar.getProgress()),
                getAdjustedValue(greenBar.getProgress()),
                getAdjustedValue(blueBar.getProgress()));
    }

    private float getAdjustedValue(final int progress) {
        return progress / MAX_PROGRESS;
    }

}
