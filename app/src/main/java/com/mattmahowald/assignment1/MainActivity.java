package com.mattmahowald.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private int beers;
    private int shots;
    private int wine;
    private int drinks = 0;
    private static final int DIGITS_IN_BAC = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private double calculateBAC() {
        EditText weightField = (EditText) findViewById(R.id.weight);
        EditText ageField = (EditText) findViewById(R.id.weight);
        int weight = Integer.parseInt(weightField.getText().toString());
        int age = Integer.parseInt(ageField.getText().toString());
        if(age < 21) return -1;
        RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
        int gid = gender.getCheckedRadioButtonId();
        double r = gid ==R.id.maleRadio ? 0.68 : 0.55;
        double BAC = (double) (drinks * 14) / (weight * 453.592) * 100;
        return BAC;
    }

    private String BACDoubleToString(double BAC) {
        String BACString = ".";
        String fullBACString = Double.toString(BAC);
        int length = fullBACString.length();
        boolean pastDecimal = false;
        for(int i = 0; i < length; i++) {
            if(pastDecimal) BACString += fullBACString.charAt(i);
            if(BACString.length() == DIGITS_IN_BAC + 1) break;
            if(fullBACString.charAt(i) == '.') pastDecimal = true;
        }
        while(BACString.length() < DIGITS_IN_BAC + 1) {
            BACString += "0";
        }
        return BACString;
    }

    public void drink(View view) {
        drinks++;
        TextView drinkView = (TextView) findViewById(R.id.drinks);

        int id = view.getId();

        double BAC = calculateBAC();
        if (BAC == -1) {
            Toast.makeText(this, "You're not 21!", Toast.LENGTH_SHORT);
            return;
        }
        if(id == R.id.beerButton) {
            beers++;
        } else if (id == R.id.wineButton) {
            wine++;
        } else if (id == R.id.shotButton) {
            shots++;
        }
        drinkView.setText("Drinks: " + Integer.toString(drinks));
        String BACString = BACDoubleToString(BAC);
        TextView BACView = (TextView) findViewById(R.id.BAC);
        BACView.setText(BACString);
    }
}
