package com.thevarunshah.simplebucketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddItem extends Activity implements OnClickListener{
	
	private final String TAG = "AddItem";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        
        Button addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {

    	Log.i(TAG, "clicked add button");
    	EditText text = (EditText) findViewById(R.id.item_text);
    	//Toast.makeText(getApplicationContext(), text.getText().toString(), Toast.LENGTH_SHORT).show();

    	Intent data = new Intent();
    	data.putExtra("text", text.getText().toString());
    	setResult(RESULT_OK, data);
    	finish();
	}

}
