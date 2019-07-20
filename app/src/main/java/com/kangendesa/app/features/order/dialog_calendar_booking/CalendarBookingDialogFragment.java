package com.kangendesa.app.features.order.dialog_calendar_booking;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.kangendesa.app.R;
import com.kangendesa.app.features.tour.detail.DetailTourActivity;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 05 Maret 2019
 */

public class CalendarBookingDialogFragment extends DialogFragment {

    /*@BindView(R.id.calendarView)
    CalendarView calendarView;*/

    public static final String TAG = "CalendarBookingDialogFragment";
    private static CalendarBookingDialog mCallback;

    private CalendarView calendarView;
    private Calendar lastSelectedCalendar = null;
    private ProgressDialog progressDialog;

    private ItemTour mItemTour;
    private ArrayList<Integer> myStringArray;
    private Date date;

    public static CalendarBookingDialogFragment newInstance(CalendarBookingDialog listener,
                                                    ItemTour itemTour) {
        Bundle args = new Bundle();
        args.putSerializable(Consts.ARG_DATA, itemTour);
        CalendarBookingDialogFragment fragment = new CalendarBookingDialogFragment();
        mCallback = listener;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemTour = (ItemTour) getArguments().getSerializable(Consts.ARG_DATA);
        initLoading();
    }

    private void initLoading() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Consts.STR_LOADING);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_calendar_booking, null);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        myStringArray = new ArrayList<Integer>();
        Log.d("Day", String.valueOf(mItemTour.getMConditionsDay().get(0)));
        for (int i = 0; i < mItemTour.getMConditionsDay().size(); i++) {
            Log.d("DayArr", String.valueOf(mItemTour.getMConditionsDay().get(i).getValue()));
            Log.d("Hari", String.valueOf(mItemTour.getMConditionsDay().get(i).getKey() + mItemTour.getMConditionsDay().get(i).getValue()));
            if (mItemTour.getMConditionsDay().get(i).getValue().equalsIgnoreCase("Minggu")) {
                myStringArray.add(1);
            } else if (mItemTour.getMConditionsDay().get(i).getValue().equalsIgnoreCase("Senin")) {
                myStringArray.add(2);
            } else if (mItemTour.getMConditionsDay().get(i).getValue().equalsIgnoreCase("Selasa")) {
                myStringArray.add(3);
            } else if (mItemTour.getMConditionsDay().get(i).getValue().equalsIgnoreCase("Rabu")) {
                myStringArray.add(4);
            } else if (mItemTour.getMConditionsDay().get(i).getValue().equalsIgnoreCase("Kamis")) {
                myStringArray.add(5);
            } else if (mItemTour.getMConditionsDay().get(i).getValue().equalsIgnoreCase("Jumat")) {
                myStringArray.add(6);
            } else if (mItemTour.getMConditionsDay().get(i).getValue().equalsIgnoreCase("Sabtu")) {
                myStringArray.add(7);
            }
        }

        lastSelectedCalendar = Calendar.getInstance();
        calendarView.setMinDate(lastSelectedCalendar.getTimeInMillis());
        Log.d("last", String.valueOf(lastSelectedCalendar));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar checkCalendar = Calendar.getInstance();
                checkCalendar.set(year, month, dayOfMonth);
                if (checkCalendar.equals(Calendar.getInstance()))
                    return;
                if (myStringArray.contains(checkCalendar.get(Calendar.DAY_OF_WEEK))) {
                    calendarView.setDate(Calendar.getInstance().getTimeInMillis());
                    Toast.makeText(getActivity(), "Tanggal Tidak Tersedia", Toast.LENGTH_SHORT).show();
                } else {
                    lastSelectedCalendar = checkCalendar;
                    date = lastSelectedCalendar.getTime();
//                    Toast.makeText(getActivity(), "Tanggal tersedia"
//                            + sdf.format(date), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ButterKnife.bind(this, view);

        builder.setView(view)
                .setTitle("Booking Date")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        CalendarBookingDialogFragment.this.getDialog().cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnOk = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id"));
                        mCallback.onCalendarBookingDialog(sdf.format(date));
//                        Toast.makeText(getActivity(), "Berhasil pilih tanggal", Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    }
                });
            }
        });
        return alertDialog;
    }

    public interface CalendarBookingDialog{
        void onCalendarBookingDialog(String tanggal);
    }
}
