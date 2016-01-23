package jslidingmenusample.jpc.jslidingmenusample.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import jslidingmenusample.jpc.jslidingmenusample.HelpActivity;
import jslidingmenusample.jpc.jslidingmenusample.R;
import jslidingmenusample.jpc.jslidingmenusample.SettingsActivity;


public class SlidingMenuListFragment extends BaseFragment implements OnClickListener {

    View layoutView;
    Context context;

    Button btnSettings, btnHelp;
    RelativeLayout rlMenuContainer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layoutView = inflater.inflate(R.layout.frag_sliding_menu, container, false);

        init();
        workaroundForLollipopNavBar();

        return layoutView;
    }


    private void init() {
        // TODO Auto-generated method stub
        rlMenuContainer = (RelativeLayout) layoutView.findViewById(R.id.menu_container);


        btnSettings = (Button) layoutView.findViewById(R.id.btnSettings);
        btnHelp = (Button) layoutView.findViewById(R.id.btnHelp);

        btnSettings.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnSettings:
                // go to settings
                homeActivity.goToScreen(SettingsActivity.class);
                break;
            case R.id.btnHelp:
                // go to help
                homeActivity.goToScreen(HelpActivity.class);
                break;
        }
    }

    protected void workaroundForLollipopNavBar() {
        try {
            int bottomPadding = 0;
            if (Build.VERSION.SDK_INT >= 21) {
                Resources resources = getResources();
                int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    bottomPadding += resources.getDimensionPixelSize(resourceId);
                }
            }
            //Logger.v("Frame Padding = " + bottomPadding);
            rlMenuContainer.setPadding(0, 0, 0, bottomPadding);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

