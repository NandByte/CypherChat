package com.nandbyte.cypherchat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {


    public SectionPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                requestFragment _requestFragment = new requestFragment();
                return _requestFragment;
            case 1:
                chatFragment _chatFragment = new chatFragment();
                return  _chatFragment;
            case 2:
                friendFragment _friendFragment = new friendFragment();
                return _friendFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
