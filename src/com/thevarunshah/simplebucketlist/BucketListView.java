package com.thevarunshah.simplebucketlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.thevarunshah.classes.BucketAdapter;
import com.thevarunshah.classes.BucketItem;


public class BucketListView extends Activity implements OnClickListener, OnItemLongClickListener, Serializable{
	
	private static final long serialVersionUID = 1L;

	private final String TAG = "BucketListView";
	
	private final ArrayList<BucketItem> bucketList = new ArrayList<BucketItem>();
	
	private ListView listView;
	private BucketAdapter listAdapter = null;
	//private ArrayAdapter<BucketItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bucket_list_view);
        
        listView = (ListView) findViewById(R.id.listview);
        //adapter = new ArrayAdapter<BucketItem>(this, android.R.layout.simple_list_item_1, bucketList);
        listAdapter = new BucketAdapter(this, R.layout.row, bucketList);
        listView.setAdapter(listAdapter);
        
        listView.setOnItemLongClickListener(this);
        
        Button addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {

    	//Toast.makeText(getApplicationContext(), "clicked on add button", Toast.LENGTH_SHORT).show();
    	Log.i(TAG, "clicked add button");
    	
    	Intent i = new Intent(BucketListView.this, AddItem.class);
    	startActivityForResult(i, 0);
	}
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.i(TAG, "adding new item");

		if(resultCode == RESULT_OK && requestCode == 0){
			BucketItem goal = new BucketItem(data.getStringExtra("text"));
			Log.i(TAG, goal.getGoal());
			
			bucketList.add(goal);
			listAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
		
		Log.i(TAG, "long pressed " + bucketList.get(position).toString());
		
		new AlertDialog.Builder(this)
			.setIconAttribute(android.R.attr.alertDialogIcon)
			.setTitle("Confirm Delete")
			.setMessage("Are you sure you want to delete this goal?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					listAdapter.remove((BucketItem)parent.getItemAtPosition(position));
		            listAdapter.notifyDataSetChanged();
					//bucketList.remove(position);
					//listAdapter.notifyDataSetChanged();
				}
			})
			.setNegativeButton("No", null)
			.show();
		
		return false;
	}
	
	@Override
	protected void onPause(){
		
		super.onPause();
		
		try {
			Log.i(TAG, "writing to file");
			writeData(this.bucketList);
		} catch (IOException e) {
			Log.i(TAG, "could not write to file");
			Log.i(TAG, "Exception: " + e);
		}
	}
	
	@Override
	protected void onResume(){
		
		super.onResume();
		
		try {
			if(bucketList.isEmpty()){
				Log.i(TAG, "reading from file");
				this.bucketList.clear();
				this.bucketList.addAll(readData());
			}
		} catch (Exception e) {
			Log.i(TAG, "could not read file");
			Log.i(TAG, "Exception: " + e);
		}
	}
	
	public static void writeData(ArrayList<BucketItem> bucketList) throws IOException {
		
		File file = new File(android.os.Environment.getExternalStorageDirectory() + "/bucket_list.ser");
		file.createNewFile();
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(bucketList);
		oos.close();
	}
	
	public static ArrayList<BucketItem> readData() throws IOException, ClassNotFoundException {
		
		File file = new File(android.os.Environment.getExternalStorageDirectory() + "/bucket_list.ser");
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		
		ArrayList<BucketItem> list = (ArrayList<BucketItem>) ois.readObject();
		ois.close();
		return list;
	}
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bucket_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
