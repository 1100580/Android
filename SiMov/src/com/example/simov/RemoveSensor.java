package com.example.simov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RemoveSensor extends ListActivity {

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
			Intent i = new Intent(RemoveSensor.this, EditUser.class);
			startActivity(i);
			return true;
		case R.id.add_sensor:
			Intent j = new Intent(RemoveSensor.this, AddSensor.class);
			startActivity(j);
			return true;
		case R.id.remove_sensor:
			Intent k = new Intent(RemoveSensor.this, RemoveSensor.class);
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

		System.out.println(asmkc.getSensores().size());

		String[] sensores = new String[asmkc.getSensores().size()];
		int counter = 0;

		for (int i = 0; i < asmkc.getSensores().size(); i++) {
			sensores[counter] = asmkc.getSensores().get(i).getName();
			counter++;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, sensores);

		ListView lv = getListView();
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String sensor = ((TextView) v).getText().toString();
		SensorBT s = asmkc.getSensorByName(sensor);

		DeleteSensors ds = new DeleteSensors();
		String i = "" + s.getId();
		ds.execute(i);

	}

	private class DeleteSensors extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... sensorId) {
			String URL = "http://172.31.100.160:8080/RESTfulDemoApplication/sensor?sensorID="
					+ sensorId[0];

			String response = "";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpDelete httpdel = new HttpDelete(URL);
			try {
				HttpResponse execute = client.execute(httpdel);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(response);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), "Sensor removido!",
					Toast.LENGTH_SHORT).show();
		}
	}
}
