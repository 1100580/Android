package com.example.simov;

import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView loginStatus;
	private EditText usernameEdit;
	private EditText passEdit;
	private Button loginButton;
	private ProgressBar loginProgress;
	private ASmackConnections asmk;
	private Login asyncLogin;
	private Context thisContext;
	boolean needsAsync = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Settings
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Inits
		setContentView(R.layout.activity_main);
		thisContext = this;
		
		asmk = ASmackConnections.getInstance(thisContext);
		usernameEdit = (EditText) findViewById(R.id.edit_message);
		passEdit = (EditText) findViewById(R.id.edit_message2);
		loginButton = (Button) findViewById(R.id.button_send);
		loginProgress = (ProgressBar) findViewById(R.id.progressBarLogin);
		loginStatus = (TextView) findViewById(R.id.loginStatus);

		// View algorithms
		loginProgress.setVisibility(View.INVISIBLE);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					asyncLogin = new Login();
					asyncLogin.execute(usernameEdit.getText().toString(),
							passEdit.getText().toString());
					//UI provisions
					loginProgress.setVisibility(View.VISIBLE);
					loginStatus.setText("");
					Thread thread1 = new Thread() {
						public void run() {
							try {
								asyncLogin.get(8000, TimeUnit.MILLISECONDS);
							} catch (Exception e) {
								e.printStackTrace();
								asyncLogin.cancel(true);
								((Activity) thisContext)
										.runOnUiThread(new Runnable() {
											public void run() {
												loginProgress
														.setVisibility(View.INVISIBLE);
												loginStatus.setText("Could not login, Timeout.");
											}
										});
							}
						}
					};
					thread1.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private class Login extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... dados) {
			try {
				asmk.getConnection().connect();
				asmk.getConnection().login(dados[0], dados[1]);
				return asmk.getConnection().isAuthenticated();
			} catch (Exception e) {
				//Algorithm Provisions
				asmk.getConnection().disconnect();
				return false;
			}

		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Boolean authenticated) {
			if (authenticated) {
				Intent i = new Intent(MainActivity.this, TypesList.class);
				startActivity(i);
			}else{
				//UI provisions
				loginProgress.setVisibility(View.INVISIBLE);
				loginStatus.setText("Could not connect to XMPP server.");
			}
		}
	}

}
