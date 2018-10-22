package com.debruyckere.florian.mynews.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;

/**
 * Created by Debruyckère Florian on 03/10/2018.
 */
public class PickersDialogs extends DialogFragment {

    private DateSettings dateSettings;
    private Button clickedButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dateSettings = new DateSettings(getActivity());
        sendButton();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getActivity(),dateSettings,year,month,day);

        return dialog;
    }

    public void sendButton(){
        dateSettings.getButton(clickedButton);
    }
    public void setButton(Button pButton){
        clickedButton = pButton;
    }
}
