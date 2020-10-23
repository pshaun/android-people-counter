package com.example.peoplecounter.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.peoplecounter.R;
import com.example.peoplecounter.model.CounterModel;

import androidx.appcompat.app.AppCompatActivity;

public class CounterActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPref;

    private CounterModel counter;

    private TextView totalTextView;
    private TextView currentTextView;
    private Button resetButton;
    private Button minusButton;
    private Button addButton;

    private void initUI() {
        //Initializing views
        totalTextView = findViewById(R.id.totalTextView);
        currentTextView = findViewById(R.id.currentTextView);
        resetButton = findViewById(R.id.resetButton);
        minusButton = findViewById(R.id.minusButton);
        addButton = findViewById(R.id.addButton);
        counter = new CounterModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        initUI();
        resetButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        //Restore shared preferences to get previous counter data
        //If nothing found, the current/total counters are set to 0 by default
        sharedPref = getSharedPreferences("com.example.peoplecounter", Context.MODE_PRIVATE);
        counter.setCurrentAmount(sharedPref.getInt(getString(R.string.curr_ppl), 0));
        counter.setTotalAmount(sharedPref.getInt(getString(R.string.total_ppl), 0));
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Save shared preferences, in this case we save the total number of people and
        //the current amount of people
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.total_ppl), counter.getTotalAmount());
        editor.putInt(getString(R.string.curr_ppl), counter.getCurrentAmount());
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addButton:
                counter.setCurrentAmount(counter.getCurrentAmount() + 1);
                counter.setTotalAmount(counter.getTotalAmount() + 1);
                break;
            case R.id.minusButton:
                counter.setCurrentAmount(counter.getCurrentAmount() - 1);
                break;
            case R.id.resetButton:
                counter.setTotalAmount(0);
                counter.setCurrentAmount(0);
                break;
        }
        updateUI();
    }

    public void updateUI() {
        totalTextView.setText(getString(R.string.total_ppl, counter.getTotalAmount()));
        currentTextView.setText(getString(R.string.curr_ppl, counter.getCurrentAmount()));
        //If current count of people is equal to zero, we hide the (-) button from the user
        minusButton.setVisibility(counter.getCurrentAmount() == 0 ? View.INVISIBLE : View.VISIBLE);
        //If the ​current count​ is more than 15 people, we set the text color to red to warn the employee that they're over capacity.
        currentTextView.setTextColor(counter.getCurrentAmount() > 15 ? getResources().getColor(R.color.colorWarning) : getResources().getColor(R.color.colorPrimary));

    }
}