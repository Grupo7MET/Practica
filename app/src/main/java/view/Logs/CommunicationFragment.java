package view.Logs;

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
import viewModel.LogViewModel;

/**
 * Class that creates the communication view, assign all the visual components, manage the communication
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

    /**
     * Data is printed on screen through a TextView. It is a viewModel's callback
     * @param msg the string we want to print on the screen
     */

    public static void printLogs(String msg) {
        sMessage = msg;
        tvOut.setText(msg);
    }

    @Override
    public void onStop(){
        viewModel.stopMessaging(getContext());
        getActivity().finish();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
