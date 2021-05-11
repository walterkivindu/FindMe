package com.example.findme;

import com.example.findme.ViewFoundChild;
import com.example.findme.Message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
 
    final int PAGE_COUNT = 3;
 
    /** Constructor of the class */
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
        Bundle data = new Bundle();
        switch(arg0){
        /** Android tab is selected */
        case 0:
        	ViewFoundChild androidFragment = new ViewFoundChild();
            data.putInt("current_page", arg0+1);
            androidFragment.setArguments(data);
            return androidFragment;

        /** Apple tab is selected */
        case 1:
            Message appleFragment = new Message();
            data.putInt("current_page", arg0+1);
            appleFragment.setArguments(data);
            return appleFragment;
        case 2:
        	ContactList yl = new ContactList();
            data.putInt("current_page", arg0+1);
            yl.setArguments(data);
            return yl;
        	
		}
        return null;
    }
 
    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    
    
 
}