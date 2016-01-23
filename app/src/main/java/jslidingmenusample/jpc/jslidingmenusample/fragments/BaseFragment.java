package jslidingmenusample.jpc.jslidingmenusample.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import jslidingmenusample.jpc.jslidingmenusample.HomeActivity;
import jslidingmenusample.jpc.jslidingmenusample.SlidingBaseActivity;


public class BaseFragment extends Fragment {

	public SlidingBaseActivity baseActivity;
	
	public HomeActivity homeActivity;
	
	public Resources mResources;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/**
		 * Context for the Base Activity
		 */
		baseActivity = (SlidingBaseActivity) getActivity();

		homeActivity = (HomeActivity) getActivity();

		mResources = baseActivity.getResources();
	}
	
	public void onBackPressed() {
		try {
			//homeActivity.removeFragments();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
