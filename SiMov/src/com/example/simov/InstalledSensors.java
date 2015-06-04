package com.example.simov;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InstalledSensors extends ListActivity {

	ASmackConnections asmkc;

	String type = "";
	ArrayList<SensorBT> sensoresbt = new ArrayList<SensorBT>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		asmkc = ASmackConnections.getInstance();

		Bundle bundle = getIntent().getExtras();
		type = bundle.getString("tipo");

		registerSensors();

		// todos do user
		// GetUserSensors d = new GetUserSensors();
		// d.execute("");

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String sensor = ((TextView) v).getText().toString();

		Intent i = new Intent(InstalledSensors.this, TypeDetails.class);
		i.putExtra("sensor", sensor);
		startActivity(i);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	public void registerSensors() {

		String[] sensores = new String[asmkc.getSensores().size()];
		int counter = 0;

		for (int i = 0; i < asmkc.getSensores().size(); i++) {
			String tipo = asmkc.getSensores().get(i).getTipo();
			if (tipo.toLowerCase().equals(type.toLowerCase())) {
				sensores[counter] = asmkc.getSensores().get(i).getName();
				counter++;
			}
		}

		String[] finalS = new String[counter];
		for (int i = 0; i < finalS.length; i++) {
			finalS[i] = sensores[i];
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, finalS);

		ListView lv = getListView();
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(InstalledSensors.this, EditUser.class);
			startActivity(i);
			return true;
		case R.id.add_sensor:
			Intent j = new Intent(InstalledSensors.this, AddSensor.class);
			startActivity(j);
			return true;
		case R.id.remove_sensor:
			Intent k = new Intent(InstalledSensors.this, RemoveSensor.class);
			startActivity(k);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
