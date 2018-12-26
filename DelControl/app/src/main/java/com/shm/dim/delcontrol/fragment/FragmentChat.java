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

    private ImageButton mButtonSend;

    private EditText mMessage;

    public FragmentChat() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        if(!mMessage.getText().toString().equals("")) {
            setColorOnSendButton(getResources().getColor(R.color.colorWhite));
            mButtonSend.setClickable(true);
        } else {
            setColorOnSendButton(getResources().getColor(R.color.colorPrimaryDark));
            mButtonSend.setClickable(false);
        }
    }

    private void setColorOnSendButton(int color) {
        mButtonSend.setColorFilter(color);
    }

}