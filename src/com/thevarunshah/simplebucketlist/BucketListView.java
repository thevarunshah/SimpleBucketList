package com.thevarunshah.simplebucketlist;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.thevarunshah.classes.BucketItem;


public class BucketListView extends Activity implements OnClickListener, OnItemLongClickListener{
	
	private final String TAG = "BucketListView";
	
	private final ArrayList<BucketItem> bucketList = new ArrayList<BucketItem>();
	
	private ListView listView;
	private ArrayAdapter<BucketItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bucket_list_view);
        
        listView = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<BucketItem>(this, android.R.layout.simple_list_item_1, bucketList);
        listView.setAdapter(adapter);
        
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
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
		
		Log.i(TAG, "long pressed goal");
		Log.i(TAG, bucketList.get(position).toString());
		new AlertDialog.Builder(this)
			.setIconAttribute(android.R.attr.alertDialogIcon)
			.setTitle("Confirm Delete")
			.setMessage("Are you sure you want to delete this goal?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					bucketList.remove(position);
					adapter.notifyDataSetChanged();
				}
			})
			.setNegativeButton("No", null)
			.show();
		
		return true;
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
