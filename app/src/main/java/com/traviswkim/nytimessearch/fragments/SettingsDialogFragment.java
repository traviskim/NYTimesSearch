package com.traviswkim.nytimessearch.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.models.SearchSetting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by traviswkim on 7/8/16.
 */
public class SettingsDialogFragment extends DialogFragment {
    private EditText mBeginDate;
    private Spinner mSortOrder;
    private CheckBox mArts;
    private CheckBox mFashionStyle;
    private CheckBox mSports;
    private final String myFormat = "MM/dd/yyyy";
    SimpleDateFormat spf = new SimpleDateFormat(myFormat);
    Calendar myCalendar = Calendar.getInstance();
    final long today = System.currentTimeMillis() - 1000;
    Date selectedDate;
    private SearchSetting ss;

    public interface SettingsDialogListener {
        void onFinishInputDialog(SearchSetting newSs);
    }

    public SettingsDialogFragment(){
    }

    public static SettingsDialogFragment newInstance(SearchSetting ss){
        SettingsDialogFragment frag = new SettingsDialogFragment();
        Bundle args = new Bundle();
        args.putString("beginDate", ss.getBeginDate());
        args.putString("sortOrder", ss.getSortOrder());
        args.putBooleanArray("newsDeskValues", new boolean[]{ss.isArts(), ss.isFasionStyle(), ss.isSports()});
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings, container);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBeginDate = (EditText) view.findViewById(R.id.etBeginDate);
        mSortOrder = (Spinner) view.findViewById(R.id.spnSortOrder);
        mArts = (CheckBox) view.findViewById(R.id.cbArts);
        mFashionStyle = (CheckBox) view.findViewById(R.id.cbFasionStyle);
        mSports = (CheckBox) view.findViewById(R.id.cbSports);
        Button mSubmit = (Button) view.findViewById(R.id.btnSave);
        Button mCancel = (Button) view.findViewById(R.id.btnCancel);

        // Fetch arguments from bundle
        final String beginDate = getArguments().getString("beginDate", "");
        final String sortOrder = getArguments().getString("sortOrder", "");
        boolean[] newsDeskValues = getArguments().getBooleanArray("newsDeskValues");

        getDialog().setTitle("Settings");
        try{
            if(beginDate != ""){
                selectedDate = spf.parse(beginDate);
            }
        }catch(ParseException e){
            selectedDate = null;
        }

        //Set previous values if exits
        mBeginDate.setText(beginDate);
        mBeginDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(selectedDate != null){
                    myCalendar.setTime(selectedDate);
                }
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), date, year, month, day);
                //dpd.getDatePicker().setMinDate(today);
                dpd.show();
            }
        });

        ArrayAdapter<CharSequence> sortOrderAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.sortorder_array, android.R.layout.simple_spinner_item);
        mSortOrder.setAdapter(sortOrderAdapter);
        int selectedPos = sortOrderAdapter.getPosition(sortOrder);
        mSortOrder.setSelection(selectedPos);

        mArts.setChecked(newsDeskValues[0]);
        mFashionStyle.setChecked((newsDeskValues[1]));
        mSports.setChecked((newsDeskValues[2]));

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialogListener listener = (SettingsDialogListener)getActivity();
                SearchSetting newSs = new SearchSetting(mBeginDate.getText().toString(), mSortOrder.getSelectedItem().toString(), mArts.isChecked(), mFashionStyle.isChecked(), mSports.isChecked());
                listener.onFinishInputDialog(newSs);
                dismiss();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateBeginDate();
        }

    };

    private void updateBeginDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mBeginDate.setText(sdf.format(myCalendar.getTime()));
    }
}
