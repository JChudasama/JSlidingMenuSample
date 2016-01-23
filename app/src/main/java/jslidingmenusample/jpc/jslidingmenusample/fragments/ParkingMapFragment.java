package jslidingmenusample.jpc.jslidingmenusample.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jslidingmenusample.jpc.jslidingmenusample.R;

/**
 * Created by JPChudasama on 1/23/2016.
 */
public class ParkingMapFragment extends BaseFragment {

    View layoutView;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layoutView = inflater.inflate(R.layout.frag_parking_map, container, false);

        return layoutView;
    }
}
