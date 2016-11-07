package com.example.tushar.todo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {
	SQLiteDatabase database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		Toolbar AddTask_toolbar = (Toolbar) findViewById(R.id.AddTask_toolbar);
		setSupportActionBar(AddTask_toolbar);
		getSupportActionBar().setTitle("Add a new task");
		// Get a support ActionBar corresponding to this toolbar
		ActionBar ab = getSupportActionBar();
		// Enable the Up button
		ab.setDisplayHomeAsUpEnabled(true);
		database = openOrCreateDatabase("TodoDatabase",MODE_PRIVATE,null);
		database.execSQL("CREATE TABLE IF NOT EXISTS tasksTable(Title VARCHAR, Description VARCHAR, Done INTEGER, _id INTEGER PRIMARY KEY AUTOINCREMENT);");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addtask_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case R.id.action_createTask:
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_SHORT;
				EditText titleEditText = (EditText)findViewById(R.id.addTaskTitle);
				EditText descEditText = (EditText)findViewById(R.id.addTaskDesc);
				String titleString = titleEditText.getText().toString();
				String descriptionString = descEditText.getText().toString();
				if ( titleString.length() == 0 || descriptionString.length() == 0 )
				{
					CharSequence text = "Fields can't be empty";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else
				{
					ContentValues values = new ContentValues();
					values.put("Title",titleString);
					values.put("Description",descriptionString);
					values.put("Done",0);

					CharSequence text = Long.toString(database.insert("tasksTable",null,values));

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
//					Intent intent = new Intent(this, MainActivity.class);
//					startActivity(intent);
				}
				return true;

			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.
				return super.onOptionsItemSelected(item);

		}
	}

}
