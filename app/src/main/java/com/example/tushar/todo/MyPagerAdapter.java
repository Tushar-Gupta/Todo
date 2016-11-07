package com.example.tushar.todo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Tushar on 08/11/16.
 */
// Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
public class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
	private int NUM_ITEMS;
	String[] titles;
	String[] descs;
	Integer[] dones;

	public MyPagerAdapter(FragmentManager fragmentManager, int num, String[] inputTitles, String[] inputDescs, Integer[] inputDones) {
		super(fragmentManager);
		NUM_ITEMS = num;
		titles = inputTitles;
		descs = inputDescs;
		dones = inputDones;
	}

	// Returns total number of pages
	@Override
	public int getCount() {
		return NUM_ITEMS;
	}

	// Returns the fragment to display for that page
	@Override
	public Fragment getItem(int position) {
		return TaskFragment.newInstance(titles[position], descs[position], dones[position]);
	}

	// Returns the page title for the top indicator
	@Override
	public CharSequence getPageTitle(int position) {
		return "Task " + (position+1);
	}

}
