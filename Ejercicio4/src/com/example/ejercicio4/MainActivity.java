package com.example.ejercicio4;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	InterestingPointEnum[] aInterestingPointsEnum;
	String[] aInterestingPoints;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.aInterestingPointsEnum = new InterestingPointEnum[] {
				InterestingPointEnum.Puerto,
				InterestingPointEnum.Iglesia,
				InterestingPointEnum.Plaza,
				InterestingPointEnum.Ayuntamiento,
				InterestingPointEnum.Instituto
				};
		this.aInterestingPoints = new String[] {
				InterestingPointEnum.Puerto.name(),
				InterestingPointEnum.Iglesia.name(),
				InterestingPointEnum.Plaza.name(),
				InterestingPointEnum.Ayuntamiento.name(),
				InterestingPointEnum.Instituto.name()
				};
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, this.aInterestingPoints);
		ListView lstcities = (ListView)findViewById(R.id.listViewPoints);
		lstcities.setAdapter(adaptador);
		lstcities.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	public void onItemClick(AdapterView<?> av, View v, int position, long arg3) {
		InterestingPointEnum item = this.aInterestingPointsEnum[position];

			
		Intent i_map = new Intent(MainActivity.this, InterestingPointActivity.class);
		i_map.putExtra("item", item);
		startActivity(i_map);
	}
}
