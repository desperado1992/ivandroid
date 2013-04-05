package com.example.ejercicio4;



import com.example.ejercicio5.R;
import com.madsa.AugmentedCompass.BrujulaAumentada;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setListAdapter(new SpeechListAdapter(this));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
    protected void onListItemClick(ListView l, View v, int position, long id)  {			

		String[] mLocations = getResources().getStringArray(R.array.locations);
    	
    	String sLocationId = mLocations[position].split(";")[FieldNames.LocationId.getValue()];
    	
    	int item = Integer.parseInt(sLocationId);
		Intent oIntent = new Intent(MainActivity.this, InterestingPointActivity.class);
		oIntent.putExtra("item", item);
    	
	    startActivity(oIntent);
	}
	
	private class SpeechListAdapter extends BaseAdapter{
    	private Context mContext;
    	private String[] mLocations;

        public SpeechListAdapter(Context context){
            mContext = context;
            mLocations = mContext.getResources().getStringArray(R.array.locations);
        }

    	
		public int getCount() {
            return mLocations.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			String title = mLocations[position].split(";")[FieldNames.LocationName.getValue()];
			
            SpeechView sv;
            if (convertView == null) {
                sv = new SpeechView(mContext, title);
            } else {
                sv = (SpeechView)convertView;
                sv.setTitle(title);
            }
			return sv;
		}    	
		
	    private class SpeechView extends LinearLayout {
	        private TextView mTitle;
	        
	    	public SpeechView(Context context, String title) {
	            super(context);	            
	            this.setOrientation(VERTICAL);
	            this.setPadding(20, 20, 20, 20);
	            this.setBackgroundColor(Color.TRANSPARENT);
	            	            
	            mTitle = new TextView(context);
	            mTitle.setText(title);
	            mTitle.setTextSize(20); 
	            mTitle.setTextColor(Color.WHITE);
	            addView(mTitle, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
	            		LayoutParams.WRAP_CONTENT));
	    	}

	        public void setTitle(String title) {
	            mTitle.setText(title);
	        }

	    }
    }      
}
