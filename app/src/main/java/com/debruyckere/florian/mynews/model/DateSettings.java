package com.debruyckere.florian.mynews.model;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by Debruyckère Florian on 03/10/2018.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener {
    Context mContext;
    Button mClickedButton;

    public DateSettings(Context pContext){
        mContext = pContext;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String s = year+" / "+month+" / "+dayOfMonth;
        mClickedButton.setText(s);
    }

    public void getButton(Button pButton){
        mClickedButton = pButton;
    }
}
