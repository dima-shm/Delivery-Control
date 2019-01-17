package com.shm.dim.delcontrol.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shm.dim.delcontrol.R;

public class FragmentChat extends Fragment {

    private EditText mMessage;

    private ImageButton mButtonSend;

    private boolean mIsSendButtonEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        initButtonSend(view);
        initEditText(view);
    }

    private void initButtonSend(View view) {
        mButtonSend = view.findViewById(R.id.send_button);
    }

    private void initEditText(View view) {
        mMessage= view.findViewById(R.id.message);
        setTextChangedListener();
    }

    private void setTextChangedListener() {
        mMessage.addTextChangedListener(getTextChangedListener());
    }

    private TextWatcher getTextChangedListener() {
        return new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeSendButtonStyle();
            }

            public void afterTextChanged(Editable s) {}

        };
    }

    private void changeSendButtonStyle() {
        if(mIsSendButtonEnabled == isFieldHasText()) {
            return;
        }
        changeButtonState();
        applyChanges();
    }

    private boolean isFieldHasText() {
        return (mMessage.getText().toString().length() > 0);
    }

    private void changeButtonState() {
        mIsSendButtonEnabled = !mIsSendButtonEnabled;
    }

    private void applyChanges() {
        setColorOnSendButton(mIsSendButtonEnabled);
        animateSendButton(mIsSendButtonEnabled);
        mButtonSend.setClickable(mIsSendButtonEnabled);
    }

    private void setColorOnSendButton(boolean isSendButtonEnabled) {
        int colorEnabled = getResources().getColor(R.color.colorPrimary);
        int colorDisabled = getResources().getColor(R.color.colorPrimaryDark);
        if(isSendButtonEnabled) {
            mButtonSend.setColorFilter(colorEnabled);
        } else {
            mButtonSend.setColorFilter(colorDisabled);
        }
    }

    private void animateSendButton(boolean isSendButtonEnabled) {
        if(isSendButtonEnabled) {
            startRotateAnimation(mButtonSend, -45);
        } else {
            startRotateAnimation(mButtonSend, 0);
        }
    }

    private void startRotateAnimation(View view, int value) {
        view.animate().rotation(value).start();
    }

}