package com.example.abmcr.robot.view.View.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abmcr.robot.R;
import com.example.abmcr.robot.view.View.ViewModel.LogViewModel;

import java.util.ArrayList;

/**
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class CommunicationFragment extends Fragment {

    private static TextView tvOut;
    private LogViewModel viewModel;
    private Observer<String> sObserverMessages;
    private static String sMessage = "";

    public static CommunicationFragment newInstance(){
        return new CommunicationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_communication, container, false);
        initViewModel();
        bindViews(v);
        return v;
    }

    //Assign view components
    private void bindViews(View v){
        tvOut = v.findViewById(R.id.tvOut);
    }

    //Assign ViewModel to this fragment & the observer variable
    private void initViewModel(){

        viewModel = ViewModelProviders.of(this).get(LogViewModel.class);
        sObserverMessages = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                printLogs(msg);
            }
        };
        viewModel.printMessages(getContext()).observe(this, sObserverMessages);
    }

    //Data is printed on screen through a TextView
    public static void printLogs(String msg) {
        sMessage = msg;
        tvOut.setText(msg);
    }

    @Override
    public void onStop(){
        viewModel.stopMessaging(getContext());
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
