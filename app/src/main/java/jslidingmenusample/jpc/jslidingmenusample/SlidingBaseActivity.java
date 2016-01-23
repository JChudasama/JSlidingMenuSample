package jslidingmenusample.jpc.jslidingmenusample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import jslidingmenusample.jpc.jslidingmenusample.fragments.SlidingMenuListFragment;


/**
 * Created by JPChudasama on 12/14/2015.
 */
public class SlidingBaseActivity extends SlidingFragmentActivity {

    private boolean isLeftMenu, isRightMenu;

    private SlidingMenu slidingMenu;

    private SlidingMenuListFragment slmFragment;

    public SlidingBaseActivity(){

    }

    public SlidingBaseActivity(boolean leftMenu, boolean rightMenu) {
        this.isLeftMenu = leftMenu;
        this.isRightMenu = rightMenu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.layout_menu);

        setupSlidingMenu(savedInstanceState);


    }

    private void setupSlidingMenu(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        try {
            slidingMenu = getSlidingMenu();
            slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
            //slidingMenu.setShadowDrawable(R.drawable.shadow);
            slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
            slidingMenu.setFadeDegree(0.35f);
            slidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);

            if (isLeftMenu && !isRightMenu) {
                setLeftSideSlidingMenu(savedInstanceState);
            } else if (!isLeftMenu && isRightMenu) {
                setRightSideSlidingMenu();
            } else {
                getSlidingMenu().setSlidingEnabled(false);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * to show right side sliding drawer menu
     * @param savedInstanceState
     */
    private void setRightSideSlidingMenu() {
        if (slidingMenu == null)
            slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.RIGHT);
        System.out.println("Right Side Menu called");
    }

    /**
     *  to show left side sliding drawer menu
     * @param savedInstanceState
     */

    public void setLeftSideSlidingMenu(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            slmFragment = new SlidingMenuListFragment();
            t.replace(R.id.menu_frame, slmFragment);
            t.commit();
        } else {
            slmFragment = (SlidingMenuListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        }
    }


    public void changeSlidingModeNONE(boolean value){
        if(slidingMenu != null){
            slidingMenu = getSlidingMenu();
            if(value){
                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }else{
                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            }
        }
    }
}
