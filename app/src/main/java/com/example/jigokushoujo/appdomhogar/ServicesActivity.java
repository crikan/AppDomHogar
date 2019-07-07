package com.example.jigokushoujo.appdomhogar;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Objects;

public class ServicesActivity extends AppCompatActivity {

    private MyAdapter mCardAdapter;

    public ServicesActivity() {
        boolean mShowingFragments = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Objects.requireNonNull(getSupportActionBar()).hide();


        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mCardAdapter = new MyAdapter();

        mCardAdapter.addCardItem(new CardItem(R.string.decowifi_pack,R.string.deco_wifi,R.string._100,R.drawable.wifihouse));
        mCardAdapter.addCardItem(new CardItem(R.string.wifi_signal_expansion,R.string.wifi_one_point,R.string._50,R.drawable.repeater));
        mCardAdapter.addCardItem(new CardItem(R.string.smart_plugs_pack, R.string.seven_plugs,R.string._150,R.drawable.plugs));
        mCardAdapter.addCardItem(new CardItem( R.string.smart_bulbs_pack, R.string.smart_bulbs_install,R.string._100,R.drawable.bulbs));
        mCardAdapter.addCardItem(new CardItem( R.string.wifi_cams,R.string.surv_cams,R.string._200,R.drawable.cams));

        CardFragmentPagerAdapter mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this));

        MyTransformer mCardMyTransformer = new MyTransformer(mViewPager, mCardAdapter);
        mCardMyTransformer.enableScaling(true);
        MyTransformer mFragmentCardMyTransformer = new MyTransformer(mViewPager, mFragmentCardAdapter);
        mFragmentCardMyTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardMyTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
