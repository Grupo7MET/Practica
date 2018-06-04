package view.Accelerometer;

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

import model.Constants;
import viewModel.AccelerometerViewModel;

/**
 * Class that creates the accelerometer view and assign all the visual components
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class AccelerometerFragment extends Fragment {

    private TextView tvTitle, tvX,tvY,tvZ, tvXValue,tvYValue,tvZValue;
    private Observer<String> acc_x, acc_y, acc_z;
    private AccelerometerViewModel viewModel;

    public static AccelerometerFragment newInstance(){
        return new AccelerometerFragment();
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
        View v = inflater.inflate(R.layout.fragment_accelerometer,container,false);
        initViewModel();
        bindViews(v);
        viewModel.sendMessage(Constants.SENDING_PROTOCOL_ACCELEROMETER);
        return v;
    }

    /**
     * Assigning all the visual components
     */
    private void bindViews(View v){
        tvTitle = v.findViewById(R.id.tvTitle);
        tvX = v.findViewById(R.id.tvX);
        tvY = v.findViewById(R.id.tvY);
        tvZ = v.findViewById(R.id.tvZ);
        tvXValue = v.findViewById(R.id.tvXValue);
        tvYValue = v.findViewById(R.id.tvYValue);
        tvZValue = v.findViewById(R.id.tvZValue);

        tvTitle.setText(R.string.aTitle);
        tvX.setText(R.string.aX);
        tvY.setText(R.string.aY);
        tvZ.setText(R.string.aZ);
    }

    /**
     * Assigning ViewModel to this fragment & the observer variables methods
     */
    private void initViewModel(){

        viewModel = ViewModelProviders.of(this).get(AccelerometerViewModel.class);

        acc_x = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                tvXValue.setText(msg);
            }
        };
        viewModel.refreshAccelerometerX(getContext()).observe(this, acc_x);

        acc_y = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                tvYValue.setText(msg);
            }
        };
        viewModel.refreshAccelerometerY(getContext()).observe(this, acc_y);

        acc_z = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                tvZValue.setText(msg);
            }
        };
        viewModel.refreshAccelerometerZ(getContext()).observe(this, acc_z);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Before exit, send a packet saying that we quit
     * The communication is closed
     */
    @Override
    public void onStop() {
        viewModel.sendMessage(Constants.SENDING_PROTOCOL_BACK_TO_MENU);
        viewModel.stopMessaging(getContext());
        getActivity().finish();
        super.onStop();
    }
}
