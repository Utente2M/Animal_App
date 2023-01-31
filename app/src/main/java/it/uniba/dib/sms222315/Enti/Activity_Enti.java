package it.uniba.dib.sms222315.Enti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.Fragment_Report_Add;
import it.uniba.dib.sms222315.Reporting.Fragment_Report_FriendReport;
import it.uniba.dib.sms222315.Reporting.Fragment_Report_MyReport;
import it.uniba.dib.sms222315.Reporting.SectionsPagerAdapter;

public class Activity_Enti extends AppCompatActivity {

    //widgets
    private TabLayout mTabLayout;
    public ViewPager mViewPager;

    //vars
    public SectionsPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enti);

       mTabLayout = (TabLayout) findViewById(R.id.Act_Enti_Tab);
       mViewPager  = (ViewPager) findViewById(R.id.Act_Enti_viewpager_cont);

        setupViewPager();

    }


    private void setupViewPager(){
        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new Fragment_Enti_YourAssociations());
        mPagerAdapter.addFragment(new Fragment_Enti_Create());
        mPagerAdapter.addFragment(new Fragment_Enti_Search());
        //mPagerAdapter.addFragment(new AccountFragment());

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("Your Associations");
        mTabLayout.getTabAt(1).setText("Create Associations");
        mTabLayout.getTabAt(2).setText("Search Associations");
        //mTabLayout.getTabAt(3).setText(getString(R.string.fragment_account));

    }

}
