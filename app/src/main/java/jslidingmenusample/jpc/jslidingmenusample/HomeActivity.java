package jslidingmenusample.jpc.jslidingmenusample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Stack;

import jslidingmenusample.jpc.jslidingmenusample.fragments.ParkingMapFragment;

/**
 * Created by JPChudasama on 12/14/2015.
 */
public class HomeActivity extends  SlidingBaseActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {

    public static Stack<Fragment> fragmentStack = new Stack<Fragment>();

    public HomeActivity homeActivity;

    public ImageView ivToggleMenu, ivBack;
    public static TextView tvTitle;

    LinearLayout content_frame;
    RelativeLayout llHeaderContainer;

    /**
     * Google Map
     */
    private GoogleMap parkingMap;
    private SupportMapFragment mapFragment;
    private Marker markerGandhinagar, markerBaroda;
    private static final LatLng LOC_GNAGAR = new LatLng(23.2200, 72.6800);
    private static final LatLng LOC_BARODA = new LatLng(22.3000, 73.2000);


    public HomeActivity() {
        // TODO Auto-generated constructor stub
        super(true, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        homeActivity = this;

        try {
            init();

            initGoogleMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        // TODO Auto-generated method stub
        content_frame = (LinearLayout) findViewById(R.id.content_frame);
        workaroundForLollipopNavBar();

        llHeaderContainer = (RelativeLayout) findViewById(R.id.llHeaderContainer);

        ivToggleMenu = (ImageView) findViewById(R.id.ivDrawer);
        ivToggleMenu.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Parking Grid");
    }

    private void initGoogleMap() throws  Exception{
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapParking);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        parkingMap = googleMap;

        /**
         * My current location
         */
        parkingMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        /**
         * Add dummy markers
         */
        addDummyMarkersToMap();

        /**
         * Marker Clicks
         */
        parkingMap.setOnMarkerClickListener(this);

        /**
         * Pan zoom after rendering the google map size
         */
        final View mapView = getSupportFragmentManager().findFragmentById(R.id.mapParking).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    LatLngBounds bounds = new LatLngBounds.Builder()
                            .include(LOC_GNAGAR)
                            .include(LOC_BARODA)
                            .build();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    parkingMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                }
            });
        }

    }

    private void addDummyMarkersToMap() {
        try {
            markerGandhinagar = parkingMap.addMarker(new MarkerOptions()
                    .position(LOC_GNAGAR)
                    .title("Gandhinagar")
                    .snippet("Gujarat India")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            markerBaroda = parkingMap.addMarker(new MarkerOptions()
                    .position(LOC_BARODA)
                    .title("Baroda")
                    .snippet("Gujarat India")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission to access the location is missing.
//            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
//        } else if (parkingMap != null) {
            // Access to the location has been granted to the app.
            parkingMap.setMyLocationEnabled(true);
//        }
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
