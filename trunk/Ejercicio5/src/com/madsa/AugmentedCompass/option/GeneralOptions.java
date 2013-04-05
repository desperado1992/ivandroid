/*
 *  Copyright (C) 2010 GTEBIM.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/. 
 *
 *  Author: Jorge Pintado de Santiago
 *  
 */

package com.madsa.AugmentedCompass.option;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class GeneralOptions extends PreferenceActivity{
	
	public static SharedPreferences appPreferences;
	private CheckBox checkSound;
	private CheckBox checkVibration;
	private CheckBox checkMaps;

	private boolean mapsBoolean = false;
	private boolean soundBoolean = false;
	private boolean vibrationBoolean = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        //General layout
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        //parameters layout (auxiliar)
        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        ll.setLayoutParams(lp);
        
		//Get the last application preferences
        appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        soundBoolean = appPreferences.getBoolean("Sound",false);
        vibrationBoolean = appPreferences.getBoolean("Vibration",false);
        mapsBoolean = appPreferences.getBoolean("Maps",false);
               
        //First Component
        TextView tv=new TextView(this);
        tv.setText("Opciones de realidad aumentada");
        lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        ll.addView(tv);
        
        //Sound CheckButton
        checkSound = new CheckBox(this);
        checkSound.setChecked(soundBoolean);
        checkSound.setText("sonido");
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        checkSound.setLayoutParams(lp);
        ll.addView(checkSound);

        //Vibration CheckButton
        checkVibration = new CheckBox(this);
        checkVibration.setChecked(vibrationBoolean
        		);
        checkVibration.setText("Vibración");
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        checkVibration.setLayoutParams(lp);
        ll.addView(checkVibration);

        //Maps CheckButton
        checkMaps = new CheckBox(this);
        checkMaps.setChecked(mapsBoolean);
        checkMaps.setText("Incluir mapa");
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        checkMaps.setLayoutParams(lp);
        ll.addView(checkMaps);
        
        //Apply button
        Button applyButton = new Button(this);
        applyButton.setText("Apply");
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity=android.view.Gravity.CENTER;
        applyButton.setLayoutParams(lp);
        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	writeOptions();
            	finish();
            }
        });
        ll.addView(applyButton);
                
        //set layout as content view
        //we use addContentView instead of setContentView because this class extends PreferenceActivity (not Activity)
        lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addContentView(ll, lp);
    }
    
	public void writeOptions(){

        // Update the preferences
        if(checkSound.isChecked()){
        	soundBoolean = true;
        }else{
        	soundBoolean = false;
        }
               
        if(checkVibration.isChecked()){
        	vibrationBoolean = true;
        }else{
        	vibrationBoolean = false;
        }

        if(checkMaps.isChecked()){
        	mapsBoolean = true;
        }else{
        	mapsBoolean = false;
        }      
        
        //Update variables
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.commit();
        updatePreferences(editor);
        editor.commit();
	}
	
	private void updatePreferences(SharedPreferences.Editor tempEditor){
        tempEditor.putBoolean("Sound", soundBoolean);
        tempEditor.putBoolean("Vibration", vibrationBoolean);
        tempEditor.putBoolean("Maps", mapsBoolean);
        tempEditor.commit(); // Very important
	}
}
