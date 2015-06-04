package com.example.simov;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.packet.DelayInfo;

import android.content.Context;
import android.os.Build;
import android.util.Log;

public class ASmackConnections {

	public static final String HOST = "172.31.100.160";
	public static final int PORT = 5222;
	public static final String SERVICE = "Work";
	private String USERNAME;

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getUSERNAME_CASA() {
		return USERNAME_CASA;
	}

	public void setUSERNAME_CASA(String uSERNAME_CASA) {
		USERNAME_CASA = uSERNAME_CASA;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	private String USERNAME_CASA;
	private String PASSWORD;

	private ArrayList<SensorBT> sensores = new ArrayList<SensorBT>();

	// asmack
	ConnectionConfiguration config;
	SmackAndroid asmk;
	Chat ch;
	private XMPPConnection connection;

	public XMPPConnection getConnection() {
		return connection;
	}

	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
	}

	public String getsensor = null;

	public static ASmackConnections instance;

	private ASmackConnections(Context context) {
		asmk = SmackAndroid.init(context);

		config = new ConnectionConfiguration(HOST, PORT, SERVICE);
		config.setSASLAuthenticationEnabled(false);
		config.setCompressionEnabled(true);
		config.setSecurityMode(SecurityMode.enabled);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			config.setTruststoreType("AndroidCAStore");
			config.setTruststorePassword(null);
			config.setTruststorePath(null);
		} else {
			config.setTruststoreType("BKS");
			String path = System.getProperty("javax.net.ssl.trustStore");
			if (path == null)
				path = System.getProperty("java.home")
						+ "\\etc\\security\\cacerts.bks";
			config.setTruststorePath(path);
		}

		connection = new XMPPConnection(config);

		XMPPConnection.DEBUG_ENABLED = true;
		SASLAuthentication.supportSASLMechanism("PLAIN", 0);

		handleMessages();
	}

	public static ASmackConnections getInstance(Context context) {
		if (instance == null) {
			synchronized (ASmackConnections.class) {
				if (instance == null) {
					instance = new ASmackConnections(context);
				}
			}
		}
		return instance;
	}

	public void handleMessages() {

		connection.addPacketListener(new PacketListener() {
			@Override
			public void processPacket(Packet packet) {
				Message message = (Message) packet;
				if (message.getBody() != null) {
					String fromName = StringUtils.parseBareAddress(message
							.getFrom());
					Log.i("XMPPChatDemoActivity ",
							" Text Recieved " + message.getBody() + " from "
									+ fromName);

					String body = message.getBody();
					for (PacketExtension extension : message.getExtensions()) {
						if (extension instanceof DelayInfo) {
							DelayInfo delayInfo = (DelayInfo) extension;
							System.out.println(message.getFrom() + " sayd at "
									+ delayInfo.getStamp() + ": "
									+ message.getBody());
							body += " : " + delayInfo.getStamp();
							return;
						}
					}

					int id = Integer.parseInt(body.substring(0,
							body.indexOf("!")));
					String m = body.substring(body.indexOf("!") + 1,
							body.length());

					for (SensorBT s : getSensores()) {
						if (s.getValores().size() == 5) {
							s.getValores().remove(0);
						}
					}

					getSensorById(id).getValores().add(m);
				}
			}
		}, new PacketFilter() {

			@Override
			public boolean accept(Packet arg0) {
				if (arg0 instanceof Message) {
					return true;
				}
				return false;
			}
		});
	}

	public String getSensoresUser() throws ClientProtocolException,
			IOException, InterruptedException {
		while (getsensor == null) {
			Thread.sleep(1000);
		}

		String ret = getsensor;
		return ret;
	}

	public void login(String username, String password) throws XMPPException,
			InterruptedException {

		USERNAME = username;
		PASSWORD = password;

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					connection.connect();

					Presence presence = new Presence(Presence.Type.available);
					presence.setStatus("Logged in @ android?");
					connection.sendPacket(presence);

					connection.login(USERNAME, PASSWORD);

					ch = connection.getChatManager().createChat(
							"simov@simov001", new MessageListener() {

								@Override
								public void processMessage(Chat arg0,
										Message message) {
								}
							});
				} catch (XMPPException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
		thread.join();
	}

	public static ASmackConnections getInstance() {
		return instance;
	}

	public void putSensors(String result) {
		getsensor = result;
	}

	public ArrayList<SensorBT> getSensores() {
		return sensores;
	}

	public SensorBT getSensorById(int sensorId) {
		for (int i = 0; i < sensores.size(); i++) {
			if (sensores.get(i).getId() == sensorId) {
				return sensores.get(i);
			}
		}
		return null;
	}

	public SensorBT findSensorById(String sensorNome) {
		for (SensorBT s : sensores) {
			if (s.getName().equalsIgnoreCase(sensorNome)) {
				return s;
			}
		}
		return null;
	}

	public void addSensorIfNotExists(SensorBT bt) {
		boolean flag = false;
		for (SensorBT s : sensores) {
			if (s.getId() == bt.getId()) {
				flag = true;
			}
		}

		if (flag == false) {
			sensores.add(bt);
		}
	}

	public SensorBT getSensorByName(String sensor) {
		for (SensorBT s : sensores) {
			if (s.getName().equals(sensor)) {
				return s;
			}
		}
		return null;
	}

}
