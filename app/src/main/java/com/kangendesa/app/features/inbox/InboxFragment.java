package com.kangendesa.app.features.inbox;

import android.os.Bundle;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseFragment;

/**
 * Created by agustinaindah on 06 Februari 2019
 */
public class InboxFragment extends BaseFragment {

    public static InboxFragment newInstance() {
        Bundle args = new Bundle();
        InboxFragment fragment = new InboxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setView() {
        return R.layout.fragment_inbox;
    }
}
