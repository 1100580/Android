package com.example.simov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditSensor extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(EditSensor.this, EditUser.class);
			startActivity(i);
			return true;
		case R.id.add_sensor:
			Intent j = new Intent(EditSensor.this, AddSensor.class);
			startActivity(j);
			return true;
		case R.id.remove_sensor:
			Intent k = new Intent(EditSensor.this, RemoveSensor.class);
			startActivity(k);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit_sensor);

		int sensorId = 1; // getIntent().getExtras().getInt("sensor", -1);

		SensorBT sensor = ASmackConnections.getInstance().getSensorById(
				sensorId);

		TextView ligado = (TextView) findViewById(R.id.ligado);
		Button ligadoBt = (Button) findViewById(R.id.buttonLigado);

		if (sensor.getLigado() == 1) {
			ligado.setText("Sensor está ligado");
			ligadoBt.setText("Desligar");
		} else {
			ligado.setText("Sensor está desligado");
			ligadoBt.setText("Ligar");
		}

		ligadoBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
			}
		});

		EditText limMax = (EditText) findViewById(R.id.limMaxEdit);
		EditText limMin = (EditText) findViewById(R.id.limMinEdit);

		Button mudar = (Button) findViewById(R.id.buttonMudar);

		mudar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
			}
		});
	}
}
