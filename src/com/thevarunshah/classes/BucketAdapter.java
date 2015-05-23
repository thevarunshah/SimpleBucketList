package com.thevarunshah.classes;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.thevarunshah.simplebucketlist.R;

public class BucketAdapter extends ArrayAdapter<BucketItem> {

	private ArrayList<BucketItem> bucketList; //the list the adapter manages
	private Context context; //context attached to adapter

	public BucketAdapter(Context context, int textViewResourceId, ArrayList<BucketItem> bucketList) {
		
		super(context, textViewResourceId, bucketList);
		
		this.context = context;
		this.bucketList = bucketList;
	}

	private class ViewHolder {
		
		CheckBox done;
		TextView goal;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if(convertView == null){
			
			LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.row, null);

			holder = new ViewHolder();
			holder.goal = (TextView) convertView.findViewById(R.id.row_text);
			holder.done = (CheckBox) convertView.findViewById(R.id.row_check);
			convertView.setTag(holder);
		} 
		else{
			
			holder = (ViewHolder) convertView.getTag();
		}
		
		final ViewHolder holderFinal = holder;
		
		//attach a check listener to the checkbox
		holder.done.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	
                BucketItem item = (BucketItem) getItem(position); //get checked item
                item.setDone(isChecked); //set as done/undone
                
                if(isChecked){
                	holderFinal.goal.setPaintFlags(holderFinal.goal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); //apply strikethrough effect
                }
                else{
                	holderFinal.goal.setPaintFlags(holderFinal.goal.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG); //get rid of strikethrough effect
                }
            }
        });
		
		//attach a press listener for toast message
		holder.done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				CheckBox cb = (CheckBox) v;
				if(cb.isChecked()){
					Toast.makeText(v.getContext(), "Good Job!", Toast.LENGTH_SHORT).show(); //tell them good job
				}
			}
		});

		BucketItem item = bucketList.get(position);
		holder.goal.setText(item.getGoal());
		holder.done.setChecked(item.isDone());
		holder.done.setTag(item);

		return convertView;
	}
}
