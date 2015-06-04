package com.example.simov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TypesList extends ListActivity {
	
	GetUserSensors g;
	ASmackConnections asmkc;

	ArrayList<SensorBT> sensoresbt = new ArrayList<SensorBT>();

	public void handleList(){
		String[] tipos = getResources().getStringArray(R.array.sensorType);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, tipos);
		ListView lv = getListView();
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		
		asmkc = ASmackConnections.getInstance();
		g = new GetUserSensors();
		g.execute();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(TypesList.this, EditUser.class);
			startActivity(i);
			return true;
		case R.id.add_sensor:
			Intent j = new Intent(TypesList.this, AddSensor.class);
			startActivity(j);
			return true;
		case R.id.remove_sensor:
			Intent k = new Intent(TypesList.this, RemoveSensor.class);
			startActivity(k);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int pos = info.position;
		long id = info.id;
		ListView lv = getListView();
		String str = lv.getItemAtPosition(pos).toString();

		switch (item.getItemId()) {
		case R.id.edit:
			Toast.makeText(getApplicationContext(), "Edit:" + id + ":" + str,
					Toast.LENGTH_LONG).show();
			return true;
		case R.id.delete:
			Toast.makeText(getApplicationContext(), "Delete:" + id + ":" + str,
					Toast.LENGTH_LONG).show();
			return true;
		case R.id.share:
			Toast.makeText(getApplicationContext(), "Share:" + id + ":" + str,
					Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String tipo = ((TextView) v).getText().toString();
		Intent i = new Intent(TypesList.this, InstalledSensors.class);
		i.putExtra("tipo", tipo);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    try{
	    	GetUserSensors get = new GetUserSensors();
	    	get.execute();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}
	
	private class GetUserSensors extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... user) {
			// TODO mudar link
			String all = asmkc.getConnection().getUser();
			String u = all.substring(0, all.indexOf("@"));

			String URL = "http://172.31.100.160:8080/RESTfulDemoApplication/sensor/user?username="
					+ u;
			System.out.println("Request: " + URL);
			String response = "";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			try {
				HttpResponse execute = client.execute(httpGet);
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
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
			        JSONObject obj = array.getJSONObject(i);
			        SensorBT bt = new SensorBT();
			        bt.setId(Integer.parseInt(obj.getString("id")));
			        bt.setName(obj.getString("name"));
			        bt.setDistAtivacao(obj.getDouble("distanciaAtivacao"));
			        bt.setTipo(obj.getString("tipo"));
			        bt.setAlertType(obj.getString("alertType"));
			        bt.setAlertMax(obj.getDouble("alertMax"));
			        bt.setAlertMin(obj.getDouble("alertMin"));
			        bt.setAlert(obj.getBoolean("alert"));
			        
			        asmkc.addSensorIfNotExists(bt);
			        
			        handleList();
			      }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
