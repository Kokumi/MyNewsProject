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
    String mDate;

    public DateSettings(Context pContext){
        mContext = pContext;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int realMonth = month+1;
        String s;
        if(realMonth < 10){
            if(dayOfMonth < 10){
                s = year+" 0"+realMonth+" 0"+dayOfMonth;
            }
            else {
                s = year+" 0"+realMonth+" "+dayOfMonth;
            }

        }
        else {
            if(dayOfMonth < 10){
                s = year+" "+realMonth+" 0"+dayOfMonth;
            }
            else {
                s = year+" "+realMonth+" "+dayOfMonth;
            }

        }
        mDate = s;
        mClickedButton.setText(s);
    }

    /**
     * set the clicked button
     * @param pButton the clicked button
     */
    public void setButton(Button pButton){
        mClickedButton = pButton;
    }

    //Only for unit test
    public String dateSetTest(int year,int month, int dayOfMonth){
        onDateSet(new DatePicker(mContext),year,month,dayOfMonth);
        return mDate;
    }
}
