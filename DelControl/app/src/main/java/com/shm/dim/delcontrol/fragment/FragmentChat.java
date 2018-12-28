package com.shm.dim.delcontrol.fragment;

import android.animation.ObjectAnimator;
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

    private ImageButton mButtonSend;

    private EditText mMessage;

    private final int SEND_BUTTON_ANIMATION_DURATION = 800;

    private boolean mIsButtonClickable;

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
        mMessage.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeSendButtonStyle();
            }

            public void afterTextChanged(Editable s) {}

        });
    }

    private void changeSendButtonStyle() {
        boolean previousButtonState = mIsButtonClickable;
        boolean newButtonState = getNewButtonState();
        if(previousButtonState == newButtonState) {
            return;
        } else {
            changeButtonState();
        }
        applyChanges();
    }

    private boolean getNewButtonState() {
        return (mMessage.getText().toString().length() > 0);
    }

    private void changeButtonState() {
        mIsButtonClickable = !mIsButtonClickable;
    }

    private void applyChanges() {
        setColorOnSendButton(mIsButtonClickable);
        animateSendButton(mIsButtonClickable);
        mButtonSend.setClickable(mIsButtonClickable);
    }

    private void setColorOnSendButton(boolean isButtonClickable) {
        int colorPrimary = getResources().getColor(R.color.colorPrimary);
        int colorPrimaryDark = getResources().getColor(R.color.colorPrimaryDark);
        if(isButtonClickable) {
            mButtonSend.setColorFilter(colorPrimary);
        } else {
            mButtonSend.setColorFilter(colorPrimaryDark);
        }
    }

    private void animateSendButton(boolean isButtonClickable) {
        if(isButtonClickable) {
            rotationSendButton(0f, -45f);
        } else {
            rotationSendButton(-45f, 0f);
        }
    }

    private void rotationSendButton(float from, float to) {
        ObjectAnimator
                .ofFloat(mButtonSend, "rotation", from, to)
                .setDuration(SEND_BUTTON_ANIMATION_DURATION)
                .start();
    }

}