package com.example.simov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TypeDetails extends Activity {

	ASmackConnections asmkc;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(TypeDetails.this, EditUser.class);
			startActivity(i);
			return true;
		case R.id.add_sensor:
			Intent j = new Intent(TypeDetails.this, AddSensor.class);
			startActivity(j);
			return true;
		case R.id.remove_sensor:
			Intent k = new Intent(TypeDetails.this, RemoveSensor.class);
			startActivity(k);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		asmkc = ASmackConnections.getInstance();

		setContentView(R.layout.type_details);

		TextView nomeSensor = (TextView) findViewById(R.id.firstLine);

		String sensorNome = getIntent().getStringExtra("sensor");

		nomeSensor.setText(sensorNome);

		SensorBT sensor = asmkc.findSensorById(sensorNome);

		TextView detalheSensor = (TextView) findViewById(R.id.secondLine);

		Button bt = (Button) findViewById(R.id.buttonEdit);

		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(TypeDetails.this, EditSensor.class);
				i.putExtra("sensor", getIntent().getStringExtra("sensor"));
				// startActivity(i);
			}
		});

		String text = "";

		if (sensor != null) {
			for (String valor : sensor.getValores())
				text = valor + "\n" + text;
		}

		detalheSensor.setText(text);

	}
}
