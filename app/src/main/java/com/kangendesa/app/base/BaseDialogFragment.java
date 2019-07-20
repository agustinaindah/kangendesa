package com.kangendesa.app.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.kangendesa.app.R;

import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(setView(), null);
        ButterKnife.bind(this, view);
        return setupDialog(builder.setView(view));
    }

    protected abstract AlertDialog setupDialog(AlertDialog.Builder builder);

    protected abstract int setView();
}
