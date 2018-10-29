package com.debruyckere.florian.mynews.model;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.DatePicker;

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
        int realMonth = month+1;
        String s = year+" "+realMonth+" "+dayOfMonth;
        mClickedButton.setText(s);
    }

    /**
     * set the clicked button
     * @param pButton the clicked button
     */
    public void setButton(Button pButton){
        mClickedButton = pButton;
    }
}
