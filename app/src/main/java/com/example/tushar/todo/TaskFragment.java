package com.example.tushar.todo;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private String title;
	private String desc;
	private Integer done;

	// newInstance constructor for creating fragment with arguments
	public static TaskFragment newInstance(String title, String desc, Integer done) {
		TaskFragment fragmentTask = new TaskFragment();
		Bundle args = new Bundle();
		args.putString("someDesc", desc);
		args.putString("someTitle", title);
		args.putInt("someDone", done);
		fragmentTask.setArguments(args);
		return fragmentTask;
	}

	// Store instance variables based on arguments passed
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		desc = getArguments().getString("someDesc");
		title = getArguments().getString("someTitle");
		done = getArguments().getInt("someDone");
	}

	// Inflate the view for the fragment based on layout XML
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, container, false);
		TextView taskTitle = (TextView) view.findViewById(R.id.taskTitle);
		taskTitle.setText(title);
		TextView taskDesc = (TextView) view.findViewById(R.id.taskDesc);
		taskDesc.setText(desc);

		TextView taskDone = (TextView) view.findViewById(R.id.taskDone);
		if ( done.intValue() == 1 )
		{
			taskDone.setText("Done");
		}
		else
		{
			taskDone.setText("Not done");
		}
//		taskDone.setText(Integer.toString(done));

		return view;
	}



}
