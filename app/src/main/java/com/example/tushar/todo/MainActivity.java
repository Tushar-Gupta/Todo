package com.example.tushar.todo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	SQLiteDatabase database;
	public final static String INDEX = "com.example.tushar.todo.INDEX";
	RecyclerView.Adapter tasksAdapter;
	RecyclerView.LayoutManager tasksLayoutManager;
	RecyclerView tasks_recycler_view;
	String[] tasks_from_table;
	int size;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
		setSupportActionBar(mainToolbar);
		database = openOrCreateDatabase("TodoDatabase",MODE_PRIVATE,null);
		database.execSQL("CREATE TABLE IF NOT EXISTS tasksTable(Title VARCHAR, Description VARCHAR, Done INTEGER, _id INTEGER PRIMARY KEY AUTOINCREMENT);");
		tasks_recycler_view = (RecyclerView) findViewById(R.id.tasks_recycler_view);

		Context context = getApplicationContext();
		Cursor cursor = database.rawQuery("SELECT * FROM tasksTable",null);
		size = cursor.getCount();
		List<String> tasks_from_table_list = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			tasks_from_table_list.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		tasks_from_table = tasks_from_table_list.toArray(new String[0]);


		tasksLayoutManager = new LinearLayoutManager(this);
		tasks_recycler_view.setLayoutManager(tasksLayoutManager);


		tasksAdapter = new TasksAdapter(tasks_from_table);
		tasks_recycler_view.setAdapter(tasksAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case R.id.action_add_task:
				Intent intent = new Intent(this, AddTask.class);
				startActivity(intent);
				return true;
			case R.id.action_del_all:
				database.execSQL("DROP TABLE IF EXISTS tasksTable;");
				intent = getIntent();
				finish();
				overridePendingTransition( 0, 0);
				startActivity(intent);
				overridePendingTransition( 0, 0);
			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.
				return super.onOptionsItemSelected(item);

		}
	}

	public void showTask(View view)
	{
		int index = ((ViewGroup) view.getParent()).indexOfChild(view);
		Intent intent = new Intent(this, DisplayTask.class);
		intent.putExtra(INDEX, index);
		startActivity(intent);
	}

}
