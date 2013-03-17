package com.example.ejercicio4;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;


public class Map extends MapActivity {
	private MapView mapview;
	private MapController map_controller;
	private GeoPoint geop;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		Bundle extras = getIntent().getExtras();
		String coordenadas = null;
		if (extras != null){
		coordenadas = extras.getString("tag_coordenadas");
		}
		this.mapview = (MapView)findViewById(R.id.mapa);
		this.mapview.setBuiltInZoomControls(true);
		this.map_controller=this.mapview.getController();
		Double latitude=Double.parseDouble(coordenadas.split(",")[0].toString());
		Double longitude = Double.parseDouble(coordenadas.split(",")[1].toString());
		this.geop = new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
		this.map_controller.animateTo(this.geop);
		this.map_controller.setZoom(90);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}