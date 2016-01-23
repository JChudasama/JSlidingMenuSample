package jslidingmenusample.jpc.jslidingmenusample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JPChudasama on 1/23/2016.
 */
public class SettingsActivity extends SlidingBaseActivity implements View.OnClickListener {

    public ImageView ivToggleMenu, ivBack;
    public static TextView tvTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();

        changeSlidingModeNONE(true);
    }

    private void init() {
        ivToggleMenu = (ImageView) findViewById(R.id.ivDrawer);
        ivToggleMenu.setVisibility(View.GONE);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Settings");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                SettingsActivity.this.finish();
                break;
        }
    }
}
