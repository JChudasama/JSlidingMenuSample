package jslidingmenusample.jpc.jslidingmenusample;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Stack;

import jslidingmenusample.jpc.jslidingmenusample.fragments.ParkingMapFragment;

/**
 * Created by JPChudasama on 12/14/2015.
 */
public class HomeActivity extends  SlidingBaseActivity implements View.OnClickListener {

    public static Stack<Fragment> fragmentStack = new Stack<Fragment>();

    public HomeActivity homeActivity;

    public ImageView ivToggleMenu, ivBack;
    public static TextView tvTitle;

    FrameLayout content_frame;
    RelativeLayout llHeaderContainer;

    public HomeActivity() {
        // TODO Auto-generated constructor stub
        super(true, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        homeActivity = this;

        init();

        try {
            if (savedInstanceState == null) {
                if (fragmentStack != null && fragmentStack.size() > 0)
                    fragmentStack.clear();
                addFragments(new ParkingMapFragment());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        // TODO Auto-generated method stub
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        workaroundForLollipopNavBar();


        llHeaderContainer = (RelativeLayout) findViewById(R.id.llHeaderContainer);

        ivToggleMenu = (ImageView) findViewById(R.id.ivDrawer);
        ivToggleMenu.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Parking Grid");
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
            content_frame.setPadding(0,0,0,bottomPadding);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void addFragments(Fragment fragment) throws Exception {
        FragmentManager manager = getSupportFragmentManager();
        fragmentStack.push(fragment);
        FragmentTransaction ft = manager.beginTransaction();

        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
        changeSlidingModeNONE(false);
    }

    public void addFragments(Fragment fragment, Bundle args) throws Exception {
        FragmentManager manager = getSupportFragmentManager();
        fragmentStack.push(fragment);
        FragmentTransaction ft = manager.beginTransaction();

        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
        changeSlidingModeNONE(false);
    }

    public void removeFragments() throws Exception {

        System.out.println("Fragment Stack Size: " + fragmentStack.size());
        if (fragmentStack.size() >= 2) {
            Fragment fragment = fragmentStack.elementAt(fragmentStack.size() - 2);
			/* pop current fragment from stack.. */
            fragmentStack.pop();
			/*
			 * We have the target fragment in hand.. Just show it.. Show a
			 * standard navigation animation
			 */
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else {
            //Global.showExitDialog(homeActivity);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ivDrawer:
                homeActivity.getSlidingMenu().toggle();
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        try {
            removeFragments();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBackButton(boolean show) {
        if (show) {
            changeSlidingModeNONE(true);
            ivBack.setVisibility(View.VISIBLE);
            ivToggleMenu.setVisibility(View.GONE);
        } else {
            changeSlidingModeNONE(false);
            ivBack.setVisibility(View.GONE);
            ivToggleMenu.setVisibility(View.VISIBLE);
        }
    }


    public void changeStatusbarColor(int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeHeaderColor(int color) {
        llHeaderContainer.setBackgroundColor(color);
    }

    public void goToScreen(Class<?> cls) {
        startActivity(new Intent(HomeActivity.this, cls));
    }
}
