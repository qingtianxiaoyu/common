package com.li.common.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.li.common.R;

/**
 * Created by liweifa on 2016/11/24.
 */

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {
    private TextView mConfirmButton;
    private TextView mExplanation;
    private View mContextView;
    DialogConfirmListener mDialogConfirmListener;

    public static CustomDialogFragment newInstances(CharSequence explanation) {
        CustomDialogFragment confirmDialogFragment = new CustomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("explanation", explanation);
        confirmDialogFragment.setArguments(bundle);
        return confirmDialogFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirmation_dialog, container, false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//     Use the  Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogStyle);
        mContextView = LayoutInflater.from(getContext()).inflate(R.layout.confirmation_dialog, null, false);
        builder.setView(mContextView);
//         Create the AlertDialog object and return it
        Dialog dialog = builder.create();
        mExplanation = (TextView) mContextView.findViewById(R.id.explanation);
        if (getArguments() != null) {
            CharSequence explanation = getArguments().getCharSequence("explanation");
            if (!TextUtils.isEmpty(explanation)) {

                mExplanation.setText(explanation);
            }
        }
        mConfirmButton = (TextView) mContextView.findViewById(R.id.confirm);
        mConfirmButton.setOnClickListener(this);
        dialog.getCurrentFocus();
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDialogConfirmListener != null) {
            mDialogConfirmListener.dismissListener();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mConfirmButton.getId()) {
            if (mDialogConfirmListener != null) {
                mDialogConfirmListener.confirmListener();
            }
            dismiss();
        }

    }

    public void addDialogConfirmListener(DialogConfirmListener dialogConfirmListeners) {
        mDialogConfirmListener = dialogConfirmListeners;
    }

    public interface DialogConfirmListener {
        void confirmListener();

        void dismissListener();
    }

}
