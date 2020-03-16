package com.example.newstoday;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] mCategoryNames;

    public NewsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        String[] categoryNames = {
                mContext.getResources().getString(R.string.sports),
                mContext.getResources().getString(R.string.politics),
                mContext.getResources().getString(R.string.env),
                mContext.getResources().getString(R.string.world),
                mContext.getResources().getString(R.string.business)
        };
        mCategoryNames = categoryNames;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SportFragment(mContext);
            case 1:
                return new PoliticsFragment(mContext);
            case 2:
                return new EnvironmentFragment(mContext);
            case 3:
                return new WorldFragment(mContext);
            case 4:
                return new BusinessFragment(mContext);
        }

        return null;
    }

    @Override
    public int getCount() {
        return mCategoryNames.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryNames[position];
    }
}
