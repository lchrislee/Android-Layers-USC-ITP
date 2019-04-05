package com.itp341.example;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.itp341.processing.ColorCallback;
import com.itp341.processing.ColorSorter;
import com.itp341.uicomponent.ColorSlider;

import java.util.ArrayList;

/**
 * A screen that allows creation of Colors and displays them in some order in a list.
 */
public class MainActivity extends AppCompatActivity implements ColorCallback {

    /**
     * A custom view to handle creation/selection of colors.
     */
    private ColorSlider colorSlider;

    private Button submitButton;
    private Button inOrderButton;
    private Button sortedButton;
    private ListView colorList;

    /**
     * Our list's ArrayAdapter. Note that this is a custom subclass because we want the ability
     * to customize the visual appearance of the view in the ListView.
     */
    private ColorListAdapter colorListAdapter;

    /**
     * The processing unit used to sort the colors for the list. The constructor requires an object
     * that implements the {@link ColorCallback} interface. By making the Activity implement the
     * interface, we guarantee that the Activity both knows how to and wants to react to certain
     * outcomes from the {@link ColorSorter}.
     */
    private ColorSorter colorSorter = new ColorSorter(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorSlider = findViewById(R.id.colorSlider);

        colorList = findViewById(R.id.listColorList);
        colorListAdapter = new ColorListAdapter(this);
        colorList.setAdapter(colorListAdapter);
        submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Color selectedColor = colorSlider.getColor();
                colorSorter.addColor(selectedColor);
            }
        });
        inOrderButton = findViewById(R.id.btnInOrder);
        inOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                colorSorter.setSorted(false);
            }
        });
        sortedButton = findViewById(R.id.btnSorted);
        sortedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                colorSorter.setSorted(true);
            }
        });
    }

    /**
     * See {@link ColorCallback}.
     *
     * @param didSucceed True when all submitted colors were processed. False otherwise.
     */
    @Override
    public void onProcessColorResponse(final boolean didSucceed) {
        if (!didSucceed) {
            Toast.makeText(MainActivity.this, getString(R.string.alreadyUsingColor), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * See {@link ColorCallback}.
     *
     * @param sortedColors The list of sorted colors to display.
     */
    @Override
    public void onSortedColors(final ArrayList<Color> sortedColors) {
        colorListAdapter.clearAndDisplay(sortedColors);
    }
}
