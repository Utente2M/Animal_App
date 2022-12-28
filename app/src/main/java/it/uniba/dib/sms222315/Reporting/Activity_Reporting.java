package it.uniba.dib.sms222315.Reporting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import it.uniba.dib.sms222315.R;

public class Activity_Reporting extends AppCompatActivity {

    //widgets
    private TabLayout mTabLayout;
    public ViewPager mViewPager;

    //vars
    public SectionsPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);

        mTabLayout = (TabLayout) findViewById(R.id.Act_report_Tab);
        mViewPager  = (ViewPager) findViewById(R.id.Act_report_viewpager_cont);

        setupViewPager();

    }

    private void setupViewPager(){
        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new Fragment_Report_MyReport());
        mPagerAdapter.addFragment(new Fragment_Report_Add());
        mPagerAdapter.addFragment(new Fragment_Report_FriendReport());
        //mPagerAdapter.addFragment(new AccountFragment());

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("My Post");
        mTabLayout.getTabAt(1).setText("Create Post");
        mTabLayout.getTabAt(2).setText("Friend's Post");
        //mTabLayout.getTabAt(3).setText(getString(R.string.fragment_account));

    }
}

