package com.example.simov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditUser extends Activity {

	EditText lon;
	EditText lat;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(EditUser.this, EditUser.class);
			startActivity(i);
			return true;
		case R.id.add_sensor:
			Intent j = new Intent(EditUser.this, AddSensor.class);
			startActivity(j);
			return true;
		case R.id.remove_sensor:
			Intent k = new Intent(EditUser.this, RemoveSensor.class);
			startActivity(k);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit_user);

		lon = (EditText) findViewById(R.id.lon);
		lat = (EditText) findViewById(R.id.lat);

		Button mudar = (Button) findViewById(R.id.mudarUser);

		mudar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditMorada ed = new EditMorada();
				ed.execute(lon.getText().toString(), lat.getText().toString());
			}
		});
	}

	private class EditMorada extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... vars) {
			String all = ASmackConnections.getInstance().getConnection()
					.getUser();
			String u = all.substring(0, all.indexOf("@"));

			String URL = "http://172.31.100.160:8080/RESTfulDemoApplication/sensor/";

			// --This code works for updating a record from the feed--
			HttpPost httpPost = new HttpPost(URL);
			JSONStringer json = null;
			try {
				json = new JSONStringer().object().key("moradaLatitude")
						.value(vars[0]).key("moradaLongitude").value(vars[1])
						.key("username").value(u).endObject();
				StringEntity entity = new StringEntity(json.toString());
				entity.setContentType("application/json;charset=UTF-8");// text/plain;charset=UTF-8
				entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httpPost.setEntity(entity);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String response = "";
			DefaultHttpClient client = new DefaultHttpClient();
			// HttpGet httpGet = new HttpGet(URL);
			try {
				HttpResponse execute = client.execute(httpPost);
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
	}

}
