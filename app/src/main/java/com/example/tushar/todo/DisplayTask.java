package com.example.tushar.todo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayTask extends AppCompatActivity {

	private SmartFragmentStatePagerAdapter adapterViewPager;
	SQLiteDatabase database;
	ViewPager vpPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_task);

		Intent intent = getIntent();
		int index = intent.getIntExtra(MainActivity.INDEX,0);

		Toolbar taskToolbar = (Toolbar) findViewById(R.id.task_toolbar);
		setSupportActionBar(taskToolbar);
		// Get a support ActionBar corresponding to this toolbar
		ActionBar ab = getSupportActionBar();
		// Enable the Up button
		ab.setDisplayHomeAsUpEnabled(true);

		database = openOrCreateDatabase("TodoDatabase",MODE_PRIVATE,null);
		database.execSQL("CREATE TABLE IF NOT EXISTS tasksTable(Title VARCHAR, Description VARCHAR, Done INTEGER, _id INTEGER PRIMARY KEY AUTOINCREMENT);");

		Context context = getApplicationContext();
		Cursor cursor = database.rawQuery("SELECT * FROM tasksTable",null);
		int length = cursor.getCount();
		List<String> titles_from_table_list = new ArrayList<String>();
		List<String> descs_from_table_list = new ArrayList<String>();
		List<Integer> dones_from_table_list = new ArrayList<Integer>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			titles_from_table_list.add(cursor.getString(0));
			descs_from_table_list.add(cursor.getString(1));
			dones_from_table_list.add(cursor.getInt(2));
			cursor.moveToNext();
		}
		cursor.close();
		String[] titles_from_table = titles_from_table_list.toArray(new String[0]);
		String[] descs_from_table = descs_from_table_list.toArray(new String[0]);
		Integer[] dones_from_table = dones_from_table_list.toArray(new Integer[0]);

		vpPager = (ViewPager) findViewById(R.id.vpPager);
		adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),length,titles_from_table,descs_from_table,dones_from_table);
		vpPager.setAdapter(adapterViewPager);
		vpPager.setCurrentItem(index);
	}

	public void toggleTask(View view)
	{
//		int index = ((ViewGroup)(((view.getParent()).getParent()).getParent())).indexOfChild((View) (view.getParent()).getParent()  );
//		int index = ((ViewGroup) view.getParent()).indexOfChild(view);
		int index = vpPager.getCurrentItem()+1;
		TextView textview = (TextView)view;
		ContentValues values = new ContentValues();
		if ( textview.getText().equals("Not done"))
		{
			textview.setText("Done");
			values.put("Done", 1);
			database.update("tasksTable", values, "_id = ? ", new String[] { index + "" } );
		}
		else
		{
			textview.setText("Not done");
			values.put("Done", 0);
			database.update("tasksTable", values, "_id = ? ", new String[] { index + "" } );
		}
	}


}
