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
 * Created by abmcr on 21/03/2018.
 */

public class CommunicationFragment extends Fragment {

    private TextView tvOut;
    //private ArrayList<String> alMessages = new ArrayList<String>();
    private String aux;

    private LogViewModel viewModel;
    private Observer<String> sMessages;

    public static CommunicationFragment newInstance(){
        return new CommunicationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //messages.clear();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_communication, container, false);
        bindViews(v);
        initViewModel();
        return v;
    }

    private void bindViews(View v){
        tvOut = v.findViewById(R.id.tvOut);

        aux = "aaa"+'\n'+"bbb";
        tvOut.setText(aux);
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        sMessages = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                tvOut.setText(msg);
            }
        };
    }
}
